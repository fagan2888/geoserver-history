/* Copyright (c) 2001, 2003 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.global;

import com.vividsolutions.jts.geom.Envelope;
import org.geotools.data.AttributeTypeMetaData;
import org.geotools.data.DataSourceException;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreMetaData;
import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureTypeMetaData;
import org.geotools.feature.AttributeType;
import org.geotools.feature.FeatureType;
import org.geotools.feature.FeatureTypeFactory;
import org.geotools.feature.SchemaException;
import org.geotools.filter.Filter;
import org.vfny.geoserver.global.dto.AttributeTypeInfoDTO;
import org.vfny.geoserver.global.dto.DataTransferObjectFactory;
import org.vfny.geoserver.global.dto.FeatureTypeInfoDTO;
import org.vfny.geoserver.global.xml.NameSpaceTranslatorFactory;
import org.vfny.geoserver.global.xml.XMLConfigWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Represents a FeatureTypeInfo, its user config and autodefined information.
 *
 * @author Gabriel Rold�n
 * @author Chris Holmes
 * @author dzwiers
 * @version $Id: FeatureTypeInfo.java,v 1.21 2004/02/06 19:58:05 dmzwiers Exp $
 */
public class FeatureTypeInfo extends GlobalLayerSupertype
    implements FeatureTypeMetaData {
    /** Default constant */
    private static final int DEFAULT_NUM_DECIMALS = 8;

    /** The DTO instane which hold this instance's data */
    private FeatureTypeInfoDTO ftc;

    /** ref to parent set of datastores. */
    private Data data;
    private Map meta;

    /**
     * AttributeTypeInfo by attribute name.
     * 
     * <p>
     * This will be null unless populated by schema or DTO.
     * </p>
     */
    private Map attributeInfo; //kill me pls

    /** will be lazily generated */
    private String xmlSchemaFrag;

    /** will be lazily created */
    private FeatureSource fs;

    /**
     * FeatureTypeInfo constructor.
     * 
     * <p>
     * Generates a new object from the data provided.
     * </p>
     *
     * @param dto FeatureTypeInfoDTO The data to populate this class with.
     * @param data Data a reference for future use to get at DataStoreInfo
     *        instances
     *
     * @throws ConfigurationException
     */
    public FeatureTypeInfo(FeatureTypeInfoDTO dto, Data data)
        throws ConfigurationException {
        ftc = dto;
        this.data = data;
        attributeInfo = null; // will need to generate later               
    }

    /**
     * toDTO purpose.
     * 
     * <p>
     * This method is package visible only, and returns a reference to the
     * GeoServerDTO. This method is unsafe, and should only be used with
     * extreme caution.
     * </p>
     *
     * @return FeatureTypeInfoDTO the generated object
     */
    Object toDTO() {
        return ftc;
    }

    /**
     * getNumDecimals purpose.
     * 
     * <p>
     * The default number of decimals allowed in the data.
     * </p>
     *
     * @return int the default number of decimals allowed in the data.
     */
    public int getNumDecimals() {
        return ftc.getNumDecimals();
    }

    /**
     * getDataStore purpose.
     * 
     * <p>
     * gets the string of the path to the schema file.  This is set during
     * feature reading, the schema file should be in the same folder as the
     * feature type info, with the name schema.xml.  This function does not
     * guarantee that the schema file actually exists, it just gives the
     * location where it _should_ be located.
     * </p>
     *
     * @return DataStoreInfo the requested DataStoreInfo if it was found.
     *
     * @see Data#getDataStoreInfo(String)
     */
    public DataStoreInfo getDataStoreInfo() {
        return data.getDataStoreInfo(ftc.getDataStoreId());
    }

    /**
     * Indicates if this FeatureTypeInfo is enabled.  For now just gets whether
     * the backing datastore is enabled.
     *
     * @return <tt>true</tt> if this FeatureTypeInfo is enabled.
     *
     * @task REVISIT: Consider adding more fine grained control to config
     *       files, so users can indicate specifically if they want the
     *       featureTypes enabled, instead of just relying on if the datastore
     *       is. Jody here - this should be done on a service by service basis
     *       WMS and WFS will need to decide for themselves on this one
     */
    public boolean isEnabled() {
        return (getDataStoreInfo() != null) && (getDataStoreInfo().isEnabled());
    }

    /**
     * Returns the XML prefix used for GML output of this FeatureType.
     * 
     * <p>
     * Returns the namespace prefix for this FeatureTypeInfo. This prefix also
     * seems to be used as a "ID" for looking up GeoServer Namespace.
     * </p>
     *
     * @return String the namespace prefix.
     */
    public String getPrefix() {
        return getDataStoreInfo().getNameSpace().getPrefix();
    }

    /**
     * Gets the namespace for this featureType.  This isn't _really_ necessary,
     * but I'm putting it in in case we change namespaces,  letting
     * FeatureTypes set their own namespaces instead of being dependant on
     * datasources.  This method will allow us to make that change more easily
     * in the future.
     *
     * @return NameSpaceInfo the namespace specified for the specified
     *         DataStoreInfo (by ID)
     *
     * @throws IllegalStateException THrown when disabled.
     */
    public NameSpaceInfo getNameSpace() {
        if (!isEnabled()) {
            throw new IllegalStateException("This featureType is not "
                + "enabled");
        }

        return getDataStoreInfo().getNameSpace();
    }

    /**
     * overrides getName to return full type name with namespace prefix
     *
     * @return String the FeatureTypeInfo name - should be unique for the
     *         parent Data instance.
     */
    public String getName() {
        return ((DataStoreInfo) data.getDataStoreInfo(ftc.getDataStoreId())).getNameSpace()
                .getPrefix() + ":" + ftc.getName();
    }

    /**
     * Convenience method for those who just want to report the name of the
     * featureType instead of requiring the full name for look up.  If
     * allowShort is true then just the localName, with no prefix, will be
     * returned if the dataStore is not enabled.  If allow short is false then
     * a full getName will be returned, with potentially bad results.
     *
     * @param allowShort does nothing
     *
     * @return String getName()
     *
     * @see getName()
     */
    public String getName(boolean allowShort) {
        if (allowShort && (!isEnabled() || (getDataStoreInfo() == null))) {
            return getShortName();
        } else {
            return getName();
        }
    }

    /**
     * Same as getName()
     *
     * @return String getName()
     *
     * @see getName()
     */
    public String getShortName() {
        return ftc.getName();
    }

    /**
     * getFeatureSource purpose.
     * 
     * <p>
     * Returns a real FeatureSource.
     * </p>
     *
     * @return FeatureSource the feature source represented by this info class
     *
     * @throws IOException when an error occurs.
     * @throws DataSourceException DOCUMENT ME!
     */
    public FeatureSource getFeatureSource() throws IOException {
        if (!isEnabled() || (getDataStoreInfo().getDataStore() == null)) {
            throw new IOException("featureType: " + getName(true)
                + " does not have a properly configured " + "datastore");
        }

        if (fs == null) {
            DataStore dataStore = data.getDataStoreInfo(ftc.getDataStoreId())
                                      .getDataStore();
            String typeName = ftc.getName();
            FeatureSource realSource = dataStore.getFeatureSource(typeName);

            if (((ftc.getSchemaAttributes() == null)
                    || ftc.getSchemaAttributes().isEmpty())) { // && 

                //(ftc.getDefinitionQuery() == null || ftc.getDefinitionQuery().equals( Query.ALL ))){
                fs = realSource;
            } else {
                try {
                    fs = reTypeSource(realSource, ftc);
                } catch (SchemaException e) {
                    throw new DataSourceException("Could not make FeatureSource attributes don't match",
                        e);
                }
            }
        }

        return fs;
    }

    public static FeatureSource reTypeSource(FeatureSource source,
        FeatureTypeInfoDTO ftc) throws SchemaException {
        AttributeType[] attributes = new AttributeType[ftc.getSchemaAttributes()
                                                          .size()];

        List attributeDefinitions = ftc.getSchemaAttributes();
        int index = 0;
        FeatureType ft = source.getSchema();

        for (int i = 0; i < attributes.length; i++) {
            AttributeTypeInfoDTO attributeDTO = (AttributeTypeInfoDTO) ftc.getSchemaAttributes()
                                                                          .get(i);
            String xpath = attributeDTO.getName();
            attributes[i] = ft.getAttributeType(xpath);

            if (attributes[i] == null) {
                throw new NullPointerException("Error finding " + xpath
                    + " specified in you schema.xml file for " + ftc.getName()
                    + "FeatureType.");
            }
        }

        FeatureType myType = FeatureTypeFactory.newFeatureType(attributes,
                ftc.getName());

        return GeoServerFeatureLocking.create(source, myType,
            ftc.getDefinitionQuery());
    }

    /**
     * getBoundingBox purpose.
     * 
     * <p>
     * The feature source bounds.
     * </p>
     *
     * @return Envelope the feature source bounds.
     *
     * @throws IOException when an error occurs
     */
    public Envelope getBoundingBox() throws IOException {
        DataStore dataStore = data.getDataStoreInfo(ftc.getDataStoreId())
                                  .getDataStore();
        FeatureSource realSource = dataStore.getFeatureSource(ftc.getName());

        return realSource.getBounds();
    }

    /**
     * getDefinitionQuery purpose.
     * 
     * <p>
     * Returns the definition query for this feature source
     * </p>
     *
     * @return Filter the definition query
     */
    public Filter getDefinitionQuery() {
        return ftc.getDefinitionQuery();
    }

    /**
     * getLatLongBoundingBox purpose.
     * 
     * <p>
     * The feature source lat/long bounds.
     * </p>
     *
     * @return Envelope the feature source lat/long bounds.
     *
     * @throws IOException when an error occurs
     */
    public Envelope getLatLongBoundingBox() throws IOException {
        if (ftc.getLatLongBBox() == null) {
            return getBoundingBox();
        }

        return ftc.getLatLongBBox();
    }

    /**
     * getSRS purpose.
     * 
     * <p>
     * Proprietary identifier number
     * </p>
     *
     * @return int the SRS number.
     */
    public String getSRS() {
        return ftc.getSRS() + "";
    }

    /**
     * Get XMLSchema for this FeatureType.
     * 
     * <p>
     * Note this may require connection to the real geotools2 DataStore and as
     * such is subject to IOExceptions.
     * </p>
     * 
     * <p>
     * You have been warned.
     * </p>
     *
     * @return XMLFragment
     *
     * @throws IOException DOCUMENT ME!
     */
    public synchronized String getXMLSchema() throws IOException {
        if (xmlSchemaFrag == null) {
            StringWriter sw = new StringWriter();

            try {
                FeatureTypeInfoDTO dto = getGeneratedDTO();
                XMLConfigWriter.storeFeatureSchema(dto, sw);
            } catch (ConfigurationException e) {
            	e.printStackTrace();
            }
            xmlSchemaFrag = sw.toString();
            sw.close();
        }

        return xmlSchemaFrag;
    }

    /**
     * Will return our delegate with all information filled out
     * 
     * <p>
     * This is a hack because we cache our DTO delegate, this method combines
     * or ftc delegate with possibly generated schema information for use by
     * XMLConfigWriter among others.
     * </p>
     * 
     * <p>
     * Call this method to receive a complete featureTypeInfoDTO that incldues
     * all schema information.
     * </p>
     *
     * @return
     *
     * @throws IOException DOCUMENT ME!
     */
    private synchronized FeatureTypeInfoDTO getGeneratedDTO()
        throws IOException {
        FeatureTypeInfoDTO dto = new FeatureTypeInfoDTO(ftc);

        if ((dto.getSchemaAttributes() == null)
                || (dto.getSchemaAttributes().size() == 0)) {
            // generate stuff
            FeatureType schema = getFeatureType();
            dto.setSchemaBase(NameSpaceTranslatorFactory.getInstance().getNameSpaceTranslator("gml").getElement("AbstractFeatureType").getQualifiedTypeDefName());
            dto.setSchemaName(schema.getTypeName()); //.toUpperCase()+"_TYPE" );
            dto.setSchemaAttributes(DataTransferObjectFactory
                .generateAttributes(schema));
        }

        return dto;
    }

    /**
     * getAttribute purpose.
     * 
     * <p>
     * XLM helper method.
     * </p>
     *
     * @param elem The element to work on.
     * @param attName The attribute name to find
     * @param mandatory true is an exception is be thrown when the attr is not
     *        found.
     *
     * @return String the Attr value
     *
     * @throws ConfigurationException thrown when an error occurs.
     */
    protected String getAttribute(Element elem, String attName,
        boolean mandatory) throws ConfigurationException {
        Attr att = elem.getAttributeNode(attName);

        String value = null;

        if (att != null) {
            value = att.getValue();
        }

        if (mandatory) {
            if (att == null) {
                throw new ConfigurationException("element "
                    + elem.getNodeName()
                    + " does not contains an attribute named " + attName);
            } else if ("".equals(value)) {
                throw new ConfigurationException("attribute " + attName
                    + "in element " + elem.getNodeName() + " is empty");
            }
        }

        return value;
    }

    /*private FeatureType getSchema(String schema) throws ConfigurationException{
       try{
               return getSchema(loadConfig(new StringReader(schema)));
       }catch(IOException e){
               throw new ConfigurationException("",e);
       }
       }*/

    /**
     * loadConfig purpose.
     * 
     * <p>
     * Parses the specified file into a DOM tree.
     * </p>
     *
     * @param fis The file to parse int a DOM tree.
     *
     * @return the resulting DOM tree
     *
     * @throws ConfigurationException
     */
    public static Element loadConfig(Reader fis) throws ConfigurationException {
        try {
            InputSource in = new InputSource(fis);
            DocumentBuilderFactory dfactory = DocumentBuilderFactory
                .newInstance();

            /*set as optimizations and hacks for geoserver schema config files
             * @HACK should make documents ALL namespace friendly, and validated. Some documents are XML fragments.
             * @TODO change the following config for the parser and modify config files to avoid XML fragmentation.
             */
            dfactory.setNamespaceAware(false);
            dfactory.setValidating(false);
            dfactory.setIgnoringComments(true);
            dfactory.setCoalescing(true);
            dfactory.setIgnoringElementContentWhitespace(true);

            Document serviceDoc = dfactory.newDocumentBuilder().parse(in);
            Element configElem = serviceDoc.getDocumentElement();

            return configElem;
        } catch (IOException ioe) {
            String message = "problem reading file " + "due to: "
                + ioe.getMessage();
            LOGGER.warning(message);
            throw new ConfigurationException(message, ioe);
        } catch (ParserConfigurationException pce) {
            String message =
                "trouble with parser to read org.vfny.geoserver.config.org.vfny.geoserver.config.xml, make sure class"
                + "path is correct, reading file ";
            LOGGER.warning(message);
            throw new ConfigurationException(message, pce);
        } catch (SAXException saxe) {
            String message = "trouble parsing XML " + ": " + saxe.getMessage();
            LOGGER.warning(message);
            throw new ConfigurationException(message, saxe);
        }
    }

    /**
     * here we must make the transformation. Crhis: do you know how to do it? I
     * don't know.  Ask martin or geotools devel.  This will be better when
     * our geometries actually have their srs objects.  And I think that we
     * may need some MS Access database, not sure, but I saw some stuff about
     * that on the list.  Hopefully they'll do it all in java soon.  I'm sorta
     * tempted to just have users define for now.
     *
     * @param fromSrId
     * @param bbox Envelope
     *
     * @return Envelope
     */
    private static Envelope getLatLongBBox(String fromSrId, Envelope bbox) {
        return bbox;
    }

    /**
     * Get abstract (description) of FeatureType.
     *
     * @return Short description of FeatureType
     */
    public String getAbstract() {
        return ftc.getAbstract();
    }

    /**
     * Keywords describing content of FeatureType.
     * 
     * <p>
     * Keywords are often used by Search engines or Catalog services.
     * </p>
     *
     * @return List the FeatureTypeInfo keywords
     */
    public List getKeywords() {
        return ftc.getKeywords();
    }

    /**
     * getTitle purpose.
     * 
     * <p>
     * returns the FeatureTypeInfo title
     * </p>
     *
     * @return String the FeatureTypeInfo title
     */
    public String getTitle() {
        return ftc.getTitle();
    }

    /**
     * getSchemaName purpose.
     * 
     * <p>
     * Description ...
     * </p>
     *
     * @return
     */
    public String getSchemaName() {
        return ftc.getSchemaName();
    }

    /**
     * setSchemaName purpose.
     * 
     * <p>
     * Description ...
     * </p>
     *
     * @param string
     */
    public void setSchemaName(String string) {
        ftc.setSchemaName(string);
    }

    /**
     * getSchemaName purpose.
     * 
     * <p>
     * Description ...
     * </p>
     *
     * @return
     */
    public String getSchemaBase() {
        return ftc.getSchemaBase();
    }

    /**
     * setSchemaName purpose.
     * 
     * <p>
     * Description ...
     * </p>
     *
     * @param string
     */
    public void setSchemaBase(String string) {
        ftc.setSchemaBase(string);
    }

    //
    // FeatureTypeMetaData Interface
    //

    /**
     * Access typeName.
     *
     * @return typeName for FeatureType
     *
     * @see org.geotools.data.FeatureTypeMetaData#getTypeName()
     */
    public String getTypeName() {
        return ftc.getDataStoreId();
    }

    /**
     * Access real geotools2 FeatureType.
     *
     * @return Schema information.
     *
     * @throws IOException
     *
     * @see org.geotools.data.FeatureTypeMetaData#getFeatureType()
     */
    public FeatureType getFeatureType() throws IOException {
        return getFeatureSource().getSchema();
    }

    /**
     * Implement getDataStoreMetaData.
     *
     * @return
     *
     * @see org.geotools.data.FeatureTypeMetaData#getDataStoreMetaData()
     */
    public DataStoreMetaData getDataStoreMetaData() {
        return (DataStoreMetaData) data.getDataStoreInfo(ftc.getDataStoreId());
    }

    /**
     * FeatureType attributes names as a List.
     * 
     * <p>
     * Convience method for accessing attribute names as a Collection. You may
     * use the names for AttributeTypeMetaData lookup or with the schema for
     * XPATH queries.
     * </p>
     *
     * @return List of attribute names
     *
     * @see org.geotools.data.FeatureTypeMetaData#getAttributeNames()
     */
    public List getAttributeNames() {
        List attribs = ftc.getSchemaAttributes();

        if (attribs.size() != 0) {
            List list = new ArrayList(attribs.size());

            for (Iterator i = attribs.iterator(); i.hasNext();) {
                AttributeTypeInfoDTO at = (AttributeTypeInfoDTO) i.next();
                list.add(at.getName());
            }

            return list;
        }

        List list = new ArrayList();

        try {
            FeatureType schema = getFeatureType();
            AttributeType[] types = schema.getAttributeTypes();
            list = new ArrayList(types.length);

            for (int i = 0; i < types.length; i++) {
                list.add(types[i].getName());
            }
        } catch (IOException e) {
        }

        return list;
    }

    /**
     * Implement AttributeTypeMetaData.
     * 
     * <p>
     * Description ...
     * </p>
     *
     * @param attributeName
     *
     * @return
     *
     * @see org.geotools.data.FeatureTypeMetaData#AttributeTypeMetaData(java.lang.String)
     */
    public synchronized AttributeTypeMetaData AttributeTypeMetaData(
        String attributeName) {
        if (attributeInfo == null) {
            attributeInfo = new HashMap();
        }

        if (attributeInfo.containsKey(attributeName)) {
            return (AttributeTypeMetaData) attributeInfo.get(attributeName);
        }

        AttributeTypeInfo info = null;

        if (ftc.getSchemaAttributes() != null) {
            for (Iterator i = ftc.getSchemaAttributes().iterator();
                    i.hasNext();) {
                AttributeTypeInfoDTO dto = (AttributeTypeInfoDTO) i.next();
                info = new AttributeTypeInfo(dto);
            }

            DataStore dataStore = data.getDataStoreInfo(ftc.getDataStoreId())
                                      .getDataStore();

            try {
                FeatureType schema = dataStore.getSchema(ftc.getName());
                info.sync(schema.getAttributeType(attributeName));
            } catch (IOException e) {
            }
        } else {
            // will need to generate from Schema 
            DataStore dataStore = data.getDataStoreInfo(ftc.getDataStoreId())
                                      .getDataStore();

            try {
                FeatureType schema = dataStore.getSchema(ftc.getName());
                info = new AttributeTypeInfo(schema.getAttributeType(
                            attributeName));
            } catch (IOException e) {
            }
        }

        attributeInfo.put(attributeName, info);

        return info;
    }

    /**
     * Implement containsMetaData.
     *
     * @param key
     *
     * @return
     *
     * @see org.geotools.data.MetaData#containsMetaData(java.lang.String)
     */
    public boolean containsMetaData(String key) {
        return meta.containsKey(key);
    }

    /**
     * Implement putMetaData.
     *
     * @param key
     * @param value
     *
     * @see org.geotools.data.MetaData#putMetaData(java.lang.String,
     *      java.lang.Object)
     */
    public void putMetaData(String key, Object value) {
        meta.put(key, value);
    }

    /**
     * Implement getMetaData.
     *
     * @param key
     *
     * @return
     *
     * @see org.geotools.data.MetaData#getMetaData(java.lang.String)
     */
    public Object getMetaData(String key) {
        return meta.get(key);
    }
}

/* Copyright (c) 2001, 2003 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.global;

import java.io.File;
import java.util.logging.Logger;
import java.util.logging.*;
import org.vfny.geoserver.global.xml.XMLConfigReader;
import org.vfny.geoserver.global.xml.XMLConfigWriter;
import org.vfny.geoserver.global.dto.*;
import java.nio.charset.*;
import org.apache.struts.action.*;
import org.apache.struts.config.*;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;

/**
 * complete configuration ser for the whole server
 *
 * @author Gabriel Rold�n
 * @version $Id: GeoServer.java,v 1.1.2.3 2004/01/05 23:57:30 dmzwiers Exp $
 */
public class GeoServer extends Abstract implements org.apache.struts.action.PlugIn{
	
	/** DOCUMENT ME! */
	private Level loggingLevel = Logger.getLogger("org.vfny.geoserver")
	                                   .getLevel();
	                                   
	private GeoServerDTO geoServer;
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getAddress() {
		return notNull(geoServer.getContact().getAddress());
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getAddressCity() {
		return notNull(geoServer.getContact().getAddressCity());
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getAddressCountry() {
		return notNull(geoServer.getContact().getAddressCountry());
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getAddressPostalCode() {
		return notNull(geoServer.getContact().getAddressPostalCode());
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getAddressState() {
		return notNull(geoServer.getContact().getAddressState());
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getAddressType() {
		return notNull(geoServer.getContact().getAddressType());
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getBaseUrl() {
	    return geoServer.getBaseUrl();
	}
	 
	
	    /**
	     * DOCUMENT ME!
	     *
	     * @return DOCUMENT ME!
	     */
	    public Charset getCharSet() {
	        return geoServer.getCharSet();
	    }
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getContactEmail() {
		return notNull(geoServer.getContact().getContactEmail());
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getContactFacsimile() {
		return notNull(geoServer.getContact().getContactFacsimile());
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getContactOrganization() {
		return notNull(geoServer.getContact().getContactOrganization());
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getContactPerson() {
		return notNull(geoServer.getContact().getContactPerson());
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getContactPosition() {
		return notNull(geoServer.getContact().getContactPosition());
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getContactVoice() {
		return notNull(geoServer.getContact().getContactVoice());
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Level getLoggingLevel() {
	    return loggingLevel;
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getMaxFeatures() {
	    return geoServer.getMaxFeatures();
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getMimeType() {
	    return "text/xml; charset=" + getCharSet().displayName();
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getNumDecimals() {
	    return geoServer.getNumDecimals();
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getSchemaBaseUrl() {
	    return geoServer.getSchemaBaseUrl();
	}
	/**
	 * wether xml documents should be pretty formatted
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isVerbose() {
	    return geoServer.isVerbose();
	}
    /** DOCUMENT ME! */
    private static final Logger LOGGER = Logger.getLogger(
            "org.vfny.geoserver.global");
            
    public static final String NAME = "GeoServer";

    /** server configuration singleton */
    //private static GeoServer serverConfig;

    /** DOCUMENT ME! */
    private WMS wms;

    /** DOCUMENT ME! */
    private WFS wfs;

    /** DOCUMENT ME! */
    private Data data;

    /** Validation Configuration */
    private Validation validation;
    private String rootDir;

	/**
	 * GeoServer constructor.
	 * <p>
	 * To be called by the Struts ActionServlet
	 * </p>
	 *
	 */
	public GeoServer(){
		rootDir = null;
		wms = null;
		wfs = null;
		data = null;
		validation = null;
		geoServer = null;
	}

    /**
     * Creates a new GeoServer object.
     *
     * @param rootDir The directory on the computer running geoserver where
     *        geoserver is located.
     *
     * @throws ConfigurationException For any configuration problems.
     */
    /*private GeoServer(String rootDir) throws ConfigurationException {
        File f = new File(rootDir);
		XMLConfigReader cr = null;
        cr = new XMLConfigReader(f);
		this.rootDir = rootDir;
		
		if(cr.isInitialized()){	
			geoServer = cr.getGeoServer();
			wfs = new WFS(cr.getWfs());
			wms = new WMS(cr.getWms());
			data = new Data(cr.getData());
			validation = new Validation();
		}else
			throw new ConfigurationException("An error occured loading the initial configuration.");
    }*/

    /**
     * Creates a new GeoServer Object for JUnit testing.
     * 
     * <p>
     * Configure based on provided Map, and Data.
     * </p>
     * 
     * <p>
     * GeoServer understands the followin entries in config map:
     * 
     * <ul>
     * <li>
     * dir: File (default to current directory
     * </li>
     * </ul>
     * </p>
     *
     * @param config a map of the config params.
     *
     * @throws ConfigurationException DOCUMENT ME!
     */
	/*private GeoServer(WMSDTO wms, WFSDTO wfs, GeoServerDTO geoServer, DataDTO data) throws ConfigurationException {
		if(rootDir == null)
			throw new ConfigurationException("RootDir not specified, server was not loaded initially from configuration files.");
		this.geoServer = geoServer;
		this.wfs = new WFS(wfs);
		this.wms = new WMS(wms);
		this.data = new Data(data);
		this.validation = new Validation();
	}*/

	private static ServletContext sc = null;
	public static GeoServer getInstance(){
		if(sc==null)
			return null;
		return (GeoServer)sc.getAttribute(NAME);
	}

    /**
     * Gets the config for the WMS.
     *
     * @return DOCUMENT ME!
     */
    public WMS getWMS() {
        return wms;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public WFS getWFS() {
        return wfs;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Data getData() {
        return data;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Validation getValidationConfig() {
        return validation;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws IllegalStateException DOCUMENT ME!
     */
    /*public static GeoServer getInstance() {
        if (serverConfig == null) {
            throw new IllegalStateException(
                "The server configuration has not been loaded yet");
        }

        return serverConfig;
    }*/

    /**
     * DOCUMENT ME!
     *
     * @param rootDir DOCUMENT ME!
     *
     * @throws ConfigurationException DOCUMENT ME!
     */
    /*public static void load(String rootDir) throws ConfigurationException {
        serverConfig = new GeoServer(rootDir);
    }*/

	/**
	 * DOCUMENT ME!
	 *
	 * @param config DOCUMENT ME!
	 *
	 * @throws ConfigurationException DOCUMENT ME!
	 */
	public void load(WMSDTO wms, WFSDTO wfs, GeoServerDTO geoServer, DataDTO data) throws ConfigurationException {
		//serverConfig = new GeoServer(wms,wfs,geoServer,data);
		if(rootDir == null)
			throw new ConfigurationException("RootDir not specified, server was not loaded initially from configuration files.");
		this.geoServer = geoServer;
		if(wfs!=null)
			this.wfs = new WFS(wfs);
		if(wms!=null)
			this.wms = new WMS(wms);
		if(data!=null)
			this.data = new Data(data);
		this.validation = new Validation();
	}

    /**
     * saves the server configuration to <code>dest</code> as an XML stream
     *
     * @TODO method body
     *
     * @param destDir DOCUMENT ME!
     */
    public void save(String destDir) throws ConfigurationException{
    	File dest = new File(destDir);
		XMLConfigWriter.store((WMSDTO)wms.getDTO(),(WFSDTO)wfs.getDTO(),(GeoServerDTO)geoServer,(DataDTO)data.getDTO(),dest);    }

	public static WMSDTO getDTO(WMS wms){
		return (WMSDTO)((WMSDTO)wms.getDTO()).clone();
	}

	public static WFSDTO getDTO(WFS wfs){
		return (WFSDTO)((WFSDTO)wfs.getDTO()).clone();
	}

	public static GeoServerDTO getDTO(GeoServer gs){
		return (GeoServerDTO)((GeoServerDTO)gs.getDTO()).clone();
	}

	public static DataDTO getDTO(Data dt){
		return (DataDTO)((DataDTO)dt.getDTO()).clone();
	}

	public WMSDTO getWMSDTO(){
		return getDTO(wms);
	}

	public WFSDTO getWFSDTO(){
		return getDTO(wfs);
	}

	public GeoServerDTO getGeoServerDTO(){
		return (GeoServerDTO)geoServer.clone();
	}

	public DataDTO getDataDTO(){
		return getDTO(data);
	}

	Object getDTO(){
		return geoServer;
	}
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getRootDir() {
        return rootDir;
    }
    
    public void destroy(){
    	// do nothing
    }
    
    public void init(ActionServlet as, ModuleConfig mc) throws javax.servlet.ServletException{
    	init((HttpServlet)as);
    }
    public void init(HttpServlet hs) throws javax.servlet.ServletException{
    	sc = hs.getServletContext();
    	rootDir = sc.getRealPath("/");
    	
    	try{
			File f = new File(rootDir);
			XMLConfigReader cr = new XMLConfigReader(f);
		
			if(cr.isInitialized()){	
				geoServer = cr.getGeoServer();
				wfs = new WFS(cr.getWfs());
				wms = new WMS(cr.getWms());
				data = new Data(cr.getData());
				validation = new Validation();
			}else
				throw new ConfigurationException("An error occured loading the initial configuration.");
    	}catch(ConfigurationException e){
    		throw new ServletException(e);
    	}
		sc.setAttribute(NAME,this);
    }
}

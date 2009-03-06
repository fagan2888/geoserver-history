/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.wfs.xml.v1_1_0;

import java.util.Iterator;

import javax.xml.namespace.QName;

import net.opengis.wfs.FeatureCollectionType;
import net.opengis.wfs.WfsFactory;

import org.geoserver.feature.CompositeFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.gml3.GML;
import org.geotools.gml3.GMLConfiguration;
import org.geotools.xml.AbstractComplexEMFBinding;
import org.geotools.xml.Configuration;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.vfny.geoserver.global.Data;
import org.vfny.geoserver.global.FeatureTypeInfo;


/**
 * Binding object for the type http://www.opengis.net/wfs:FeatureCollectionType.
 *
 * <p>
 *        <pre>
 *         <code>
 *  &lt;xsd:complexType name="FeatureCollectionType"&gt;
 *      &lt;xsd:annotation&gt;
 *          &lt;xsd:documentation&gt;
 *              This type defines a container for the response to a
 *              GetFeature or GetFeatureWithLock request.  If the
 *              request is GetFeatureWithLock, the lockId attribute
 *              must be populated.  The lockId attribute can otherwise
 *              be safely ignored.
 *           &lt;/xsd:documentation&gt;
 *      &lt;/xsd:annotation&gt;
 *      &lt;xsd:complexContent&gt;
 *          &lt;xsd:extension base="gml:AbstractFeatureCollectionType"&gt;
 *              &lt;xsd:attribute name="lockId" type="xsd:string" use="optional"&gt;
 *                  &lt;xsd:annotation&gt;
 *                      &lt;xsd:documentation&gt;
 *                    The value of the lockId attribute is an identifier
 *                    that a Web Feature Service generates when responding
 *                    to a GetFeatureWithLock request.  A client application
 *                    can use this value in subsequent operations (such as a
 *                    Transaction request) to reference the set of locked
 *                    features.
 *                 &lt;/xsd:documentation&gt;
 *                  &lt;/xsd:annotation&gt;
 *              &lt;/xsd:attribute&gt;
 *              &lt;xsd:attribute name="timeStamp" type="xsd:dateTime" use="optional"&gt;
 *                  &lt;xsd:annotation&gt;
 *                      &lt;xsd:documentation&gt;
 *                    The timeStamp attribute should contain the date and time
 *                    that the response was generated.
 *                 &lt;/xsd:documentation&gt;
 *                  &lt;/xsd:annotation&gt;
 *              &lt;/xsd:attribute&gt;
 *              &lt;xsd:attribute name="numberOfFeatures"
 *                  type="xsd:nonNegativeInteger" use="optional"&gt;
 *                  &lt;xsd:annotation&gt;
 *                      &lt;xsd:documentation&gt;
 *                    The numberOfFeatures attribute should contain a
 *                    count of the number of features in the response.
 *                    That is a count of all features elements dervied
 *                    from gml:AbstractFeatureType.
 *                 &lt;/xsd:documentation&gt;
 *                  &lt;/xsd:annotation&gt;
 *              &lt;/xsd:attribute&gt;
 *          &lt;/xsd:extension&gt;
 *      &lt;/xsd:complexContent&gt;
 *  &lt;/xsd:complexType&gt;
 *
 *          </code>
 *         </pre>
 * </p>
 *
 * @generated
 */
public class FeatureCollectionTypeBinding extends AbstractComplexEMFBinding {
    WfsFactory wfsfactory;
    Data catalog;
    boolean generateBounds;

    public FeatureCollectionTypeBinding(WfsFactory wfsfactory, Data catalog, Configuration configuration) {
        this.wfsfactory = wfsfactory;
        this.catalog = catalog;
        this.generateBounds = !configuration.getProperties().contains(GMLConfiguration.NO_FEATURE_BOUNDS);
    }

    public int getExecutionMode() {
        return OVERRIDE;
    }

    /**
     * @generated
     */
    public QName getTarget() {
        return WFS.FEATURECOLLECTIONTYPE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Class getType() {
        return FeatureCollectionType.class;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Object parse(ElementInstance instance, Node node, Object value)
        throws Exception {
        return value;
    }

    public Object getProperty(Object object, QName name)
        throws Exception {
        //check for feature collection memebers
        if (GML.featureMembers.equals(name)) {
            FeatureCollectionType featureCollection = (FeatureCollectionType) object;

            if (!featureCollection.getFeature().isEmpty()) {
                if (featureCollection.getFeature().size() > 1) {
                    //wrap in a single
                    return new CompositeFeatureCollection(featureCollection.getFeature());
                }

                //just return the single
                return featureCollection.getFeature().iterator().next();
            }
        } else if(GML.boundedBy.equals(name) && generateBounds) {
            FeatureCollectionType featureCollection = (FeatureCollectionType) object;

            ReferencedEnvelope env = null;
            for (Iterator it = featureCollection.getFeature().iterator(); it.hasNext();) {
                FeatureCollection fc = (FeatureCollection) it.next();
                if(env == null) {
                    env = fc.getBounds();
                }
                else {
                    env.expandToInclude(fc.getBounds());
                }
                
                // workaround bogus collection implementation that won't return the crs
                if(env != null &&  env.getCoordinateReferenceSystem() == null) {
                    CoordinateReferenceSystem crs = fc.getSchema().getCoordinateReferenceSystem();
                    if ( crs == null ) {
                        //fall back on catalog
                        FeatureTypeInfo info = catalog.getFeatureTypeInfo(fc.getSchema().getName());
                        if ( info != null ) {
                            crs = info.getDeclaredCRS();
                        }
                    }
                    env = new ReferencedEnvelope(env, crs);
                }
                
                if ( env != null ) {
                    //JD: here we don't return the envelope if it is null or empty, this is to work 
                    // around and issue with validation in the cite engine. I have opened a jira task 
                    // to track this, and hopefully eventually fix the cite engine
                    //    http://jira.codehaus.org/browse/GEOS-2700
                    return !( env.isNull() || env.isEmpty() ) ? env : null; 
                }
            }
        }

        //delegate to parent lookup
        return super.getProperty(object, name);
    }
}

/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.kml;

import java.util.Map;
import java.util.logging.Level;

import org.geoserver.catalog.FeatureTypeInfo;
import org.geoserver.config.GeoServer;
import org.geoserver.platform.ServiceException;
import org.geoserver.wms.MapLayerInfo;
import org.geoserver.wms.WMSMapContext;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeType;
import org.opengis.feature.type.FeatureType;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

/**
 * Strategy using geometry size to determine feature allocation in tiles. Bigger
 * geometries get into the bigger tiles. Does not work with simple points, use
 * attribute sorting or random strategy in that case
 * 
 * @author Andrea Aime
 */
public class GeometryRegionatingStrategy extends
        ExternalSortRegionatingStrategy {

    public GeometryRegionatingStrategy(GeoServer gs) {
        super(gs);
    }

    @Override
    protected void checkAttribute(WMSMapContext con, SimpleFeatureType ft) {
        // find out which attribute we're going to use
        Map options = con.getRequest().getFormatOptions();
        attribute = (String) options.get("regionateAttr");
        if (attribute == null) {
            attribute = MapLayerInfo.getRegionateAttribute(featureType);
        }
        if (attribute == null || ft.getDescriptor(attribute) == null) {
            LOGGER.log(Level.FINER, "No attribute specified, falling "
                    + "back on geometry attribute");
            attribute = ft.getGeometryDescriptor().getLocalName();
        } else {
            // Make sure the attribute is actually there
            AttributeType attributeType = ft.getType(attribute);
            if (attributeType == null) {
                throw new ServiceException("Could not find regionating attribute "
                        + attribute + " in layer " + featureType.getName());
            }
        }

        // geometry size is a double
        h2Type = "DOUBLE";
    }

    @Override 
    protected String checkAttribute(FeatureTypeInfo cfg){
        String attribute = MapLayerInfo.getRegionateAttribute(cfg);
        try{
            FeatureType ft = cfg.getFeatureType();
            if ((attribute != null) && (ft.getDescriptor(attribute) != null))
                return attribute;

            return ft.getGeometryDescriptor().getLocalName();
        } catch (Exception e) {
            LOGGER.severe("Couldn't get attribute name due to " + e);
            return null;
        }
    }

    @Override
    protected Double getSortAttributeValue(SimpleFeature f) {
        Geometry g = (Geometry) f.getAttribute(attribute);

        if (g instanceof MultiPoint)
            return (double) ((MultiPoint) g).getNumGeometries();
        if (g instanceof Polygon || g instanceof MultiPolygon)
            return g.getArea();
        else
            return g.getLength();
    }

}

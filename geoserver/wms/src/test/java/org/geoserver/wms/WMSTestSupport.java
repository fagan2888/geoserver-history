/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.wms;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.geoserver.test.GeoServerTestSupport;
import org.geotools.map.DefaultMapLayer;
import org.geotools.map.MapLayer;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.styling.Style;
import org.vfny.geoserver.global.FeatureTypeInfo;
import org.vfny.geoserver.global.MapLayerInfo;
import org.vfny.geoserver.global.WMS;
import org.vfny.geoserver.wms.requests.GetMapRequest;
import org.vfny.geoserver.wms.servlets.GetMap;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.vividsolutions.jts.geom.Envelope;


/**
 * Base support class for wms tests.
 * <p>
 * Deriving from this test class provides the test case with preconfigured
 * geoserver and wms objects.
 * </p>
 * @author Justin Deoliveira, The Open Planning Project, jdeolive@openplans.org
 *
 */
public class WMSTestSupport extends GeoServerTestSupport {
   
    /**
     * @return The global wms singleton from the application context.
     */
    protected WMS getWMS() {
        return (WMS) applicationContext.getBean("wms");
    }

    /**
     * Convenience method for subclasses to create a map layer from a layer name.
     * <p>
     * The map layer is created with the default style for the layer.
     * </p>
     * @param layerName The name of the layer.
     *
     * @return A new map layer.
     */
    protected MapLayer createMapLayer(QName layerName)
        throws IOException {
        //TODO: support coverages
        FeatureTypeInfo info = getCatalog().getFeatureTypeInfo(layerName);
        Style style = info.getDefaultStyle();

        DefaultMapLayer layer = new DefaultMapLayer(info.getFeatureSource(), style);
        layer.setTitle( info.getTypeName() );
        
        return layer;
    }

    /**
     * Calls through to {@link #createGetMapRequest(QName[])}.
     *
     */
    protected GetMapRequest createGetMapRequest(QName layerName) {
        return createGetMapRequest(new QName[] { layerName });
    }

    /**
     * Convenience method for subclasses to create a new GetMapRequest object.
     * <p>
     * The returned object has the following properties:
     *  <ul>
     *    <li>styles set to default styles for layers specified
     *    <li>bbox set to (-180,-90,180,180 )
     *    <li>crs set to epsg:4326
     *  </ul>
     *  Caller must set additional parameters of request as need be.
     * </p>
     *
     * @param The layer names of the request.
     *
     * @return A new GetMapRequest object.
     */
    protected GetMapRequest createGetMapRequest(QName[] layerNames) {
        GetMapRequest request = new GetMapRequest(new GetMap(getWMS()));
        request.setHttpServletRequest(createRequest("wms"));

        MapLayerInfo[] layers = new MapLayerInfo[layerNames.length];
        List styles = new ArrayList();

        for (int i = 0; i < layerNames.length; i++) {
            FeatureTypeInfo ftInfo = getCatalog().getFeatureTypeInfo(layerNames[i]);
            styles.add(ftInfo.getDefaultStyle());

            layers[i] = new MapLayerInfo(ftInfo);
        }

        request.setLayers(layers);
        request.setStyles(styles);
        request.setBbox(new Envelope(-180, -90, 180, 90));
        request.setCrs(DefaultGeographicCRS.WGS84);
        request.setSRS("EPSG:4326");
        request.setRawKvp(new HashMap());
        return request;
    }
    
    
    /**
     * Asserts that the image is not blank, in the sense that there must be
     * pixels different from the passed background color.
     *
     * @param testName the name of the test to throw meaningfull messages if
     *        something goes wrong
     * @param image the imgage to check it is not "blank"
     * @param bgColor the background color for which differing pixels are
     *        looked for
     */
    protected void assertNotBlank(String testName, BufferedImage image, Color bgColor) {
        int pixelsDiffer = 0;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (image.getRGB(x, y) != bgColor.getRGB()) {
                    ++pixelsDiffer;
                }
            }
        }

        LOGGER.info(testName + ": pixel count=" + (image.getWidth() * image.getHeight())
            + " non bg pixels: " + pixelsDiffer);
        assertTrue(testName + " image is comlpetely blank", 0 < pixelsDiffer);
    }
    
}

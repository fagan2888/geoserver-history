/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.wfs.v1_1;

import net.opengis.wfs.DescribeFeatureTypeType;
import net.opengis.wfs.GetFeatureType;
import net.opengis.wfs.WfsFactory;
import org.geoserver.data.test.MockData;
import org.geoserver.platform.Operation;
import org.geoserver.platform.Service;
import org.geoserver.util.ReaderUtils;
import org.geoserver.wfs.WFSTestSupport;
import org.geoserver.wfs.xml.v1_1_0.XmlSchemaEncoder;
import org.vfny.geoserver.global.Data;
import org.vfny.geoserver.global.FeatureTypeInfo;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;


public class DescribeFeatureResponseTest extends WFSTestSupport {
    Operation request() {
        Service service = new Service("wfs", null, null);
        DescribeFeatureTypeType type = WfsFactory.eINSTANCE.createDescribeFeatureTypeType();
        type.setBaseUrl("http://localhost:8080/geoserver");

        Operation request = new Operation("wfs", service, null, new Object[] { type });

        return request;
    }

    public void testSingle() throws Exception {
        Data catalog = getCatalog();
        FeatureTypeInfo meta = catalog.getFeatureTypeInfo(MockData.BASIC_POLYGONS);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        XmlSchemaEncoder response = new XmlSchemaEncoder(getWFS(), catalog, getResourceLoader());
        response.write(new FeatureTypeInfo[] { meta }, output, request());

        Element schema = ReaderUtils.parse(new StringReader(new String(output.toByteArray())));
        assertEquals("xsd:schema", schema.getNodeName());

        NodeList types = schema.getElementsByTagName("xsd:complexType");
        assertEquals(1, types.getLength());
    }

    public void testWithDifferntNamespaces() throws Exception {
        FeatureTypeInfo meta1 = getCatalog().getFeatureTypeInfo(MockData.BASIC_POLYGONS);
        FeatureTypeInfo meta2 = getCatalog().getFeatureTypeInfo(MockData.POLYGONS);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        XmlSchemaEncoder response = new XmlSchemaEncoder(getWFS(), getCatalog(), getResourceLoader());
        response.write(new FeatureTypeInfo[] { meta1, meta2 }, output, request());

        Element schema = ReaderUtils.parse(new StringReader(new String(output.toByteArray())));
        assertEquals("xsd:schema", schema.getNodeName());

        NodeList imprts = schema.getElementsByTagName("xsd:import");
        assertEquals(2, imprts.getLength());
    }
}

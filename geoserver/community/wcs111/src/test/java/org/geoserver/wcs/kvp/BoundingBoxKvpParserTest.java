package org.geoserver.wcs.kvp;

import static org.vfny.geoserver.wcs.WcsException.WcsExceptionCode.InvalidParameterValue;
import junit.framework.TestCase;

import net.opengis.ows.v1_1_0.BoundingBoxType;

import org.vfny.geoserver.wcs.WcsException;

public class BoundingBoxKvpParserTest extends TestCase {
    BoundingBoxKvpParser parser = new BoundingBoxKvpParser();
    
    public void test1DRange() throws Exception {
        executeFailingBBoxTest("10", "This bbox was invalid?");
        executeFailingBBoxTest("10,20", "This bbox was invalid?");
        executeFailingBBoxTest("10,20,30", "This bbox was invalid?");
    }
    
    public void testNonNumericalRange() throws Exception {
        executeFailingBBoxTest("10,20,a,b", "This bbox was invalid?");
        executeFailingBBoxTest("a,20,30,b", "This bbox was invalid?");
    }
    
    public void testOutOfDimRange() throws Exception {
        executeFailingBBoxTest("10,20,30,40,50,60,EPSG:4326", "This bbox has more dimensions than the crs?");
        executeFailingBBoxTest("10,20,30,40,EPSG:4979", "This bbox has less dimensions than the crs?");
    }
    
    public void testUnknownCRS() throws Exception {
        executeFailingBBoxTest("10,20,30,40,50,60,EPSG:MakeNoPrisoners!", "This crs should definitely be unknown...");
    }
    
    void executeFailingBBoxTest(String bbox, String message) throws Exception {
        try {
            parser.parse(bbox);
            fail(message);
        } catch(WcsException e) {
            assertEquals(InvalidParameterValue.toString(), e.getCode());
            assertEquals("BoundingBox", e.getLocator());
        }
    }
    
    public void testNoCrs() throws Exception {
        BoundingBoxType bbox = (BoundingBoxType) parser.parse("10,20,15,30");
        assertEquals(2, bbox.getLowerCorner().size());
        assertEquals(10.0, bbox.getLowerCorner().get(0));
        assertEquals(15.0, bbox.getLowerCorner().get(1));
        assertEquals(2, bbox.getUpperCorner().size());
        assertEquals(20.0, bbox.getUpperCorner().get(0));
        assertEquals(30.0, bbox.getUpperCorner().get(1));
        assertNull(bbox.getCrs());
    }
    
    public void test2DNoCrs() throws Exception {
        BoundingBoxType bbox = (BoundingBoxType) parser.parse("10,20,15,30,EPSG:4326");
        assertEquals(2, bbox.getLowerCorner().size());
        assertEquals(10.0, bbox.getLowerCorner().get(0));
        assertEquals(15.0, bbox.getLowerCorner().get(1));
        assertEquals(2, bbox.getUpperCorner().size());
        assertEquals(20.0, bbox.getUpperCorner().get(0));
        assertEquals(30.0, bbox.getUpperCorner().get(1));
        assertEquals("EPSG:4326", bbox.getCrs());
    }
    
    public void test3DNoCrs() throws Exception {
        BoundingBoxType bbox = (BoundingBoxType) parser.parse("10,20,15,30,0,10,EPSG:4979");
        assertEquals(3, bbox.getLowerCorner().size());
        assertEquals(10.0, bbox.getLowerCorner().get(0));
        assertEquals(15.0, bbox.getLowerCorner().get(1));
        assertEquals(0.0, bbox.getLowerCorner().get(2));
        assertEquals(3, bbox.getUpperCorner().size());
        assertEquals(20.0, bbox.getUpperCorner().get(0));
        assertEquals(30.0, bbox.getUpperCorner().get(1));
        assertEquals(10.0, bbox.getUpperCorner().get(2));
        assertEquals("EPSG:4979", bbox.getCrs());
    }
    
    
}

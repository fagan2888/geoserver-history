/* Copyright (c) 2001, 2003 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.zserver;

import com.k_int.IR.IRQuery;
import com.k_int.IR.InformationFragment;
import com.k_int.IR.QueryModels.RPNTree;
import com.k_int.IR.RecordFormatSpecification;
import com.k_int.IR.SearchException;
import com.k_int.IR.SearchTask;
import com.k_int.util.RPNQueryRep.AttrPlusTermNode;
import com.k_int.util.RPNQueryRep.RootNode;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.util.Properties;
import java.util.logging.Logger;


/**
 * Tests the GeoSearchable and GeoSearchTask classes.
 *
 * @author Chris Holmes, TOPP
 * @version $Id: GeoSearchSuite.java,v 1.8 2004/04/02 11:35:45 cholmesny Exp $
 */
public class GeoSearchSuite extends TestCase {
    /* Initializes the logger. Uncomment to see log messages.*/

    //static {
    //    org.vfny.geoserver.config.Log4JFormatter.init("org.vfny.geoserver", 
    // java.util.logging.Level.FINEST);
    //}

    /** Standard logging instance */
    private static final Logger LOGGER = Logger.getLogger(
            "org.vfny.geoserver.zserver");
    private static final String BASE_DIR = System.getProperty("user.dir");

    /** Unit test data directory */
    private static final String DATA_DIRECTORY = BASE_DIR
        + "/test/test-data/zserver";
    private static final String INDEX_DIR = DATA_DIRECTORY + "/index";
    private static final String ATTRIBUTE_MAP = DATA_DIRECTORY + "/geo.map";
    private Properties testProps;
    private IRQuery testQuery;
    private RootNode root1;
    private RootNode root2;
    private AttrPlusTermNode rpn1;
    private AttrPlusTermNode rpn2;
    private GeoSearchable geoSource;
    private RecordFormatSpecification spec;

    /**
     * Initializes the database and request handler.
     *
     * @param testName DOCUMENT ME!
     */
    public GeoSearchSuite(String testName) {
        super(testName);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(GeoSearchSuite.class);
        LOGGER.fine("Creating GeoSearch suite.");

        return suite;
    }

    public void setUp() {
        testProps = new Properties();
        testProps.setProperty("database", INDEX_DIR);
        testProps.setProperty("datafolder", DATA_DIRECTORY);
        GeoProfile.setUseAttrMap(ATTRIBUTE_MAP);

        //To make sure the db is made.
        GeoIndexer indexer = new GeoIndexer(testProps);
        int numIndexed = indexer.update();
        root1 = new RootNode();
        root2 = new RootNode();
        rpn1 = new AttrPlusTermNode(root1);
        rpn1.setAttr(null, new Integer(GeoProfile.USE),
            new Integer(GeoProfile.Attribute.ANY));
        rpn1.setTerm("water");
        root1.setChild(rpn1);
        testQuery = new IRQuery(new RPNTree(root1), "default");
    }

    public void testCreateTask() {
        geoSource = new GeoSearchable();
        geoSource.init(testProps);

        SearchTask task = geoSource.createTask(testQuery, null);
        assertTrue(task.getQuery().equals(testQuery));
    }

    public void testEvaluate1() throws SearchException {
        geoSource = new GeoSearchable();
        geoSource.init(testProps);

        GeoSearchTask task = (GeoSearchTask) geoSource.createTask(testQuery,
                null);
        task.evaluate(2000);
        assertEquals(task.getFragmentCount(), 2);
    }

    public void testEvaluate2() throws SearchException {
        geoSource = new GeoSearchable();
        geoSource.init(testProps);
        rpn2 = new AttrPlusTermNode(root2);
        rpn2.setAttr(null, new Integer(GeoProfile.USE),
            new Integer(GeoProfile.Attribute.ORIGIN));
        rpn2.setTerm("rolf");
        root2.setChild(rpn2);

        IRQuery testQuery2 = new IRQuery(new RPNTree(root2), "default");
        GeoSearchTask task = (GeoSearchTask) geoSource.createTask(testQuery2,
                null);
        task.evaluate(2000);
        assertEquals(1, task.getFragmentCount());
    }

    public void testRetrieval1() throws SearchException {
        geoSource = new GeoSearchable();
        geoSource.init(testProps);

        GeoSearchTask task = (GeoSearchTask) geoSource.createTask(testQuery,
                null);
        task.evaluate(2000);
        spec = new RecordFormatSpecification("sutrs", null, "B");

        InformationFragment frag = task.getFragment(1, spec);
        String result = frag.getOriginalObject().toString();
        LOGGER.fine("the result is " + result);
        assertTrue(result.equals("Water Supply Watersheds"));

        spec = new RecordFormatSpecification("html", null, "S");

        InformationFragment[] fragArr = task.getFragment(1, 2, spec);
        String result2 = fragArr[1].getOriginalObject().toString();
        LOGGER.fine("the result is " + result2);
    }

    public void testRetrieval2() throws SearchException {
        geoSource = new GeoSearchable();
        geoSource.init(testProps);

        GeoSearchTask task = (GeoSearchTask) geoSource.createTask(testQuery,
                null);
        task.evaluate(2000);

        spec = new RecordFormatSpecification("html", null, "S");

        InformationFragment[] fragArr = task.getFragment(1, 2, spec);
        String result2 = fragArr[1].getOriginalObject().toString();
        LOGGER.fine("the result is " + result2);
        assertTrue(result2.substring(0, 16).equals("<P><B>TITLE=Dust"));
    }

    public void testRetrieval3() throws SearchException {
        geoSource = new GeoSearchable();
        geoSource.init(testProps);

        GeoSearchTask task = (GeoSearchTask) geoSource.createTask(testQuery,
                null);
        task.evaluate(2000);

        spec = new RecordFormatSpecification("xml", null, "F");

        InformationFragment[] fragArr = task.getFragment(1, 1, spec);
        String result2 = fragArr[0].getOriginalObject().toString();
        LOGGER.fine("the result is " + result2);

        //TODO: assert here, to make sure result is good, but this
        //still tests that the function works.
    }
}

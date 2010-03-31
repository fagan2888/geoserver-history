package org.geoserver.gss;

import static org.custommonkey.xmlunit.XMLAssert.*;
import static org.geoserver.gss.DefaultGeoServerSynchronizationService.*;

import java.util.Map;

import org.geotools.data.DataUtilities;
import org.geotools.data.FeatureStore;
import org.geotools.data.VersioningDataStore;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.w3c.dom.Document;

import com.mockrunner.mock.web.MockHttpServletResponse;

public class GetCentralRevisionTest extends GSSTestSupport {
    
    @Override
    protected void setUpInternal() throws Exception {
        super.setUpInternal();
        
        // initialize the GSS service
        Map gssBeans = applicationContext.getBeansOfType(DefaultGeoServerSynchronizationService.class);
        DefaultGeoServerSynchronizationService gss = (DefaultGeoServerSynchronizationService) gssBeans.values().iterator().next();
        gss.ensureUnitEnabled();
        
        VersioningDataStore synch = (VersioningDataStore) getCatalog().getDataStoreByName("synch").getDataStore(null);
        assertNotNull(synch.getSchema(SYNCH_HISTORY));
        assertFalse(synch.isVersioned(SYNCH_HISTORY));
        assertNotNull(synch.getSchema(SYNCH_TABLES));
        assertFalse(synch.isVersioned(SYNCH_TABLES));

        FeatureStore<SimpleFeatureType, SimpleFeature> fs = (FeatureStore<SimpleFeatureType, SimpleFeature>) synch.getFeatureSource(SYNCH_TABLES);
        SimpleFeatureBuilder fb = new SimpleFeatureBuilder(fs.getSchema());
        fs.addFeatures(DataUtilities.collection(fb.buildFeature(null, new Object[] {"restricted", "2"})));
        fs.addFeatures(DataUtilities.collection(fb.buildFeature(null, new Object[] {"roads", "2"})));
        
        fs = (FeatureStore<SimpleFeatureType, SimpleFeature>) synch.getFeatureSource(SYNCH_HISTORY);
        fb = new SimpleFeatureBuilder(fs.getSchema());
        // three synchs occurred on this layer
        fs.addFeatures(DataUtilities.collection(fb.buildFeature(null, new Object[] {"roads", 150, 160})));
        fs.addFeatures(DataUtilities.collection(fb.buildFeature(null, new Object[] {"roads", 182, 210})));
        fs.addFeatures(DataUtilities.collection(fb.buildFeature(null, new Object[] {"roads", 193, 340})));
        // and none on restricted
    }
    
    /**
     * Tests a layer that is not there
     * @throws Exception
     */
    public void testCentralRevisionUnknownLayer() throws Exception {
        MockHttpServletResponse response = getAsServletResponse(root() + "service=gss&request=GetCentralRevision&typeName=sf:missing");
        validate(response);
        Document doc = dom(response);
        // print(doc);
        checkOwsException(doc);
    }
    
    /**
     * Tests a layer that is there, but not in the synch tables
     * @throws Exception
     */
    public void testCentralRevisionLocalLayer() throws Exception {
        MockHttpServletResponse response = getAsServletResponse(root() + "service=gss&request=GetCentralRevision&typeName=sf:archsites");
        validate(response);
        Document doc = dom(response);
        // print(doc);
        checkOwsException(doc);
    }
    

    public void testCentralRevisionRestricted() throws Exception {
        MockHttpServletResponse response = getAsServletResponse(root() + "service=gss&request=GetCentralRevision&typeName=sf:restricted");
        validate(response);
        Document doc = dom(response);
        // print(doc);
        
        // no synchronization, give me everything back
        assertXpathEvaluatesTo("-1", "//gss:CentralRevisions/gss:LayerRevision/@centralRevision", doc);
    }
    
    public void testCentralRevisionRoads() throws Exception {
        MockHttpServletResponse response = getAsServletResponse(root() + "service=gss&request=GetCentralRevision&typeName=sf:roads");
        validate(response);
        Document doc = dom(response);
        // print(doc);
        
        assertXpathEvaluatesTo("340", "//gss:CentralRevisions/gss:LayerRevision/@centralRevision", doc);
    }
    
}

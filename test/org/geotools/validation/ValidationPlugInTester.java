package org.geotools.validation;

import java.util.HashMap;

import org.geotools.data.DataTestCase;
import org.geotools.data.DataUtilities;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.feature.Feature;
import org.geotools.feature.IllegalAttributeException;
import org.geotools.validation.spatial.LineIsSingleSegmentFeatureValidation;
import org.geotools.validation.spatial.LineNoSelfIntersectFeatureValidation;
import org.geotools.validation.spatial.LineNoSelfOverlappingFeatureValidation;
import org.geotools.validation.spatial.LinesNotIntersectIntegrityValidation;

/*
 *    Geotools2 - OpenSource mapping toolkit
 *    http://geotools.org
 *    (C) 2003, Geotools Project Managment Committee (PMC)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 *
 */

/**
 * ValidationPlugInTester purpose.
 * <p>
 * Description of ValidationPlugInTester ...
 * <p>
 * Capabilities:
 * <ul>
 * </li></li>
 * </ul>
 * Example Use:
 * <pre><code>
 * ValidationPlugInTester x = new ValidationPlugInTester(...);
 * </code></pre>
 * 
 * @author bowens, Refractions Research, Inc.
 * @author $Author: dmzwiers $ (last modification)
 * @version $Id: ValidationPlugInTester.java,v 1.3 2004/01/12 21:01:29 dmzwiers Exp $
 */
public class ValidationPlugInTester extends DataTestCase {

	MemoryDataStore store;
	ValidationProcessor processor;
	
	/**
	 * ValidationPlugInTester constructor.
	 * <p>
	 * Description
	 * </p>
	 * @param arg0
	 */
	public ValidationPlugInTester(String arg0) {
		super(arg0);
		
	}
	
	/**
	 * Construct data store for use.
	 * 
	 * @see junit.framework.TestCase#setUp()
	 * 
	 * @throws Exception
	 */
	protected void setUp() throws Exception {
		super.setUp();
		store = new MemoryDataStore();
		store.addFeatures( roadFeatures );
		store.addFeatures( riverFeatures );
		processor = new ValidationProcessor();
	}

	/**
	 * Override tearDown.
	 *
	 * @see junit.framework.TestCase#tearDown()
	 * 
	 * @throws Exception
	 */
	protected void tearDown() throws Exception {
		store = null;
		super.tearDown();
		
	}
		
		
	public void testLineNoSelfIntersectFV_CorrectData()
	{
		// the visitor
		RoadNetworkValidationResults roadValidationResults = new RoadNetworkValidationResults();
			 
		LineNoSelfIntersectFeatureValidation selfIntersectValidatorRoads 
					= new LineNoSelfIntersectFeatureValidation("RoadSelfIntersect", 
							"Tests to see if a road intersects itself, which is bad!", 
							new String[] {"road"});
		
		try {
			processor.addValidation(selfIntersectValidatorRoads);
		} catch (Exception e) {
			assertTrue(false);
		}
		
		try {
			processor.runFeatureTests(this.roadType, DataUtilities.collection(this.roadFeatures), roadValidationResults);
		} catch (Exception e1) {
			assertTrue(false);
		}
		
		System.out.println("NoLineSelfIntersect - correct");
		String[] messages = roadValidationResults.getFailedMessages();
		for (int i=0; i<messages.length; i++)
			System.out.println(messages[i]);
		assertTrue(roadValidationResults.getFailedMessages().length == 0);
		
	}
	
	public void testLineNoSelfIntersectFV_IncorrectData()
	{
		// the visitor
		RoadNetworkValidationResults roadValidationResults = new RoadNetworkValidationResults();
		 
		LineNoSelfIntersectFeatureValidation selfIntersectValidatorRoads 
					= new LineNoSelfIntersectFeatureValidation("RoadSelfIntersect", 
							"Tests to see if a road intersects itself, which is bad!", 
							new String[] {"road"});
	
		try {
			processor.addValidation(selfIntersectValidatorRoads);
		} catch (Exception e) {
			assertTrue(false);
		}
	
		// produce a broken road (newRoad)
		try {
			this.newRoad = this.roadType.create(new Object[] {
				new Integer(2), line(new int[] { 3, 6, 3, 8, 5, 8, 5, 7, 2, 7}), "r4"
			}, "road.rd4");
		} catch (IllegalAttributeException e) {}
		
		try {
			processor.runFeatureTests(this.roadType, DataUtilities.collection(new Feature[] {this.newRoad}), roadValidationResults);
			}
		catch (Exception e1) {
			assertTrue(false);
		}
	
		System.out.println("NoLineSelfIntersect - incorrect");
		String[] messages = roadValidationResults.getFailedMessages();
		for (int i=0; i<messages.length; i++)
			System.out.println(messages[i]);
		assertTrue(roadValidationResults.getFailedMessages().length > 0);
	
	}
	
	
	public void testLineNoSelfOverlapFV_CorrectData()
	{
		// the visitor
		RoadNetworkValidationResults roadValidationResults = new RoadNetworkValidationResults();
		 
		LineNoSelfOverlappingFeatureValidation selfOverlappingValidatorRoads 
					= new LineNoSelfOverlappingFeatureValidation("RoadSelfIntersect", 
							"Tests to see if a road intersects itself, which is bad!", 
							new String[] {"road"});
	
		try {
			processor.addValidation(selfOverlappingValidatorRoads);
		} catch (Exception e) {
			assertTrue(false);
		}
	
		try {
			processor.runFeatureTests(this.roadType, DataUtilities.collection(this.roadFeatures), roadValidationResults);
		} catch (Exception e1) {
			assertTrue(false);
		}
		
		System.out.println("NoLineSelfOverlap - correct");
		String[] messages = roadValidationResults.getFailedMessages();
		for (int i=0; i<messages.length; i++)
			System.out.println(messages[i]);
		assertTrue(roadValidationResults.getFailedMessages().length == 0);
	
	}
	
	public void testLineNoSelfOverlapFV_IncorrectData()
	{
		// the visitor
		RoadNetworkValidationResults roadValidationResults = new RoadNetworkValidationResults();
	 
		LineNoSelfOverlappingFeatureValidation selfOverlappingValidatorRoads 
					= new LineNoSelfOverlappingFeatureValidation("RoadSelfIntersect", 
							"Tests to see if a road intersects itself, which is bad!", 
							new String[] {"road"});

		try {
			processor.addValidation(selfOverlappingValidatorRoads);
		} catch (Exception e) {
			assertTrue(false);
		}

		// produce a broken road (newRoad)
		try {
			this.newRoad = this.roadType.create(new Object[] {
				new Integer(2), line(new int[] { 7, 7, 8, 7, 9, 7, 9, 6, 8, 6, 8, 7, 7, 7}), "r4"
			}, "road.rd4");
		} catch (IllegalAttributeException e) {}
	
		try {
			processor.runFeatureTests(this.roadType, DataUtilities.collection(new Feature[] {this.newRoad}), roadValidationResults);
			}
		catch (Exception e1) {
			assertTrue(false);
		}

		System.out.println("NoLineSelfOverlap - incorrect");
		String[] messages = roadValidationResults.getFailedMessages();
		for (int i=0; i<messages.length; i++)
			System.out.println(messages[i]);
		assertTrue(roadValidationResults.getFailedMessages().length > 0);

	}
	
	
	public void testLineIsSingleSegmentFV_CorrectData()
	{
		// the visitor
		RoadNetworkValidationResults roadValidationResults = new RoadNetworkValidationResults();
		 
		LineIsSingleSegmentFeatureValidation singleSegmentLineValidatorRoads 
					= new LineIsSingleSegmentFeatureValidation("RoadSelfIntersect", 
							"Tests to see if a road intersects itself, which is bad!", 
							new String[] {"road"});
	
		try {
			processor.addValidation(singleSegmentLineValidatorRoads);
		} catch (Exception e) {
			assertTrue(false);
		}
	
		try {
			processor.runFeatureTests(this.roadType, DataUtilities.collection(new Feature[] {this.newRoad}), roadValidationResults);
		} catch (Exception e1) {
			assertTrue(false);
		}
		
		System.out.println("LineIsSingleSegment - correct");
		String[] messages = roadValidationResults.getFailedMessages();
		for (int i=0; i<messages.length; i++)
			System.out.println(messages[i]);
		assertTrue(roadValidationResults.getFailedMessages().length == 0);
	
	}
	
	public void testLineIsSingleSegmentFV_IncorrectData()
	{
		// the visitor
		RoadNetworkValidationResults roadValidationResults = new RoadNetworkValidationResults();
	 
		LineIsSingleSegmentFeatureValidation singleSegmentLineValidatorRoads 
					= new LineIsSingleSegmentFeatureValidation("RoadSelfIntersect", 
							"Tests to see if a road intersects itself, which is bad!", 
							new String[] {"road"});

		try {
			processor.addValidation(singleSegmentLineValidatorRoads);
		} catch (Exception e) {
			assertTrue(false);
		}
	
		try {
			processor.runFeatureTests(this.roadType, DataUtilities.collection(this.roadFeatures), roadValidationResults);
			}
		catch (Exception e1) {
			assertTrue(false);
		}

		System.out.println("LineIsSingleSegment - incorrect");
		String[] messages = roadValidationResults.getFailedMessages();
		for (int i=0; i<messages.length; i++)
			System.out.println(messages[i]);
		assertTrue(roadValidationResults.getFailedMessages().length > 0);

	}


	public void testLinesIntersectIV_CorrectData()
	{
		// the visitor
		RoadNetworkValidationResults roadValidationResults = new RoadNetworkValidationResults();
		 
		LinesNotIntersectIntegrityValidation noIntersectingLinesValidatorAll 
					= new LinesNotIntersectIntegrityValidation("linesIntersect", 
							"Tests to see if any line geometries cross!", 
							Validation.ALL);
	
		try {
			processor.addValidation(noIntersectingLinesValidatorAll);
		} catch (Exception e) {
			assertTrue(false);
		}
		
		
//		Feature[] roads = new Feature[2];
//		roads[0] = roadFeatures[0];
//		roads[1] = newRoad;
//		roadFeatures = null;
//		roadFeatures = new Feature[2];
//		roadFeatures = roads;
//		
//		Feature[] rivers = new Feature[1];
//		rivers[0] = riverFeatures[1];
//		rivers[0] = newRiver;
//		riverFeatures = null;
//		riverFeatures = new Feature[2];
//		riverFeatures = roads;
//		
//		store = new MemoryDataStore();
//		store.addFeatures( roadFeatures );
//		store.addFeatures( riverFeatures );
		
		
		HashMap layers = new HashMap();
		try {
			layers.put("road", DataUtilities.source(new Feature[] {newRoad}));
			layers.put("river", DataUtilities.source(riverFeatures));
		}
		catch (Exception e) {
			assertTrue(false);
		}
	
	
		try {
			processor.runIntegrityTests(layers, null, roadValidationResults);
		} catch (Exception e1) {
			assertTrue(false);
		}
		
		System.out.println("NoLinesIntersect - correct");
		String[] messages = roadValidationResults.getFailedMessages();
		for (int i=0; i<messages.length; i++)
			System.out.println(messages[i]);
		assertTrue(roadValidationResults.getFailedMessages().length == 0);
	
	}
	
	public void testLinesIntersectIV_IncorrectData()
	{
		// the visitor
		RoadNetworkValidationResults roadValidationResults = new RoadNetworkValidationResults();
	 
		LinesNotIntersectIntegrityValidation noIntersectingLinesValidatorAll 
					= new LinesNotIntersectIntegrityValidation("linesIntersect", 
							"Tests to see if any line geometries cross!", 
							Validation.ALL);

		try {
			processor.addValidation(noIntersectingLinesValidatorAll);
		} catch (Exception e) {
			assertTrue(false);
		}
	
	
		HashMap layers = new HashMap();
		try {
			layers.put("road", store.getFeatureSource("road"));
			layers.put("river", store.getFeatureSource("river"));
		}
		catch (Exception e) {
			assertTrue(false);
		}
	
	
		try {
			processor.runIntegrityTests(layers, null, roadValidationResults);
			}
		catch (Exception e1) {
			assertTrue(false);
		}

		System.out.println("NoLinesIntersect - incorrect");
		String[] messages = roadValidationResults.getFailedMessages();
		for (int i=0; i<messages.length; i++)
			System.out.println(messages[i]);
		assertTrue(roadValidationResults.getFailedMessages().length > 0);

	}
	
	
}

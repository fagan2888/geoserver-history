/*
 * Created on Jan 24, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.geotools.validation.spatial;

import java.util.Map;

import org.geotools.validation.DefaultIntegrityValidation;
import org.geotools.validation.ValidationResults;

import com.vividsolutions.jts.geom.Envelope;

/**
 * PolygonCoveredByFeaturePolygonValidation purpose.
 * <p>
 * TODO No idea, fill this in.
 * </p>
 * 
 * @author dzwiers, Refractions Research, Inc.
 * @author $Author: jive $ (last modification)
 * @version $Id: PolygonCoveredByFeaturePolygonValidation.java,v 1.1 2004/01/31 00:24:05 jive Exp $
 */
public class PolygonCoveredByFeaturePolygonValidation
	extends DefaultIntegrityValidation {

	/**
	 * PolygonCoveredByFeaturePolygonValidation constructor.
	 * <p>
	 * Description
	 * </p>
	 * 
	 */
	public PolygonCoveredByFeaturePolygonValidation() {
		super();
	}

	/**
	 * Ensure Polygon is covered by the Polygon.
	 * <p>
	 * </p>
	 * @see org.geotools.validation.IntegrityValidation#validate(java.util.Map, com.vividsolutions.jts.geom.Envelope, org.geotools.validation.ValidationResults)
	 * 
	 * @param layers a HashMap of key="TypeName" value="FeatureSource"
	 * @param envelope The bounding box of modified features
	 * @param results Storage for the error and warning messages
	 * @return True if no features intersect. If they do then the validation failed.
	 */
	public boolean validate(Map layers, Envelope envelope, ValidationResults results) throws Exception{
		//TODO Fix Me
		return false;
	}
}

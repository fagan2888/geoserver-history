/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.wms.responses.featureInfo;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.geoserver.data.util.CoverageUtils;
import org.geoserver.platform.ServiceException;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridEnvelope2D;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.data.DataUtilities;
import org.geotools.data.DefaultQuery;
import org.geotools.data.Query;
import org.geotools.data.store.FilteringFeatureCollection;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.GeoTools;
import org.geotools.factory.Hints;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.IllegalAttributeException;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.filter.FilterFactory;
import org.geotools.filter.IllegalFilterException;
import org.geotools.filter.visitor.SimplifyingFilterVisitor;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.GeneralEnvelope;
import org.geotools.geometry.TransformedDirectPosition;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.metadata.iso.spatial.PixelTranslation;
import org.geotools.parameter.FloatParameter;
import org.geotools.parameter.Parameter;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.renderer.lite.MetaBufferEstimator;
import org.geotools.renderer.lite.RendererUtilities;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.resources.geometry.XRectangle2D;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Rule;
import org.geotools.styling.Style;
import org.opengis.coverage.CannotEvaluateException;
import org.opengis.coverage.PointOutsideCoverageException;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.filter.Or;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.metadata.spatial.PixelOrientation;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.parameter.ParameterValueGroup;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.datum.PixelInCell;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.MathTransform2D;
import org.opengis.referencing.operation.TransformException;
import org.vfny.geoserver.global.CoverageInfo;
import org.vfny.geoserver.global.FeatureTypeInfo;
import org.vfny.geoserver.global.GeoServer;
import org.vfny.geoserver.global.MapLayerInfo;
import org.vfny.geoserver.wms.WmsException;
import org.vfny.geoserver.wms.requests.GetFeatureInfoRequest;
import org.vfny.geoserver.wms.requests.GetMapRequest;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;


/**
 * Abstract class to do the common work of the FeatureInfoResponse subclasses.
 * Subclasses should just need to implement writeTo(), to write the actual
 * response, the executions are handled here, figuring out where on the map
 * the pixel is located.
 *
 * <p>
 * Would be nice to have some greater control over the pixels that are
 * selected. Ideally we would be able to detect things like the size of the
 * mark, so that users need not click on the exact center, or the exact pixel.
 * This is not a big deal for polygons, but is for lines and points.  One
 * half solution to make things a bit nicer would be a global parameter to set
 * a wider pixel range.
 * </p>
 *
 * @author James Macgill, PSU
 * @author Gabriel Roldan, Axios
 * @author Chris Holmes, TOPP
 * @author Brent Owens, TOPP
 */
public abstract class AbstractFeatureInfoResponse extends GetFeatureInfoDelegate {
    /** A logger for this class. */
    protected static final Logger LOGGER = org.geotools.util.logging.Logging.getLogger(
            "org.vfny.geoserver.responses.wms.featureinfo");

    /** The formats supported by this map delegate. */
    protected List supportedFormats = null;
    protected List results;
    protected List metas;

    /**
     * setted in execute() from the requested output format, it's holded just
     * to be sure that method has been called before getContentType() thus
     * supporting the workflow contract of the request processing
     */
    protected String format = null;

    /**
     * Creates a new GetMapDelegate object.
     */

    /**
     * Autogenerated proxy constructor.
     */
    public AbstractFeatureInfoResponse() {
        super();
    }

    /**
     * Returns the content encoding for the output data.
     *
     * <p>
     * Note that this reffers to an encoding applied to the response stream
     * (such as GZIP or DEFLATE), and not to the MIME response type, wich is
     * returned by <code>getContentType()</code>
     * </p>
     *
     * @return <code>null</code> since no special encoding is performed while
     *         wrtting to the output stream.
     */
    public String getContentEncoding() {
        return null;
    }

    /**
     * Writes the image to the client.
     *
     * @param out The output stream to write to.
     *
     * @throws ServiceException DOCUMENT ME!
     * @throws IOException DOCUMENT ME!
     */
    public abstract void writeTo(OutputStream out) throws ServiceException, IOException;

    /**
     * The formats this delegate supports.
     *
     * @return The list of the supported formats
     */
    public List getSupportedFormats() {
        return supportedFormats;
    }

    /**
     * DOCUMENT ME!
     *
     * @param gs app context
     *
     * @task TODO: implement
     */
    public void abort(GeoServer gs) {
    }

    /**
     * Gets the content type.  This is set by the request, should only be
     * called after execute.  GetMapResponse should handle this though.
     *
     * @param gs server configuration
     *
     * @return The mime type that this response will generate.
     *
     * @throws IllegalStateException if<code>execute()</code> has not been
     *         previously called
     */
    public String getContentType(GeoServer gs) {
        if (format == null) {
            throw new IllegalStateException(
                "Content type unknown since execute() has not been called yet");
        }

        // chain geoserver charset so that multibyte feature info responses
        // gets properly encoded, same as getCapabilities responses 
        return format + ";charset=" + gs.getCharSet().name();
    }

    /**
     * Performs the execute request using geotools rendering.
     *
     * @param requestedLayers The information on the types requested.
     * @param queries The results of the queries to generate maps with.
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     *
     * @throws WmsException For any problems.
     */
    @SuppressWarnings("unchecked")
    protected void execute(MapLayerInfo[] requestedLayers, Style[] styles, Filter[] filters, int x, int y, int buffer)
        throws WmsException {
        GetFeatureInfoRequest request = getRequest();
        this.format = request.getInfoFormat();

        GetMapRequest getMapReq = request.getGetMapRequest();
        CoordinateReferenceSystem requestedCRS = getMapReq.getCrs(); // optional, may be null

        // basic information about the request
        int width = getMapReq.getWidth();
        int height = getMapReq.getHeight();
        ReferencedEnvelope bbox = new ReferencedEnvelope(getMapReq.getBbox(), getMapReq.getCrs());
        double scaleDenominator = RendererUtilities.calculateOGCScale(bbox, width, new HashMap());
        FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2(GeoTools.getDefaultHints());

        final int layerCount = requestedLayers.length;
        results = new ArrayList(layerCount);
        metas = new ArrayList(layerCount);
        
        try {
            for (int i = 0; i < layerCount; i++) {
                // extract the active rules from the style. If no rule active, nothing to draw
                List<Rule> rules = getActiveRules(styles[i], scaleDenominator);
                if(rules.size() == 0)
                    continue;
                
                if (requestedLayers[i].getType() == org.vfny.geoserver.global.Data.TYPE_VECTOR.intValue()) {
                    FeatureTypeInfo finfo = requestedLayers[i].getFeature();
                    CoordinateReferenceSystem dataCRS = finfo.getFeatureType().getCoordinateReferenceSystem();
                    
                    

                    // compute the request radius
                    double radius;
                    if(buffer <= 0) {
                        // estimate the radius given the currently active rules
                        MetaBufferEstimator estimator = new MetaBufferEstimator();
                        for (Rule rule : rules) {
                            rule.accept(estimator);
                        }
                        
                        if(estimator.getBuffer() < 2.0 || !estimator.isEstimateAccurate()) {
                            radius = 2.0;
                        } else {
                            radius =  estimator.getBuffer() / 2.0;
                        }
                    } else {
                        radius = buffer;
                    }
                    
                    // make sure we don't go overboard, the admin might have set a maximum
                    int maxRadius = request.getWMS().getMaxBuffer();
                    if(maxRadius > 0 && radius > maxRadius)
                        radius = maxRadius;
                    
                    Polygon pixelRect = getEnvelopeFilter(x, y, width, height, bbox, radius);
                    if ((requestedCRS != null) && !CRS.equalsIgnoreMetadata(dataCRS, requestedCRS)) {
                        try {
                            MathTransform transform = CRS.findMathTransform(requestedCRS, dataCRS, true);
                            pixelRect = (Polygon) JTS.transform(pixelRect, transform); // reprojected
                        } catch (MismatchedDimensionException e) {
                            LOGGER.severe(e.getLocalizedMessage());
                        } catch (TransformException e) {
                            LOGGER.severe(e.getLocalizedMessage());
                        } catch (FactoryException e) {
                            LOGGER.severe(e.getLocalizedMessage());
                        }
                    }

                    Filter getFInfoFilter = null;
                    try {
                        getFInfoFilter = ff.intersects(ff.property(finfo.getFeatureType().getGeometryDescriptor().getLocalName()), ff.literal(pixelRect));
                    } catch (IllegalFilterException e) {
                        throw new WmsException(null, "Internal error : " + e.getMessage());
                    }

                    // include the eventual layer definition filter
                    if (filters[i] != null) {
                        getFInfoFilter = ff.and(getFInfoFilter, filters[i]);
                    }
                    
                    // see if we can include the rule filters as well, if too many we'll do them in memory
                    Filter postFilter = Filter.INCLUDE;
                    Filter rulesFilters = buildRulesFilter(ff, rules);
                    if(!(rulesFilters instanceof Or) ||
                        (rulesFilters instanceof Or && ((Or) rulesFilters).getChildren().size() <= 20)) {
                        getFInfoFilter = ff.and(getFInfoFilter, rulesFilters);
                    } else {
                        postFilter = rulesFilters;
                    }

                    Query q = new DefaultQuery(finfo.getTypeName(), null, getFInfoFilter, request.getFeatureCount(), Query.ALL_NAMES, null);
                    FeatureCollection<SimpleFeatureType, SimpleFeature> match = finfo.getFeatureSource().getFeatures(q);
                    
                    // if we could not include the rules filter into the query, post process in memory
                    if(!Filter.INCLUDE.equals(postFilter))
                        match = new FilteringFeatureCollection<SimpleFeatureType, SimpleFeature>(match, postFilter);

                    //this was crashing Gml2FeatureResponseDelegate due to not setting
                    //the featureresults, thus not being able of querying the SRS
                    //if (match.getCount() > 0) {
                    results.add(match);
                    metas.add(requestedLayers[i]);

                    //}
                } else {

                    
                    final CoverageInfo cinfo = requestedLayers[i].getCoverage();
                    final AbstractGridCoverage2DReader reader = (AbstractGridCoverage2DReader)cinfo.getReader();
                    final ParameterValueGroup params = reader.getFormat().getReadParameters();
                    final GeneralParameterValue[] parameters = CoverageUtils.getParameters(params, requestedLayers[i].getCoverage().getParameters(),true);
                    //get the original grid geometry
                    final GridGeometry2D coverageGeometry=(GridGeometry2D) cinfo.getGrid();
                    // set the requested position in model space for this request
                    final Coordinate middle = pixelToWorld(x, y, bbox, width, height);
                    DirectPosition position = new DirectPosition2D(requestedCRS, middle.x, middle.y);
                	
                	//change from request crs to coverage crs in order to compute a minimal request area, 
                    // TODO this code need to be made much more robust
                    if (requestedCRS != null) {
                        
                        final CoordinateReferenceSystem targetCRS = coverageGeometry.getCoordinateReferenceSystem();
                        final TransformedDirectPosition arbitraryToInternal = new 
                        	TransformedDirectPosition(requestedCRS, targetCRS, new Hints(Hints.LENIENT_DATUM_SHIFT,Boolean.TRUE));
                        try {
                            arbitraryToInternal.transform(position);
                        } catch (TransformException exception) {
                            throw new CannotEvaluateException("Unable to answer the geatfeatureinfo",exception);
                        }
                        position=arbitraryToInternal;
                    }
                    //check that the provided point is inside the bbox for this coverage
                    if(!reader.getOriginalEnvelope().contains(position))
                    	throw new CannotEvaluateException("The position at which we should evaluate the coverage does not fall within the coverage's bbox");
                    
                    
                    //now get the position in raster space using the world to grid related to corner
                    final MathTransform worldToGrid=reader.getOriginalGridToWorld(PixelInCell.CELL_CORNER).inverse();
                    final DirectPosition rasterMid = worldToGrid.transform(position,null);
                    // create a 20X20 rectangle aruond the mid point and then intersect with the original range
                    final Rectangle2D.Double rasterArea= new Rectangle2D.Double();
                    rasterArea.setFrameFromCenter(rasterMid.getOrdinate(0), rasterMid.getOrdinate(1), rasterMid.getOrdinate(0)+10, rasterMid.getOrdinate(1)+10);
                    final Rectangle interegerRasterArea=rasterArea.getBounds();
                    XRectangle2D.intersect(interegerRasterArea, reader.getOriginalGridRange().toRectangle(), interegerRasterArea);
                    //paranoiac check, did we fall outside the coverage raster area? This should never really happne if the request is well formed.
                    if(interegerRasterArea.isEmpty())
                    	throw new CannotEvaluateException("Unable to evaluate the request coverage "+requestedLayers[i].toString());
                    // now set the grid geometry for this request
                    for(int k=0;k<parameters.length;k++){
                    	if(!(parameters[k] instanceof Parameter<?>))
                    		continue;
                    	
                    	final Parameter<?> parameter = (Parameter<?>) parameters[k];
                    	if(parameter.getDescriptor().getName().equals(AbstractGridFormat.READ_GRIDGEOMETRY2D.getName()))
                    	{
                    		//
                    		//create a suitable geometry for this request reusing the getmap (we could probably optimize)
                    		//
                    		parameter.setValue(new GridGeometry2D(
                    				new GridEnvelope2D(interegerRasterArea),
                    				reader.getOriginalGridToWorld(PixelInCell.CELL_CENTER),
                    				reader.getCrs()
                    				));
                    	}
                    	
                    }
                    final GridCoverage2D coverage=(GridCoverage2D) reader.read(parameters);
                    if(coverage==null)
                    {
                    	if(LOGGER.isLoggable(Level.FINE))
                    		LOGGER.fine("Unable to load raster data for this request.");
                    	return;
                    }

                    try {
                        final double[] pixelValues = coverage.evaluate(position,(double[]) null);
                        final FeatureCollection<SimpleFeatureType, SimpleFeature> pixel;
                        pixel = wrapPixelInFeatureCollection(coverage, pixelValues, cinfo.getName());
                        metas.add(requestedLayers[i]);
                        results.add(pixel);
                    } catch(PointOutsideCoverageException e) {
                        // it's fine, users might legitimately query point outside, we just don't return anything
                    }
                }
            }
        } catch (Exception e) {
            throw new WmsException(null, "Internal error occurred", e);
        } 
    }

    private Filter buildRulesFilter(org.opengis.filter.FilterFactory ff, List<Rule> rules) {
        // build up a or of all the rule filters
        List<Filter> filters = new ArrayList<Filter>();
        for (Rule rule : rules) {
            if(rule.getFilter() == null)
                return Filter.INCLUDE;
            filters.add(rule.getFilter());
        }
        // not or and and simplify (if there is any include/exclude we'll get 
        // a very simple result ;-)
        Filter or = ff.or(filters);
        SimplifyingFilterVisitor simplifier = new SimplifyingFilterVisitor();
        return (Filter) or.accept(simplifier, null);
    }

    /**
     * Selects the rules active at this zoom level
     * @param style
     * @param scaleDenominator
     * @return
     */
    private List<Rule> getActiveRules(Style style, double scaleDenominator) {
        List<Rule> result = new ArrayList<Rule>();
        
        for(FeatureTypeStyle fts : style.getFeatureTypeStyles()) {
            for (Rule r : fts.rules()) {
                if((r.getMinScaleDenominator() <= scaleDenominator)
                    && (r.getMaxScaleDenominator() > scaleDenominator)) {
                    result.add(r);
                }
            }
        }
        return result;
    }

    private Polygon getEnvelopeFilter(int x, int y, int width, int height, Envelope bbox, double radius) {
        Coordinate upperLeft = pixelToWorld(x - radius, y - radius, bbox, width, height);
        Coordinate lowerRight = pixelToWorld(x + radius, y + radius, bbox, width, height);

        Coordinate[] coords = new Coordinate[5];
        coords[0] = upperLeft;
        coords[1] = new Coordinate(lowerRight.x, upperLeft.y);
        coords[2] = lowerRight;
        coords[3] = new Coordinate(upperLeft.x, lowerRight.y);
        coords[4] = coords[0];

        GeometryFactory geomFac = new GeometryFactory();
        LinearRing boundary = geomFac.createLinearRing(coords); // this needs to be done with each FT so it can be reprojected
        Polygon pixelRect = geomFac.createPolygon(boundary, null);
        return pixelRect;
    }

    private FeatureCollection<SimpleFeatureType, SimpleFeature> wrapPixelInFeatureCollection(
            GridCoverage2D coverage, double[] pixelValues, String coverageName) throws SchemaException, IllegalAttributeException {
        GridSampleDimension[] sampleDimensions = coverage.getSampleDimensions();
        SimpleFeatureType gridType;
        try {
            SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
            builder.setName(coverageName);
            final Set<String> bandNames=new HashSet<String>();
            for (int i = 0; i < sampleDimensions.length; i++) {
            	String name=sampleDimensions[i].getDescription().toString();
            	//GEOS-2518
            	if(bandNames.contains(name))
            		// it might happen again that the name already exists but it pretty difficult I'd say
            		name= new StringBuilder(name).append("_Band").append(i).toString();
            	bandNames.add(name);
                builder.add(name, Double.class);
            }
            gridType = builder.buildFeatureType();
        } catch(Exception e) {
            // sometimes a grid coverage format does not assign unique descriptions to coverages
            SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
            builder.setName(coverageName);
            for (int i = 0; i < sampleDimensions.length; i++) {
                builder.add("Band " + (i + 1), Double.class);
            }
            gridType = builder.buildFeatureType();
        }
        
        Double[] values = new Double[pixelValues.length];
        for (int i = 0; i < values.length; i++) {
            values[i] = new Double(pixelValues[i]);
        }
        return DataUtilities.collection(SimpleFeatureBuilder.build(gridType, values, ""));
    }

    /**
     * Converts a coordinate expressed on the device space back to real world
     * coordinates.  Stolen from LiteRenderer but without the need of a
     * Graphics object
     *
     * @param x horizontal coordinate on device space
     * @param y vertical coordinate on device space
     * @param map The map extent
     * @param width image width
     * @param height image height
     *
     * @return The correspondent real world coordinate
     *
     * @throws RuntimeException DOCUMENT ME!
     */
    private static Coordinate pixelToWorld(double x, double y, Envelope map, double width, double height) {
        //set up the affine transform and calculate scale values
        final AffineTransform at = worldToScreenTransform(map, width, height);
        
        Point2D result = null;

        try {
            result = at.inverseTransform(new java.awt.geom.Point2D.Double(x, y),
                    new java.awt.geom.Point2D.Double());
        } catch (NoninvertibleTransformException e) {
            throw new RuntimeException(e);
        }

        return new Coordinate(result.getX(), result.getY());

    }

    /**
     * Sets up the affine transform.  Stolen from liteRenderer code.
     *
     * @param mapExtent the map extent
     * @param width the screen size
     * @param height DOCUMENT ME!
     *
     * @return a transform that maps from real world coordinates to the screen
     */
    private static AffineTransform worldToScreenTransform(Envelope mapExtent, double width, double height) {
        double scaleX = (double) width / mapExtent.getWidth();
        double scaleY = (double) height / mapExtent.getHeight();

        double tx = -mapExtent.getMinX() * scaleX;
        double ty = (mapExtent.getMinY() * scaleY) + height;

        AffineTransform at = new AffineTransform(scaleX, 0.0d, 0.0d, -scaleY, tx, ty);

        return at;
    }
}

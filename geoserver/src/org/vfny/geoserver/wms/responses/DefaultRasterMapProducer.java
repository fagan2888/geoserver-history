/* Copyright (c) 2001, 2003 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.wms.responses;
 
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.geotools.renderer.lite.LiteRenderer2;
import org.vfny.geoserver.wms.GetMapProducer;
import org.vfny.geoserver.wms.WMSMapContext;
import org.vfny.geoserver.wms.WmsException;

import com.vividsolutions.jts.geom.Envelope;


/**
 * Abstract base class for GetMapProducers that relies in LiteRenderer for
 * creating the raster map and then outputs it in the format they specializes
 * in.
 * 
 * <p>
 * This class does the job of producing a BufferedImage using geotools
 * LiteRenderer, so it should be enough for a subclass to implement
 * {@linkPlain #formatImageOutputStream(String, BufferedImage, OutputStream)}
 * </p>
 * 
 * <p>
 * Generates a map using the geotools jai rendering classes.  Uses the Lite
 * renderer, loading the data on the fly, which is quite nice.  Thanks Andrea
 * and Gabriel.  The word is that we should eventually switch over to
 * StyledMapRenderer and do some fancy stuff with caching layers, but  I think
 * we are a ways off with its maturity to try that yet.  So Lite treats us
 * quite well, as it is stateless and therefor loads up nice and fast.
 * </p>
 * 
 * <p></p>
 *
 * @author Chris Holmes, TOPP
 * @version $Id: JAIMapResponse.java,v 1.29 2004/09/16 21:44:28 cholmesny Exp $
 */
public abstract class DefaultRasterMapProducer implements GetMapProducer {
    /** A logger for this class. */
    private static final Logger LOGGER = Logger.getLogger(
            "org.vfny.geoserver.responses.wms.map");

    /** Which format to encode the image in if one is not supplied */
    private static final String DEFAULT_MAP_FORMAT = "image/png";

    /** The image generated by the execute method. */
    private BufferedImage image;

    /** The one to do the magic of rendering a map */
    private LiteRenderer2 renderer;

    /**
     * Set in produceMap(...) from the requested output format, it's holded
     * just to be sure that method has been called before getContentType()
     * thus supporting the workflow contract of the request processing
     */
    private String format = null;

    /**
     * set to <code>true</code> on <code>abort()</code> so
     * <code>produceMap</code> leaves the image being worked on to the garbage
     * collector.
     */
    private boolean abortRequested;

    /**
     * Holds the map context passed to produceMap, so subclasses can use it if
     * they need it from inside {@linkPlain #formatImageOutputStream(String,
     * BufferedImage, OutputStream)}
     */
    private WMSMapContext mapContext;

    /**
     *
     */
    public DefaultRasterMapProducer() {
        this(DEFAULT_MAP_FORMAT);
    }

    /**
     *
     */
    public DefaultRasterMapProducer(String outputFormat) {
        setOutputFormat(outputFormat);
    }

    /**
     * Sets the MIME type of the output image.
     *
     * @param format the desired output map format.
     */
    public void setOutputFormat(String format) {
        this.format = format;
    }

    /**
     * Writes the image to the client.
     *
     * @param out The output stream to write to.
     *
     * @throws org.vfny.geoserver.ServiceException DOCUMENT ME!
     * @throws java.io.IOException DOCUMENT ME!
     */
    public void writeTo(OutputStream out)
        throws org.vfny.geoserver.ServiceException, java.io.IOException {
        formatImageOutputStream(this.format, this.image, out);
    }

    /**
     * Halts the loading.  Right now just calls renderer.stopRendering.
     */
    public void abort() {
        this.abortRequested = true;
        if(this.renderer != null)
        this.renderer.stopRendering();
    }

    /**
     * Gets the content type.  This is set by the request, should only be
     * called after execute.  GetMapResponse should handle this though.
     *
     * @return The mime type that this response will generate.
     *
     * @throws java.lang.IllegalStateException if <code>produceMap()</code> has
     *         not been previously called
     * @throws IllegalStateException DOCUMENT ME!
     */
    public String getContentType() throws java.lang.IllegalStateException {
        if (this.format == null) {
            throw new IllegalStateException(
                "the output map format was not yet specified");
        }

        return this.format;
    }

    /**
     * returns the content encoding for the output data (null for this class)
     *
     * @return <code>null</code> since no special encoding is performed while
     *         wrtting to the output stream. Do not confuse this with
     *         getMimeType().
     */
    public String getContentEncoding() {
        return null;
    }

    /**
     * Performs the execute request using geotools rendering.
     *
     * @param map The information on the types requested.
     *
     * @throws WmsException For any problems.
     */
    public void produceMap(WMSMapContext map) throws WmsException {
        this.mapContext = map;

        final int width = map.getMapWidth();
        final int height = map.getMapHeight();

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("setting up " + width + "x" + height + " image");
        }

        BufferedImage curImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);

        final Graphics2D graphic = curImage.createGraphics();

        if (!map.isTransparent()) {
            graphic.setColor(map.getBgColor());
            graphic.fillRect(0, 0, width, height);
        } else {
            LOGGER.fine("setting to transparent");

            // Do not need to set Alpha Blending ..... simply we don't draw the background :-)
            
            //int type = AlphaComposite.SRC_OVER;
            //graphic.setComposite(AlphaComposite.getInstance(type, 0.0f));
        }

        Rectangle paintArea = new Rectangle(width, height);

        this.renderer = new LiteRenderer2(map);

        //we already do everything that the optimized data loading does...
        //if we set it to true then it does it all twice...
        this.renderer.setOptimizedDataLoadingEnabled(true);

        Envelope dataArea = map.getAreaOfInterest();
        AffineTransform at = this.renderer.worldToScreenTransform(dataArea,
                paintArea);

        LOGGER.fine("calling renderer");

        if (this.abortRequested) {
            return;
        }

        this.renderer.paint(graphic, paintArea, at);
        map = null;

        if (!this.abortRequested) {
            this.image = curImage;
        }

        graphic.dispose();
    }

    /**
     * This is the method subclases must implement to  transform the rendered
     * image into the appropriate format, streaming to the output stream.
     *
     * @param format The name of the format
     * @param image The image to be formatted.
     * @param outStream The stream to write to.
     *
     * @throws WmsException
     * @throws IOException DOCUMENT ME!
     */
    protected abstract void formatImageOutputStream(String format,
        BufferedImage image, OutputStream outStream)
        throws WmsException, IOException;

    /**
     * This is a package protected method with the sole purpose of facilitate
     * unit testing. Do not use by any means for oher purposes.
     *
     * @return DOCUMENT ME!
     */
    public BufferedImage getImage() {
        return this.image;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    protected WMSMapContext getMapContext() {
        return this.mapContext;
    }
}

/* Copyright (c) 2001, 2003 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.wms.responses.map.tiff;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.vfny.geoserver.wms.WmsException;
import org.vfny.geoserver.wms.responses.DefaultRasterMapProducer;

import com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriter;
import com.sun.media.imageioimpl.plugins.tiff.TIFFImageWriterSpi;

/**
 */
public final class  TiffMapProducer extends DefaultRasterMapProducer {
    /** A logger for this class. */
    private static final Logger LOGGER = Logger.getLogger("org.vfny.geoserver.responses.wms.map");

    /** DOCUMENT ME! */
    private static final String DEFAULT_MAP_FORMAT = "image/tiff";

    /**
     * Creates a map producer that relies on JAI to encode the BufferedImage generated the default
     * (image/png) image format.
     */
    public TiffMapProducer() {
        this(DEFAULT_MAP_FORMAT);
    }

    /**
     * Creates a map producer that relies on JAI to encode the BufferedImage generated in
     * <code>outputFormat</code> format.
     * 
     * @param outputFormat the output format MIME type.
     */
    public TiffMapProducer( String outputFormat ) {
        setOutputFormat(outputFormat);
    }

    /**
     * Transforms the rendered image into the appropriate format, streaming to the output stream.
     * 
     * @param format The name of the format
     * @param image The image to be formatted.
     * @param outStream The stream to write to.
     * @throws WmsException not really.
     * @throws IOException if the image writing fails.
     */
    protected void formatImageOutputStream( String format, BufferedImage image,
            OutputStream outStream ) throws WmsException, IOException {

        final ImageWriter writer = new TIFFImageWriter(new TIFFImageWriterSpi());
        final ImageOutputStream ioutstream = new MemoryCacheImageOutputStream(outStream);

        // tiff
        writer.setOutput(ioutstream);
        writer.write(image);
        ioutstream.close();
        writer.dispose();
    }
}

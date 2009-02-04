/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.rest.format;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.restlet.data.MediaType;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

/**
 * Data format for serializing and de-serializing an object as JSON with XStream.
 * <p>
 * Subclasses should override the {@link #read(InputStream)} and {@link #write(Object, OutputStream)}
 * methods to create a customized xstream instance, or to use some other JSON serialization method. 
 * </p>
 * @author Justin Deoliveira, OpenGEO
 *
 */
public class ReflectiveJSONFormat extends StreamDataFormat {

    public ReflectiveJSONFormat() {
        super(new MediaType( "text/json" ));
    }

    /**
     * Reads an JSON input stream into an object.
     *  
     * @param in The json.
     * 
     * @return The object de-serialized from JSON.
     */
    protected Object read( InputStream input ) throws IOException {
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        return xstream.fromXML( input );
    }

    /**
     * Writes an object as JSON to an output stream.
     * 
     * @param data The object.
     * @param output The output stream.
     */
    protected void write( Object data, OutputStream output ) throws IOException {
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.toXML( data, output );
    }
}

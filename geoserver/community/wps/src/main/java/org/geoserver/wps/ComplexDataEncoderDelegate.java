package org.geoserver.wps;

import org.geoserver.wps.ppio.XMLPPIO;
import org.geotools.xml.EncoderDelegate;
import org.xml.sax.ContentHandler;

public class ComplexDataEncoderDelegate implements EncoderDelegate {

    XMLPPIO ppio;
    Object object;
    
    public ComplexDataEncoderDelegate( XMLPPIO ppio, Object object ) {
        this.ppio = ppio;
        this.object = object;
    }
    
    public XMLPPIO getProcessParameterIO() { 
        return ppio;
    }
    
    public void encode(ContentHandler handler) throws Exception {
        ppio.encode(object, handler);
    }

}

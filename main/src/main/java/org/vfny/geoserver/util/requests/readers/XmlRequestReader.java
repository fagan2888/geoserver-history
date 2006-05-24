/* Copyright (c) 2001, 2003 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.util.requests.readers;

import java.io.Reader;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.vfny.geoserver.Request;
import org.vfny.geoserver.ServiceException;
import org.vfny.geoserver.servlets.AbstractService;


/**
 * This utility reads in XML requests and returns them as appropriate request
 * objects.
 *
 * @author Rob Hranac, TOPP
 * @author Chris Holmes, TOPP
 * @author Gabriel Rold?n
 * @version $Id: XmlRequestReader.java,v 1.7 2004/02/13 01:07:09 dmzwiers Exp $
 */
public abstract class XmlRequestReader {
    /** Class logger */
    protected static Logger LOGGER = Logger.getLogger(
            "org.vfny.geoserver.requests.readers");

	/** The service handling the request **/
    private AbstractService service;
	
	/**
     * DOCUMENT ME!
     *
     * @param reader DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     *
     * @throws ServiceException DOCUMENT ME!
     */
    public abstract Request read(Reader reader, HttpServletRequest req) throws ServiceException;
    
    /**
     * This will create a new XmlRequestReader
     * @param service The service handling the request
     */
    public XmlRequestReader(AbstractService service) {
    	this.service = service;
	}
    /**
     * This will create a new XmlRequestReader
     * @deprecated use {@link #XmlRequestReader(AbstractService)}
     */
    public XmlRequestReader() {
    	this(null);
    }

	/**
	 * @return the service handling the request
	 */
    public AbstractService getServiceRef() {
		return service;
	}

	/**
	 * sets the service handling the request
	 */
    public void setServiceRef(AbstractService service) {
		this.service = service;
	}
    
    
    
    

}

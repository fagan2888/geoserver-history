/* Copyright (c) 2001, 2003 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.servlets.wms;

import org.vfny.geoserver.requests.readers.KvpRequestReader;
import org.vfny.geoserver.requests.readers.XmlRequestReader;
import org.vfny.geoserver.requests.readers.wms.CapabilitiesKvpReader;
import org.vfny.geoserver.requests.readers.wms.CapabilitiesXmlReader;
import org.vfny.geoserver.responses.Response;
import org.vfny.geoserver.responses.wms.WMSCapabilitiesResponse;
import org.vfny.geoserver.servlets.WMService;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author Gabriel Rold�n
 * @version $Id: Capabilities.java,v 1.4 2004/01/21 00:26:09 dmzwiers Exp $
 */
public class Capabilities extends WMService {
    /**
     * DOCUMENT ME!
     *
     * @param params DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    protected KvpRequestReader getKvpReader(Map params) {
        return new CapabilitiesKvpReader(params);
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    protected XmlRequestReader getXmlRequestReader() {
        return new CapabilitiesXmlReader();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    protected Response getResponseHandler() {
        return new WMSCapabilitiesResponse();
    }
}

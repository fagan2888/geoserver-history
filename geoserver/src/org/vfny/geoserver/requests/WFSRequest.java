/* Copyright (c) 2001, 2003 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.requests;

/**
 * Defines a general WFS Request type
 *
 * @author Gabriel Rold�n
 * @version $Id: WFSRequest.java,v 1.5 2004/01/21 00:26:06 dmzwiers Exp $
 */
abstract public class WFSRequest extends Request {
    public static final String WFS_SERVICE_TYPE = "WFS";

    /**
     * A WFSRequest configured with WFS_SERVICE_TYPE
     */
    public WFSRequest() {
        super(WFS_SERVICE_TYPE);
    }

    /**
     * A WFSRequest configured with WFS_SERVICE_TYPE
     *
     * @param requestType DOCUMENT ME!
     */
    public WFSRequest(String requestType) {
        super(WFS_SERVICE_TYPE, requestType);
    }
}

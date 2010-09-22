/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.wms.request;

import org.geoserver.wms.WMS;
import org.vfny.geoserver.Request;

/**
 * This class enforces a standard interface for GetCapabilities requests.
 * 
 * @author Rob Hranac, TOPP
 * @author Chris Holmes, TOPP
 * @version $Id$
 */
public class GetCapabilitiesRequest extends Request {

    private String updateSequence;

    private String namespace;

    private WMS config;

    public GetCapabilitiesRequest(WMS wms) {
        this(wms, null);
    }

    /**
     * Creates a new capabilities request object.
     * 
     * @param wms
     *            the WMS config.
     * 
     * @param updateSequence
     *            The updateSequence number from the GetCapabilities request
     */
    public GetCapabilitiesRequest(WMS wms, String updateSequence) {
        super("WMS", "GetCapabilities", wms.getServiceInfo());
        this.config = wms;
        this.updateSequence = updateSequence;
    }

    public WMS getWMS() {
        return config;
    }

    /**
     * Returns a string representation of this CapabilitiesRequest.
     * 
     * @return a string of with the service and version.
     */
    public String toString() {
        return "GetCapabilities [service: " + service + ", version: " + version + "]";
    }

    /**
     * Override of equals. Just calls super.equals, since there are no extra fields here that aren't
     * in Request. `
     * 
     * @param o
     *            the object to test against.
     * 
     * @return <tt>true</tt> if o is equal to this request.
     */
    public boolean equals(Object o) {
        return super.equals(o);
    }

    /**
     * @return the updateSequence
     */
    public String getUpdateSequence() {
        return updateSequence;
    }

    /**
     * @param updateSequence
     *            the updateSequence to set
     */
    public void setUpdateSequence(String updateSequence) {
        this.updateSequence = updateSequence;
    }

    /**
     * Returns the namespace prefix we should filter layers on (if any) (used in WMS only atm, but
     * could be easily expanded to wfs/wcs too)
     * 
     * @return the namespace prefix which to filter the content for
     */
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }
}

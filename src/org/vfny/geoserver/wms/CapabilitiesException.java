/*
 *    Geotools2 - OpenSource mapping toolkit
 *    http://geotools.org
 *    (C) 2002, Geotools Project Managment Committee (PMC)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 *
 */
package org.vfny.geoserver.wms;

public class CapabilitiesException extends Exception {
    /** The layer specified in the given call has a duplicate */
    public static final int EXP_DUPLICATELAYER = 1;

    /** The Layer specified in the given call is invalid */
    public static final int EXP_INVALIDLAYER = 2;
    private int code;

    /**
     * Constructs this Exception
     *
     * @param code The relevant code of the error
     * @param msg A detailed message describing the error
     */
    public CapabilitiesException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    /**
     * Constructs this Exception
     *
     * @param code The relevant code of the error
     * @param msg A detailed message describing the error
     * @param exp The exception which caused this exception to be thrown
     */
    public CapabilitiesException(int code, String msg, Exception exp) {
        super(msg + " : " + exp.getMessage());
        this.code = code;
    }
}

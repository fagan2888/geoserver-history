/* Copyright (c) 2001, 2003 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
/* Copyright (c) 2001 - 2004 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.global.dto;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


//import com.vividsolutions.jts.geom.*;

/**
 * Utility methods with custom equals implementation against Maps and Lists.
 * 
 * <p>
 * Static Library class for testing equality of complex structures independant
 * of their contents.
 * </p>
 * 
 * <p></p>
 *
 * @author dzwiers, Refractions Research, Inc.
 * @version $Id: EqualsLibrary.java,v 1.3 2004/01/21 00:26:09 dmzwiers Exp $
 */
public final class EqualsLibrary {
    /**
     * EqualsLibrary constructor.
     * 
     * <p>
     * Should never be called, static class.
     * </p>
     */
    private EqualsLibrary() {
    }

    /**
     * equals purpose.
     * 
     * <p>
     * Performs a complex equality check between two Lists
     * </p>
     *
     * @param a One List to be compared
     * @param b The other List to be compared
     *
     * @return true if they are equal
     *
     * @see java.util.List
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public static boolean equals(List a, List b) {
        if (a == b) {
            return true;
        }

        if (a == null) {
            return false;
        }

        if (b == null) {
            return false;
        }

        if (a.size() != b.size()) {
            return false;
        }

        Iterator i = a.iterator();

        while (i.hasNext())

            if (!b.contains(i.next())) {
                return false;
            }

        return true;
    }

    /**
     * equals purpose.
     * 
     * <p>
     * Performs a complex equality check between two Maps
     * </p>
     *
     * @param a One Map to be compared
     * @param b The other Map to be compared
     *
     * @return true if they are equal
     *
     * @see java.util.Map
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public static boolean equals(Map a, Map b) {
        if (a == b) {
            return true;
        }

        if (a == null) {
            return false;
        }

        if (b == null) {
            return false;
        }

        if (a.size() != b.size()) {
            return false;
        }

        Iterator i = a.keySet().iterator();

        while (i.hasNext()) {
            Object t = i.next();

            if (!b.containsKey(t) || !a.get(t).equals(b.get(t))) {
                return false;
            }
        }

        return true;
    }
}

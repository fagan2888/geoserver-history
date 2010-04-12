/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.gss;

import java.io.IOException;

import net.opengis.wfs.TransactionType;

/**
 * Allows communication with a specific GSS Unit service
 * 
 * @author Andrea Aime
 * 
 */
public interface GSSClient {

    /**
     * Grabs the latest central revision number known to the client for the specified layer
     */
    public long getCentralRevision(String layerName) throws IOException;

    /**
     * Posts the changes occurred locally between fromRevision and toRevision to the client
     */
    public void postDiff(String layerName, long fromVersion, long toVersion, TransactionType changes)
            throws IOException;

    /**
     * Grabs the changes occurred on the unit since the fromVersion unit revision
     */
    public TransactionType getDiff(String layerName, long fromVersion) throws IOException;
}

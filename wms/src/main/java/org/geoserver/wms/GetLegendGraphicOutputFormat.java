/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.wms;

import org.geoserver.platform.ServiceException;

/**
 * Provides the skeleton for producers of a legend image, as required by the GetLegendGraphic WMS
 * request.
 * 
 * <p>
 * Implementations are meant to be state-less and to support a single image output format, which is
 * declared in {@link #getContentType()}.
 * </p>
 * <p>
 * For a given GetLegendGraphicOutputFormat to be found at runtime, it is only needed that a Spring
 * bean is declared in the GeoServer application context.
 * </p>
 * 
 * @author Gabriel Roldan
 * @version $Id$
 */
public interface GetLegendGraphicOutputFormat {
    /**
     * Asks this legend graphic producer to create a graphic for the GetLegenGraphic request
     * parameters held in <code>request</code>
     * 
     * @param request
     *            the "parsed" request, where "parsed" means that it's properties are already
     *            validated so this method must not take care of verifying the requested layer
     *            exists and the like.
     * 
     * @throws ServiceException
     *             something goes wrong
     */
    LegendGraphic produceLegendGraphic(GetLegendGraphicRequest request) throws ServiceException;

    /**
     * Returns the MIME type of the content supported by this format
     * 
     * @return the output format
     * 
     * @throws java.lang.IllegalStateException
     *             if this method is called before
     *             {@linkplain #produceLegendGraphic(GetLegendGraphicRequest)}.
     */
    String getContentType();

}

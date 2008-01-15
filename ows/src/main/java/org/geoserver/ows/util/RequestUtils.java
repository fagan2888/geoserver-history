/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.ows.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.geoserver.platform.ServiceException;
import org.geotools.util.Version;


/**
 * Utility class performing operations related to http requests.
 *
 * @author Justin Deoliveira, The Open Planning Project, jdeolive@openplans.org
 *
 * TODO: this class needs to be merged with org.vfny.geoserver.Requests.
 */
public class RequestUtils {
    static String forcedBaseUrl;
    
    /**
     * Returns the url which is hte base of schemas stored / served by
     * geoserver.
     * <p>
     *         This method returns:
     *         <pre>
     *        <code>
     *    baseURL( req ) + "schemas/"
     *  </code>
     *  </pre>
     * </p>
     *
     * @return A String of the form "<scheme>://<server>:<port>/<context>/schemas/"
     */
    public static String schemaBaseURL(HttpServletRequest req) {
        return baseURL(req) + "schemas/";
    }

    /**
     * Pulls out the base url ( from the client point of view ), from the
     * given request object.
     *
     * @return A String of the form "<scheme>://<server>:<port>/<context>/"
     *
     */
    public static String baseURL(HttpServletRequest req) {
        if(forcedBaseUrl == null)
            return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath() + "/";
        else
            return forcedBaseUrl;
    }
    
    /**
     * Given a base URL and a proxy url (which may or may-not be null)
     * this method grafts the two together so that the proper 'proxified' or 'non-proxified' url is returned
     * 
     */
    public static String proxifiedBaseURL(String baseUrl, String proxyBase) {
        if (proxyBase == null || proxyBase.trim().length() == 0)
            return baseUrl;
        
        try {
            URI baseUri = new URI(baseUrl);
            if (proxyBase.endsWith("/")) proxyBase = proxyBase.substring(0, proxyBase.length() -1);
            
            String proxifiedBaseUrl = proxyBase + baseUri.getPath();
            if (!proxifiedBaseUrl.endsWith("/")) proxifiedBaseUrl += "/";
            
            return proxifiedBaseUrl;
        } catch (URISyntaxException urise) {
            //hmm...guess the proxy base must be invalid
            throw new RuntimeException("Invalid Proxy Base URL property is set in your GeoServer installation.",urise);
        }
    }
    
    
    
    /**
     * Forces the base url to a certain string. Useful to redirect the xml schema
     * locations to the local file system during testing (since no web server is running during unit tests)
     * @param url
     */
    public static void setForcedBaseUrl(String url) {
        forcedBaseUrl = url;
    }

    /**
     * Given a list of provided versions, and a list of accepted versions, this method will
     * return the negotiated version to be used for response according to the pre OWS 1.1 specifications,
     * that is, WMS 1.1, WMS 1.3, WFS 1.0, WFS 1.1 and WCS 1.0
     * @param providedList a non null, non empty list of provided versions (in "x.y.z" format)
     * @param acceptedList a list of accepted versions, eventually null or empty (in "x.y.z" format)
     * @return the negotiated version to be used for response
     */
    public static String getVersionPreOws(List<String> providedList, List<String> acceptedList) {
        //first figure out which versions are provided
        TreeSet<Version> provided = new TreeSet<Version>();
        for (String v : providedList) {
            provided.add(new Version(v));
        }
        
        // if no accept list provided, we return the biggest
        if(acceptedList == null || acceptedList.isEmpty())
            return provided.last().toString();
    
        //next figure out what the client accepts (and check they are good version numbers)
        TreeSet<Version> accepted = new TreeSet<Version>();
        for (String v : acceptedList) {
            if (!v.matches("[0-99]\\.[0-99]\\.[0-99]")) {
                String msg = v + " is an invalid version numver";
                throw new ServiceException(msg, "VersionNegotiationFailed");
            }
            
            accepted.add(new Version(v));
        }
    
        // prune out those not provided
        for (Iterator<Version> v = accepted.iterator(); v.hasNext();) {
            Version version = (Version) v.next();
    
            if (!provided.contains(version)) {
                v.remove();
            }
        }
    
        // lookup a matching version
        String version = null;
        if (!accepted.isEmpty()) {
            //return the highest version provided
            version = ((Version) accepted.last()).toString();
        } else {
            for (String v : acceptedList) {
                accepted.add(new Version(v));
            }
    
            //if highest accepted less then lowest provided, send lowest
            if ((accepted.last()).compareTo(provided.first()) < 0) {
                version = (provided.first()).toString();
            }
    
            //if lowest accepted is greater then highest provided, send highest
            if ((accepted.first()).compareTo(provided.last()) > 0) {
                version = (provided.last()).toString();
            }
    
            if (version == null) {
                //go through from lowest to highest, and return highest provided 
                // that is less than the highest accepted
                Iterator<Version> v = provided.iterator();
                Version last = v.next();
    
                for (; v.hasNext();) {
                    Version current = v.next();
    
                    if (current.compareTo(accepted.last()) > 0) {
                        break;
                    }
    
                    last = current;
                }
    
                version = last.toString();
            }
        }
        
        return version;
    }
    
    /**
     * Given a list of provided versions, and a list of accepted versions, this method will
     * return the negotiated version to be used for response according to the OWS 1.1 specification
     * (at the time of writing, only WCS 1.1.1 is using it)
     * @param providedList a non null, non empty list of provided versions (in "x.y.z" format)
     * @param acceptedList a list of accepted versions, eventually null or empty (in "x.y.z" format)
     * @return the negotiated version to be used for response
     */
    public static String getVersionOws11(List<String> providedList, List<String> acceptedList) {
        return null;
//        //first figure out which versions are provided
//        TreeSet<Version> provided = new TreeSet<Version>();
//        for (String v : providedList) {
//            provided.add(new Version(v));
//        }
//        
//        // if no accept list provided, we throw an exception
//        if(acceptedList == null || acceptedList.isEmpty())
//            
//    
//        //next figure out what the client accepts (and check they are good version numbers)
//        TreeSet<Version> accepted = new TreeSet<Version>();
//        for (String v : acceptedList) {
//            if (!v.matches("[0-99]\\.[0-99]\\.[0-99]")) {
//                String msg = v + " is an invalid version numver";
//                throw new ServiceException(msg, "VersionNegotiationFailed");
//            }
//            
//            accepted.add(new Version(v));
//        }
//    
//        // prune out those not provided
//        for (Iterator<Version> v = accepted.iterator(); v.hasNext();) {
//            Version version = (Version) v.next();
//    
//            if (!provided.contains(version)) {
//                v.remove();
//            }
//        }
//    
//        // lookup a matching version
//        String version = null;
//        if (!accepted.isEmpty()) {
//            //return the highest version provided
//            version = ((Version) accepted.last()).toString();
//        } else {
//            for (String v : acceptedList) {
//                accepted.add(new Version(v));
//            }
//    
//            //if highest accepted less then lowest provided, send lowest
//            if ((accepted.last()).compareTo(provided.first()) < 0) {
//                version = (provided.first()).toString();
//            }
//    
//            //if lowest accepted is greater then highest provided, send highest
//            if ((accepted.first()).compareTo(provided.last()) > 0) {
//                version = (provided.last()).toString();
//            }
//    
//            if (version == null) {
//                //go through from lowest to highest, and return highest provided 
//                // that is less than the highest accepted
//                Iterator<Version> v = provided.iterator();
//                Version last = v.next();
//    
//                for (; v.hasNext();) {
//                    Version current = v.next();
//    
//                    if (current.compareTo(accepted.last()) > 0) {
//                        break;
//                    }
//    
//                    last = current;
//                }
//    
//                version = last.toString();
//            }
//        }
//        
//        return version;
    }
}

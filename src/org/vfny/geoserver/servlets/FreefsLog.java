/* Copyright (c) 2001 Vision for New York - www.vfny.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root application directory.
 */

package org.vfny.geoserver.servlets;

import java.io.PrintWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Iterator;
import javax.servlet.http.*;
//import javax.servlet.*;
import org.geotools.resources.Geotools;
import org.geotools.resources.Log4JFormatter;
import org.geotools.resources.MonolineFormatter;
import org.vfny.geoserver.config.ConfigInfo;
import org.vfny.geoserver.zserver.GeoZServer;
import org.vfny.geoserver.config.TypeRepository;
//Logging system
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.prefs.*;

/**
 * Initializes all logging functions.
 * 
 * @author Rob Hranac, Vision for New York
 * @author Chris Holmes, TOPP
 * @version 0.95 beta, 4/24/03
 *
 */
public class FreefsLog extends HttpServlet {

    /** Change this variable for different amounts of log messages.  Options
       include SEVERE, WARNING, INFO, FINER, FINEST (in order) */
    private static Level loggingLevel = Level.FINER;

    /** Standard logging instance for class */
    private static final Logger LOGGER = 
        Logger.getLogger("org.vfny.geoserver.servlet");
   
    /** Default name for configuration file */
    private static final String CONFIG_FILE =  "configuration.xml";
    /** Default name for configuration directory */
    private static final String CONFIG_DIR =  "data/";

    private static final String BACKING_STORE_AVAIL = "BackingStoreAvail";
    
    /**
     * The logger for the filter module.
     */
    private static final Logger LOG = Logger.getLogger("org.vfny.geoserver.servlets");
   

    private GeoZServer server;
    /**
     * Initializes logging and config.
     *
     */ 
    public void init() {
	//HACK: java.util.prefs are awful.  See 
	//http://www.allaboutbalance.com/disableprefs.  When the site comes 
	//back up we should implement their better way of fixing the problem.
	System.setProperty("java.util.prefs.syncInterval", "5000000");
	String root = this.getServletContext().getRealPath("/");
	String path = root + CONFIG_DIR;
	LOG.finer("init with path: " + path);
	ConfigInfo cfgInfo = ConfigInfo.getInstance(path);
	if (cfgInfo.runZServer()) {
	    try {
		server = new GeoZServer(cfgInfo.getZServerProps());
		server.start();
	    } catch (java.io.IOException e) {
		LOGGER.info("zserver module could not start: " + e.getMessage());
	    }
	}	
    }
    
    
    /**
     * Initializes logging.
     *
     * @param req The servlet request object.
     * @param resp The servlet response object.
     */ 
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        //BasicConfigurator.configure();
    }

    /**
     * Closes down the zserver if it is running, and frees up resources.
     */    
    public void destroy() {
	super.destroy();
	TypeRepository.getInstance().closeTypeResources();
	
	LOGGER.finer("shutting down zserver");
    if (server != null) server.shutdown(1);
    
    }

}

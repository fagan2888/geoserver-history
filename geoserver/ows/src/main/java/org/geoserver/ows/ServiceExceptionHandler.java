/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.ows;

import org.geoserver.ows.util.ResponseUtils;
import org.geoserver.platform.Service;
import org.geoserver.platform.ServiceException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Handles an exception thrown by a service.
 * <p>
 * A service exception handler must declare the services in which it is capable
 * of handling exceptions for, see {@link #getServices()}.
 * </p>
 * <p>
 * Instances must be declared in a spring context as follows:
 * <pre>
 *         <code>
 *  &lt;bean id="myServiceExcepionHandler" class="com.xyz.MyServiceExceptionHandler"&gt;
 *     &lt;constructor-arg ref="myService"/&gt;
 *  &lt;/bean&gt;
 * </code>
 * </pre>
 *
 * Where <code>myService</code> is the id of another bean somewhere in the
 * context.
 *
 * </p>
 *
 * @author Justin Deoliveira, The Open Planning Project
 *
 */
public abstract class ServiceExceptionHandler {
    /**
     * Logger
     */
    protected static Logger LOGGER = org.geotools.util.logging.Logging.getLogger("org.geoserver.ows");

    /**
     * The services this handler handles exceptions for.
     */
    List /*<Service>*/ services;

    /**
     * Constructs the handler with the list of {@link Service}'s that it
     * handles exceptions for.
     *
     * @param services A list of {@link Service}.
     */
    public ServiceExceptionHandler(List services) {
        this.services = services;
    }

    /**
     * Constructs the handler for a single {@link Service} that it handles
     * exceptions for.
     *
     * @param service The service to handle exceptions for.
     */
    public ServiceExceptionHandler(Service service) {
        this.services = Collections.singletonList(service);
    }

    /**
     * @return The services this handler handles exceptions for.
     */
    public List getServices() {
        return services;
    }

    /**
     * Handles the service exception.
     *
     * @param exception The service exception.
     * @param service The service that generated the exception
     * @param request The original request to which the service generated the exception.
     * @param response The response to report the exception to.
     */
    public abstract void handleServiceException(ServiceException exception, Service service,
        HttpServletRequest request, HttpServletResponse response);
    
    /**
     * Dumps an exception message along all its causes messages (since more often
     * than not the real cause, such as "unknown property xxx" is a few levels down)
     * @param e
     * @param s
     */
    protected void dumpExceptionMessages(ServiceException e, StringBuffer s) {
        Throwable ex = e;
        do {
            Throwable cause = ex.getCause();
            if(e.getMessage() != null && !"".equals(e.getMessage())) {
                s.append(ResponseUtils.encodeXML(e.getMessage()));
                for ( Iterator t = e.getExceptionText().iterator(); t.hasNext(); ) {
                    s.append("\n").append( t.next() );
                }
                if(cause != null)
                    s.append("\n");
            }
            
            // avoid infinite loop if someone did the very stupid thing of setting
            // the cause as the exception itself (I only found this situation once, but...)
            if(ex == cause || cause == null)
                break;
            else
                ex = cause;
        } while(true);
    }
}

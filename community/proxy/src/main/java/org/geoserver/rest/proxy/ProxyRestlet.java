package org.geoserver.rest.proxy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.geoserver.proxy.ProxyConfig;
import org.geoserver.proxy.ProxyConfig.Mode;
import org.geoserver.rest.RestletException;
import org.geoserver.security.PropertyFileWatcher;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.StreamRepresentation;

/**
 * The ProxyRestlet implements a simple REST service that forwards HTTP requests on to a third-party
 * webserver.
 * 
 * @author David Winslow <cdwinslow@opengeo.org>
 * @author Alan Gerber <agerber@openplans.org>
 */
public class ProxyRestlet extends Restlet {
    private static Logger LOGGER = org.geotools.util.logging.Logging.getLogger(ProxyRestlet.class);

    private ProxyConfig config;
    private boolean watcherWorks;
    private PropertyFileWatcher configWatcher;
    
    /*
     * Initialize the proxy
     */
    public ProxyRestlet()
    {
        super();
        init();
    }
    
    /*
     * Initialize the proxy with context to call parent with
     */
    public ProxyRestlet(Context context)
    {
        super(context);
        init();
    }
    
    /*
     * Prepares the proxy's environment.
     */
    private void init()
    {
        try{
            configWatcher = new PropertyFileWatcher(ProxyConfig.getConfigFile());
            watcherWorks = true;
            LOGGER.log(Level.INFO, "Proxy init'd pretty much ok.");
        }
        catch(Exception e){
            LOGGER.log(Level.WARNING, "Proxy could not create configuration watcher.  Proxy will not be able to update its configuration when it is modified.  Exception:", e);
            watcherWorks = false;
        }
        /*Load up the proxy configuration*/
        config = ProxyConfig.loadConfFromDisk();
    }
    
    /*
     * Forwards a request if it is permitted by the proxy's rules as configured.
     */
    @Override
    public void handle(Request request, Response response) {
        /* Check the proxy's config has been modified if the watcher was created correctly*/
        if (watcherWorks && configWatcher.isStale()) {
            //reload the config if it's stale
            config = ProxyConfig.loadConfFromDisk();
        }
        /* Grab the argument */
        Form f = request.getResourceRef().getQueryAsForm();
        /* The first argument should be the request for a URL to grab by proxy */
        String url = f.getFirstValue("url");
        try {
            /*Construct the connection to the server*/
            URL resolved = new URL(url);
            final HttpURLConnection connection = (HttpURLConnection) resolved.openConnection();
            connection.setRequestMethod(request.getMethod().toString());

            /*Check if this request is permitted to be forwarded*/
            if (checkPermission(resolved, connection.getContentType()) != true) {
                throw new RestletException("Request for nonpermitted content type or hostname",
                        Status.CLIENT_ERROR_BAD_REQUEST);
            }
                
            /*Appropriately forward the message*/
            if (request.getMethod().equals(Method.PUT)
                    || request.getMethod().equals(Method.POST)) {
                connection.setDoOutput(true);
                copyStream(request.getEntity().getStream(), connection.getOutputStream());
            }
                
            response.setEntity(new StreamRepresentation(new MediaType(connection
                    .getContentType())) {
                @Override
                public void write(OutputStream out) throws IOException {
                    copyStream(connection.getInputStream(), out);
                }

                @Override
                public InputStream getStream() throws IOException {
                    throw new UnsupportedOperationException();
                }
            });
        /*If the request is broken, offer appropriate notice*/
        } catch (MalformedURLException e) {
            LOGGER.log(Level.WARNING, "Invalid proxy request. ", e);
            throw new RestletException("Invalid proxy request", Status.CLIENT_ERROR_BAD_REQUEST);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Couldn't connect to proxied service", e);
            throw new RestletException("Couldn't connect to proxied service",
                    Status.SERVER_ERROR_BAD_GATEWAY);
        }
    }

    
    private boolean checkPermission(URL locator, String contentType) {
        /* Check that the correct protocol is being used */
        if (locator.getProtocol().equals("http")) {
            boolean hostnameOk = false, mimetypeOk = false;
            /* Check hostname and mimetype as appropriate to mode */
            if (config.mode == Mode.HOSTNAME || config.mode == Mode.HOSTNAMEANDMIMETYPE
                    || config.mode == Mode.HOSTNAMEORMIMETYPE) {
                hostnameOk = checkHostnamePermission(locator);
            }
            if (config.mode == Mode.MIMETYPE || config.mode == Mode.HOSTNAMEANDMIMETYPE
                    || config.mode == Mode.HOSTNAMEORMIMETYPE) {
                mimetypeOk = checkContentType(contentType);
            }
            /* Return whether an action is permitted based on how proxy is configured */
            switch (config.mode) {
            case MIMETYPE:
                return mimetypeOk;
            case HOSTNAME:
                return hostnameOk;
            case HOSTNAMEANDMIMETYPE:
                return hostnameOk && mimetypeOk;
            case HOSTNAMEORMIMETYPE:
                return hostnameOk || mimetypeOk;
            }
        }
        /* The request is not permitted to go through. */
        return false;
    }

    /* 
     * Checks a URL for whether its hostname is permitted
     * @param   locator         A URL to check the permission status of
     * @return                  true if the hostname is permitted; otherwise false 
     */
    private boolean checkHostnamePermission(URL locator) {
        /* Iterate through the whitelist of hosts */
        for (String hostname : config.hostnameWhitelist) {
            /* Check if the regex matches the URL. */
            if (hostname.equalsIgnoreCase(locator.getHost())) {
                /* Then the URL is allowed. */
                return true;
            }
        }
        //otherwise say no
        return false;
    }
    
    /* 
     * Checks whether the content-type of a request is permitted by the proxy
     * @param   contentType     A content type
     * @return                  true if the content-type is permitted; otherwise false 
     */
    private boolean checkContentType(String contentType) {
        //Trim off extraneous information
        String firstType = contentType.split(";")[0];
        //Check off the content type provided vs. permitted content types
        for (String legitContentType : config.mimetypeWhitelist) {
            if (firstType.equalsIgnoreCase(legitContentType)) {
                //if the content type is permitted, send the packet on through
                return true;
            }
        }
        //otherwise say no
        return false;
    }
    
    @Override
    public Logger getLogger()
    {
        return LOGGER;
    }

    private void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buff = new byte[4096];
        int length = 0;
        while ((length = in.read(buff)) != -1) {
            out.write(buff, 0, length);
        }
    }
    
}

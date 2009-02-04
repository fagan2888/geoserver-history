/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.rest;

import java.util.HashMap;
import java.util.Map;

import org.geoserver.ows.util.ResponseUtils;
import org.geoserver.rest.format.DataFormat;
import org.geoserver.rest.format.ReflectiveHTMLFormat;
import org.geoserver.rest.format.ReflectiveJSONFormat;
import org.geoserver.rest.format.ReflectiveXMLFormat;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;

/**
 * Base class for resources which work reflectively from underlying target objects.
 * <h2>HTTP Methods</h2>
 * <p>
 * By default this class allows access via GET, POST, PUT and denies access via DELETE. 
 * Subclasses should override this behavior via the methods 
 * {@link #allowGet()}, {@link #allowPost()}, {@link #allowPut()}, and {@link #allowDelete()}
 * as needed. 
 * </p>
 * <h2>Object Serialization and Deserialization</h2>
 * <p>
 * A reflective resource corresponds to another class of object, referred here as the 
 * "target" object. During GET requests instances of the target object can be serialized as 
 * HTML, XML, or JSON. During POST and PUT requests instances of the "target" object can be 
 * de-serialized as XML or JSON. HTML serialization is achieved via Freemarker, XML and JSON
 * (de)serialization is achieved via XStream. Serialization and de-serialization is performed
 * by the {@link DataFormat} class. The supported formats listed above can be customized as 
 * need be by overriding/extending the {@link #createSupportedFormats()} method.
 * </p>
 * 
 * @author David Winslow, OpenGeo
 * @author Justin Deoliveira, OpenGeo
 */
public abstract class ReflectiveResource extends AbstractResource {

    /**
     * Creates a new reflective resource.
     */
    public ReflectiveResource( Context context, Request request, Response response ) {
        super(context, request, response);
    }

    /**
     * Creates a new reflective resource relying on {@link #init(Context, Request, Response)} to 
     * initialize the resource.
     * 
     */
    public ReflectiveResource() {
    }
    
    /**
     * Handles a GET request by serializing the target object.
     * <p>
     * This method operates by:
     * <ol>
     *   <li>Determining the serialization format from {@link #getFormatGet()}
     *   <li>Getting the target object from {@link #handleObjectGet()}
     *   <li>Serializing to output
     *   <li>
     * </ol>
     * </p>
     * 
     * @see #handleObjectGet() 
     */
    @Override 
    public final void handleGet() {
        DataFormat format = getFormatGet();
        try {
            getResponse().setEntity(format.toRepresentation(handleObjectGet()));
        } 
        catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Gets the target object to be serialized in a GET request.
     */
    protected abstract Object handleObjectGet() throws Exception;

    /**
     * Returns true.
     */
    @Override
    public boolean allowPost() {
        return true;
    }

    /**
     * Handles a POST request by de-serializing the content of the request into an instance 
     * of the target object.
     * <p>
     * This method operates by:
     * <ol>
     *   <li>Determining the serialization format from {@link #getFormatPostOrPut()}
     *   <li>De-Serializing to an instanceof the target object.
     *   <li>Passing the target object to {@link #handleObjectPost(Object)}
     * </ol>
     * </p>
     * 
     * @see #handleObjectPost(Object)
     */
    @Override
    public final void handlePost() {
        DataFormat format = getFormatPostOrPut();
        Object object = format.toObject( getRequest().getEntity() );
        String location = null;
        try {
            location = handleObjectPost(object);
        } 
        catch (Exception e) {
            handleException(e);
        } 
        if ( location != null ) {
            // set the Location header by appending the location to the current resource
            String uri = getRequest().getResourceRef().getIdentifier();
            int question = uri.indexOf( '?' );
            if ( question != -1 ) {
                uri = uri.substring(0,question);
            }
            uri = ResponseUtils.appendPath(uri, location);
            getResponse().redirectSeeOther(uri);
            
            // set response 201
            getResponse().setStatus( Status.SUCCESS_CREATED );
        }
        else {
            
        }
    }

    /**
     * Handles a de-serialized instance of the target object in a POST request.
     * <p>
     * This method returns the location that the object can be retrieved from after the 
     * POST. This value is used to set the 'Location' header of the http response by 
     * appending this value to the uri used to make the request. For example consider the
     * following:
     * <pre>
     *   protected String handleObjectPost(Object object) {
     *     Foo foo = (Foo) object;
     *     doSomethingWithFoo(foo);
     *     
     *     return "foo";
     *   }
     *  </pre>
     *  Along with the POST request to "http://localhost:8080/geoserver/rest/foos". The resulting value
     *  of the 'Location' header would be "http://localhost:8080/geoserver/rest/foos/foo". 
     * </p>
     * @param object Instance of the target object.
     * 
     * @return The location of the resulting resource. 
     */
    protected abstract String handleObjectPost(Object object) throws Exception;

    /**
     * Returns true.
     */
    @Override
    public boolean allowPut() {
        return true;
    }

    /**
     * Handles a PUT request by de-serializing the content of the request into an instance 
     * of the target object.
     * <p>
     * This method operates by:
     * <ol>
     *   <li>Determining the serialization format from {@link #getFormatPostOrPut()}
     *   <li>De-serializing to an instance of the target object.
     *   <li>Passing the target object to {@link #handleObjectPut(Object)}
     * </ol>
     * </p>
     * 
     * @see #handleObjectPost(Object)
     */
    @Override
    public final void handlePut() {
        DataFormat format = getFormatPostOrPut();
        Object object = format.toObject( getRequest().getEntity() );
        try {
            handleObjectPut(object);
        } 
        catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Handles a de-serialized instance of the target object in a PUT request.
     * <p>
     * 
     * </p>
     * @param object Instance of the target object. 
     */
    protected abstract void handleObjectPut(Object object) throws Exception;

    /**
     * Handles a DELETE request.
     * <p>
     * This method simply delegates to {@link #handleObjectDelete()}. Note that the DELETE method is
     * not allowed by default, so if subclasses which to handle the DELETE method they must override
     * {@link #allowDelete()}. 
     * </p>
     * 
     * @see #handleObjectDelete()
     */
    @Override
    public final void handleDelete() {
        try {
            handleObjectDelete();
        } 
        catch (Exception e) {
            handleException(e);
        }
    }
    
    /**
     * Handles a delete on the target object.
     * <p>
     * Subclasses should override this method as well as {@link #allowDelete()} to handle the 
     * DELTE method.
     * </p>
     * 
     */
    protected void handleObjectDelete() throws Exception {
    }
    
    /**
     * Creates the map of formats used to serialize and de-serialize instances of the target object.
     * <p>
     * Keys in the map are file extensions (examples: xml,json), and mime types (examples: text/xml,
     * text/json). Values are instances of {@link DataFormat}. 
     *  </p>
     *  <p>
     *  Subclasses may override or extend this method to customize the supported formats. By default
     *  this method supports html, xml, and json. 
     *  </p>
     *  
     *  @see #createHTMLFormat()
     *  @see #createXMLFormat()
     *  @see #createJSONFormat()
     */
    protected Map<String, DataFormat> createSupportedFormats(Request request,Response response) {
        HashMap<String,DataFormat> formats = new HashMap<String, DataFormat>();
        formats.put( "xml" , createXMLFormat(request,response) );
        formats.put( "text/xml", formats.get( "xml") );
        formats.put( "json", createJSONFormat(request,response) );
        formats.put( "text/json", formats.get( "json") );
        formats.put( "html", createHTMLFormat(request,response));
        formats.put( "text/html", formats.get( "html" ) ); 
        formats.put(null, formats.get( "html") );
        
        return formats;
    }

    /**
     * Creates the data format for serializing objects as HTML.
     * <p>
     * This implementation returns a new instance of {@link ReflectiveHTMLFormat}. Subclasses 
     * may override as need be.
     * </p>
     */
    protected DataFormat createHTMLFormat(Request request,Response response) {
        return new ReflectiveHTMLFormat(request,response,this); 
    }

    /**
     * Creates the data format for serializing and de-serializing objects as XML.
     * <p>
     * This implementation returns a new instance of {@link ReflectiveXMLFormat}. Subclasses 
     * may override as need be.
     * </p>
     */
    protected ReflectiveXMLFormat createXMLFormat(Request request,Response response) {
        return new ReflectiveXMLFormat();
    }

    /**
     * Creates the data format for serializing and de-serializing objects as JSON.
     * <p>
     * This implementation returns a new instance of {@link ReflectiveJSONLFormat}. Subclasses 
     * may override as need be.
     * </p>
     */
    protected ReflectiveJSONFormat createJSONFormat(Request request,Response response) {
        return new ReflectiveJSONFormat();
    }

    /**
     * Convenience method for subclasses to look up the value of an attribute from the request, 
     * ie {@link Request#getAttributes()}.
     * 
     * @param attribute THe name of the attribute to lookup.
     * 
     * @return The value as a string, or null if the attribute does not exist.
     */
    protected String getAttribute(String attribute) {
        Object o = getRequest().getAttributes().get( attribute );
        
        if ( o == null ) {
            return null;
        }
        
        return o.toString();
    }
    
    /**
     * Helper method which checks if a restlet exception was thrown, if so it throws it, else
     * wraps it in a restlet exception.
     */
    void handleException( Exception e ) throws RestletException {
        if ( e instanceof RestletException ) {
            throw (RestletException) e ;
        }
        
        throw new RestletException( "", Status.SERVER_ERROR_INTERNAL, e );
    }

}

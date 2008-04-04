package org.geoserver.geosearch;

import org.restlet.Restlet;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Method;
import org.restlet.data.MediaType;
import org.restlet.data.Status;

import org.vfny.geoserver.global.GeoServer;
import org.vfny.geoserver.global.Data;
import org.vfny.geoserver.global.NameSpaceInfo;
import org.vfny.geoserver.config.DataConfig;
import org.geoserver.ows.util.RequestUtils;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class SiteMapRestlet extends GeoServerProxyAwareRestlet {

    private Data myData;
    private DataConfig myDataConfig;
    private String GEOSERVER_ROOT;
    private Namespace SITEMAP = Namespace.getNamespace("http://www.sitemaps.org/schemas/sitemap/0.9");

    public Data getData(){
        return myData;
    }

    public DataConfig getDataConfig(){
        return myDataConfig;
    }

    public void setData(Data d){
        myData = d;
    }

    public void setDataConfig(DataConfig dc){
        myDataConfig = dc;
    }

    public void handle(Request request, Response response){
        GEOSERVER_ROOT = getBaseURL(request);

        if (request.getMethod().equals(Method.GET)){
            doGet(request, response);
        } else { 
            response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    public void doGet(Request request, Response response){
        Document d = new Document();
        Element urlset = new Element("urlset", SITEMAP);
        d.setRootElement(urlset);

        NameSpaceInfo[] namespaces = getData().getNameSpaces();
        for (int i = 0; i < namespaces.length; i++){
            addUrl(urlset, GEOSERVER_ROOT + "/geosearch/" + namespaces[i].getPrefix() + ".kml");
        }

        response.setEntity(new JDOMRepresentation(d));
    }

    private void addUrl(Element urlset, String url){
        Element urlElement = new Element("url", SITEMAP);
        Element loc = new Element("loc", SITEMAP);
        loc.setText(url);
        urlElement.addContent(loc);
        urlset.addContent(urlElement);
    }

    public static String getParentUrl(String url){
        while (url.endsWith("/")){
            url = url.substring(0, url.length() - 1);
        }

        int lastSlash = url.lastIndexOf('/');
        if (lastSlash != -1){
            url = url.substring(0, lastSlash);
        }

        return url;
    }
}

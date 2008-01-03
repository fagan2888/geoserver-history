/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.wfs.response;

import net.opengis.wfs.ActionType;
import net.opengis.wfs.GetFeatureType;
import net.opengis.wfs.InsertResultsType;
import net.opengis.wfs.InsertedFeatureType;
import net.opengis.wfs.TransactionResponseType;
import net.opengis.wfs.TransactionResultsType;
import net.opengis.wfs.TransactionType;

import org.geoserver.ows.Response;
import org.geoserver.ows.util.RequestUtils;
import org.geoserver.ows.util.ResponseUtils;
import org.geoserver.platform.Operation;
import org.geoserver.platform.ServiceException;
import org.geoserver.wfs.WFS;
import org.geoserver.wfs.WFSException;
import org.geoserver.wfs.xml.v1_1_0.WFSConfiguration;
import org.geotools.util.Version;
import org.geotools.xml.Encoder;
import org.opengis.filter.identity.FeatureId;
import org.vfny.geoserver.global.Data;
import org.xml.sax.SAXException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;


public class TransactionResponse extends Response {
    private boolean verbose = false;
    private String indent = " ";
    private String offset = "";
    WFS wfs;
    Data catalog;
    WFSConfiguration configuration;

    public TransactionResponse(WFS wfs, Data catalog, WFSConfiguration configuration) {
        super(TransactionResponseType.class);
        this.wfs = wfs;
        this.catalog = catalog;
        this.configuration = configuration;
    }

    public String getMimeType(Object value, Operation operation)
        throws ServiceException {
        return "text/xml";
    }

    public void write(Object value, OutputStream output, Operation operation)
        throws IOException, ServiceException {
        TransactionResponseType response = (TransactionResponseType) value;

        if (new Version("1.0.0").equals(operation.getService().getVersion())) {
            v_1_0(response, output, operation);
        } else {
            v_1_1(response, output, operation);
        }
    }

    public void v_1_0(TransactionResponseType response, OutputStream output, Operation operation)
        throws IOException, ServiceException {
        TransactionResultsType result = response.getTransactionResults();

        Writer writer = new OutputStreamWriter(output);
        writer = new BufferedWriter(writer);

        //boolean verbose = ConfigInfo.getInstance().formatOutput();
        //String indent = ((verbose) ? "\n" + OFFSET : " ");
        String encoding = wfs.getCharSet().displayName();
        String xmlHeader = "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>";
        writer.write(xmlHeader);

        if (verbose) {
            writer.write("\n");
        }

        writer.write("<wfs:WFS_TransactionResponse");
        writer.write(indent + "version=\"1.0.0\"");
        writer.write(indent + "xmlns:wfs=\"http://www.opengis.net/wfs\"");

        writer.write(indent + "xmlns:ogc=\"http://www.opengis.net/ogc\"");

        writer.write(indent + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
        writer.write(indent);
        writer.write("xsi:schemaLocation=\"http://www.opengis.net/wfs ");

        TransactionType req = (TransactionType)operation.getParameters()[0];
        String proxifiedBaseUrl = RequestUtils.proxifiedBaseURL(req.getBaseUrl(), wfs.getGeoServer().getProxyBaseUrl());
        
        String baseUrl = ResponseUtils.appendPath(proxifiedBaseUrl,
                "schemas/wfs/1.0.0/WFS-transaction.xsd");

        writer.write(baseUrl);
        writer.write("\">");

        InsertResultsType insertResults = response.getInsertResults();

        //JD: make sure we group insert results by handle, this is wfs 1.0 cite
        // thing that conflicts with wfs 1.1 cite, i am pretty sure its just a 
        // problem with the 1.0 tests
        String lastHandle = null;
        boolean first = true;

        if (insertResults != null) {
            for (Iterator i = insertResults.getFeature().iterator(); i.hasNext();) {
                InsertedFeatureType insertedFeature = (InsertedFeatureType) i.next();
                String handle = insertedFeature.getHandle();

                if (first || ((lastHandle == null) && (handle != null))
                        || ((lastHandle != null) && (handle != null) && handle.equals(lastHandle))) {
                    if (!first) {
                        //close last one, if not the first time through
                        writer.write("</wfs:InsertResult>");
                    }

                    writer.write("<wfs:InsertResult");

                    if (insertedFeature.getHandle() != null) {
                        writer.write(" handle=\"" + insertedFeature.getHandle() + "\"");
                    }

                    writer.write(">");
                }

                for (Iterator id = insertedFeature.getFeatureId().iterator(); id.hasNext();) {
                    FeatureId featureId = (FeatureId) id.next();
                    writer.write("<ogc:FeatureId fid=\"" + featureId.toString() + "\"/>");
                }

                first = false;
                lastHandle = handle;
            }

            writer.write("</wfs:InsertResult>");
        }

        writer.write(indent + "<wfs:TransactionResult");

        if (result.getHandle() != null) {
            writer.write(" handle=\"" + result.getHandle() + "\"");
        }

        writer.write(">");
        writer.write(indent + offset + "<wfs:Status>");
        writer.write(indent + offset + offset);

        //if there is an actino, that means we failed
        if (!result.getAction().isEmpty()) {
            writer.write("<wfs:FAILED/>");
        } else {
            writer.write("<wfs:SUCCESS/>");
        }

        writer.write(indent + offset + "</wfs:Status>");

        if (!result.getAction().isEmpty()) {
            ActionType action = (ActionType) result.getAction().get(0);

            if (action.getLocator() != null) {
                writer.write(indent + offset + "<wfs:Locator>");
                writer.write(action.getLocator() + "</wfs:Locator>");
            }

            if (action.getMessage() != null) {
                writer.write(indent + offset + "<wfs:Message>");
                ResponseUtils.writeEscapedString(writer, action.getMessage());
                writer.write("</wfs:Message>");
            }
        }

        writer.write(indent + "</wfs:TransactionResult>");

        if (verbose) {
            writer.write("\n");
        }

        writer.write("</wfs:WFS_TransactionResponse>");
        writer.flush();
    }

    public void v_1_1(TransactionResponseType response, OutputStream output, Operation operation)
        throws IOException, ServiceException {
        if (!response.getTransactionResults().getAction().isEmpty()) {
            //since we do atomic transactions, an action failure means all we rolled back
            // spec says to throw exception
            ActionType action = (ActionType) response.getTransactionResults().getAction().iterator()
                                                     .next();
            throw new WFSException(action.getMessage(), action.getCode(), action.getLocator());
        }

        Encoder encoder = new Encoder(configuration, configuration.schema());

        TransactionType req = (TransactionType)operation.getParameters()[0];
        String proxifiedBaseUrl = RequestUtils.proxifiedBaseURL(req.getBaseUrl(), wfs.getGeoServer().getProxyBaseUrl());
        
        encoder.setSchemaLocation(org.geoserver.wfs.xml.v1_1_0.WFS.NAMESPACE,
            ResponseUtils.appendPath(proxifiedBaseUrl, "schemas/wfs/1.1.0/wfs.xsd"));
        encoder.encode(response, org.geoserver.wfs.xml.v1_1_0.WFS.TRANSACTIONRESPONSE, output);
       
    }
}

/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.gss.xml;

import java.io.IOException;
import java.util.Set;

import javax.xml.namespace.QName;

import org.eclipse.xsd.XSDSchema;
import org.geoserver.wfs.xml.v1_1_0.WFS;
import org.geotools.xml.XSD;

/**
 * This interface contains the qualified names of all the types,elements, and attributes in the
 * http://geoserver.org/gss schema.
 */
public class GSS extends XSD {

    public static final String NAMESPACE = "http://geoserver.org/gss";

    /* Type Definitions */
    public static final QName GetCentralRevisionType = new QName(NAMESPACE, "GetCentralRevisionType");
    public static final QName CentralRevisionsType = new QName(NAMESPACE, "CentralRevisionsType");
    public static final QName LayerRevisionType = new QName(NAMESPACE, "LayerRevisionType");
    public static final QName PostDiffType = new QName(NAMESPACE, "PostDiffType");
    
    /* Element definitions */
    public static final QName GetCentralRevision = new QName(NAMESPACE, "GetCentralRevision");
    public static final QName CentralRevisions = new QName(NAMESPACE, "CentralRevisions");
    public static final QName PostDiff = new QName(NAMESPACE, "PostDiff");

    /** wfs dependency */
    WFS wfs;

    public GSS(WFS wfs) {
        this.wfs = wfs;
    }

    @Override
    protected void addDependencies(Set dependencies) {
        super.addDependencies(dependencies);

        dependencies.add(wfs);
    }

    @Override
    public String getNamespaceURI() {
        return NAMESPACE;
    }

    @Override
    public String getSchemaLocation() {
        return getClass().getResource("gss.xsd").toString();
    }

    @Override
    protected XSDSchema buildSchema() throws IOException {
        XSDSchema gssSchema = super.buildSchema();
        gssSchema = wfs.getSchemaBuilder().addApplicationTypes(gssSchema);
        return gssSchema;
    }
}

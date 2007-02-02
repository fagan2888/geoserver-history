/* Copyright (c) 2001, 2003 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.ows.xml.v1_0;

import net.opengis.ows.v1_0_0.ExceptionReportType;
import org.geotools.xml.AbstractComplexEMFBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;
import javax.xml.namespace.QName;


/**
 * Binding object for the element http://www.opengis.net/ows:ExceptionReport.
 *
 * <p>
 *        <pre>
 *         <code>
 *  &lt;element name="ExceptionReport"&gt;
 *      &lt;annotation&gt;
 *          &lt;documentation&gt;Report message returned to the client that requested any OWS operation when the server detects an error while processing that operation request. &lt;/documentation&gt;
 *      &lt;/annotation&gt;
 *      &lt;complexType&gt;
 *          &lt;sequence&gt;
 *              &lt;element maxOccurs="unbounded" ref="ows:Exception"&gt;
 *                  &lt;annotation&gt;
 *                      &lt;documentation&gt;Unordered list of one or more Exception elements that each describes an error. These Exception elements shall be interpreted by clients as being independent of one another (not hierarchical). &lt;/documentation&gt;
 *                  &lt;/annotation&gt;
 *              &lt;/element&gt;
 *          &lt;/sequence&gt;
 *          &lt;attribute name="version" type="string" use="required"&gt;
 *              &lt;annotation&gt;
 *                  &lt;documentation&gt;Specification version for OWS operation. The string value shall contain one x.y.z "version" value (e.g., "2.1.3"). A version number shall contain three non-negative integers separated by decimal points, in the form "x.y.z". The integers y and z shall not exceed 99. Each version shall be for the Implementation Specification (document) and the associated XML Schemas to which requested operations will conform. An Implementation Specification version normally specifies XML Schemas against which an XML encoded operation response must conform and should be validated. See Version negotiation subclause for more information. &lt;/documentation&gt;
 *              &lt;/annotation&gt;
 *          &lt;/attribute&gt;
 *          &lt;attribute name="language" type="language" use="optional"&gt;
 *              &lt;annotation&gt;
 *                  &lt;documentation&gt;Identifier of the language used by all included exception text values. These language identifiers shall be as specified in IETF RFC 1766. When this attribute is omitted, the language used is not identified. &lt;/documentation&gt;
 *              &lt;/annotation&gt;
 *          &lt;/attribute&gt;
 *      &lt;/complexType&gt;
 *  &lt;/element&gt;
 *
 *          </code>
 *         </pre>
 * </p>
 *
 * @generated
 */
public class ExceptionReportBinding extends AbstractComplexEMFBinding {
    /**
     * @generated
     */
    public QName getTarget() {
        return OWS.EXCEPTIONREPORT;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Class getType() {
        return ExceptionReportType.class;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Object parse(ElementInstance instance, Node node, Object value)
        throws Exception {
        //TODO: implement
        return null;
    }
}

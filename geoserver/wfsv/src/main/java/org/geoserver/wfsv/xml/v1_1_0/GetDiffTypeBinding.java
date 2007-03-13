/* Copyright (c) 2001, 2003 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.wfsv.xml.v1_1_0;

import org.geotools.xml.*;
import javax.xml.namespace.QName;


/**
 * Binding object for the type http://www.opengis.net/wfsv:GetDiffType.
 *
 * <p>
 *        <pre>
 *         <code>
 *  &lt;xsd:complexType name="GetDiffType"&gt;
 *      &lt;xsd:annotation&gt;
 *          &lt;xsd:documentation&gt;
 *              A GetDiff element contains one or more DifferenceQuery elements
 *              that describe a difference query operation on one feature type.  In
 *              response to a GetDiff request, a Versioning Web Feature Service
 *              must be able to generate a Transaction command that can be used
 *              to alter features at fromFeatureVersion and alter them into features
 *              at toFeatureVersion
 *           &lt;/xsd:documentation&gt;
 *      &lt;/xsd:annotation&gt;
 *      &lt;xsd:complexContent&gt;
 *          &lt;xsd:extension base="wfs:BaseRequestType"&gt;
 *              &lt;xsd:sequence&gt;
 *                  &lt;xsd:element maxOccurs="unbounded" ref="wfsv:DifferenceQuery"/&gt;
 *              &lt;/xsd:sequence&gt;
 *              &lt;xsd:attribute
 *                  default="application/xml; subtype=wfsv-transaction/1.1.0"
 *                  name="outputFormat" type="xsd:string" use="optional"&gt;
 *                  &lt;xsd:annotation&gt;
 *                      &lt;xsd:documentation&gt;
 *                       The outputFormat attribute is used to specify the output
 *                       format that the Versioning Web Feature Service should generate in
 *                       response to a GetDiff or GetFeatureWithLock element.
 *                       The default value of 'application/xml; subtype=wfsv-transaction/1.1.0'
 *                       indicates that the output is an XML document that
 *                       conforms to the WFS 1.1.0 Transaction definition.
 *                       For the purposes of experimentation, vendor extension,
 *                       or even extensions that serve a specific community of
 *                       interest, other acceptable output format values may be
 *                       used to specify other formats as long as those values
 *                       are advertised in the capabilities document.
 *                    &lt;/xsd:documentation&gt;
 *                  &lt;/xsd:annotation&gt;
 *              &lt;/xsd:attribute&gt;
 *          &lt;/xsd:extension&gt;
 *      &lt;/xsd:complexContent&gt;
 *  &lt;/xsd:complexType&gt;
 *
 *          </code>
 *         </pre>
 * </p>
 *
 * @generated
 */
public class GetDiffTypeBinding extends AbstractComplexBinding {
    /**
     * @generated
     */
    public QName getTarget() {
        return WFSV.GetDiffType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Class getType() {
        return null;
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

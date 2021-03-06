/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.wfsv.xml.v1_1_0;

import java.math.BigInteger;

import javax.xml.namespace.QName;

import net.opengis.wfs.ResultTypeType;
import net.opengis.wfsv.DifferenceQueryType;
import net.opengis.wfsv.GetLogType;
import net.opengis.wfsv.WfsvFactory;

import org.geotools.xml.AbstractComplexBinding;
import org.geotools.xml.ElementInstance;
import org.geotools.xml.Node;


/**
 * Binding object for the type http://www.opengis.net/wfsv:GetLogType.
 *
 * <p>
 *        <pre>
 *         <code>
 *  &lt;xsd:complexType name="GetLogType"&gt;
 *      &lt;xsd:annotation&gt;
 *          &lt;xsd:documentation&gt;
 *              A GetLog element contains one or more DifferenceQuery elements
 *              that describe a diffence query operation on one feature type.
 *              In response to a GetLog request, a Web Feature Service
 *              must be able to generate a list of logs entries for features matched
 *              by the DifferenceQuery parameters. Each log entry is an instance
 *              of the ChangeSet feature type.
 *              In response to a GetFeature request, a Versioning Web Feature Service
 *              must be able to generate a GML3 response that validates
 *              using a schema generated by the DescribeFeatureType request against
 *              the ChangeSets feature type.
 *              A Web Feature Service may support other possibly non-XML
 *              (and even binary) output formats as long as those formats
 *              are advertised in the capabilities document.
 *           &lt;/xsd:documentation&gt;
 *      &lt;/xsd:annotation&gt;
 *      &lt;xsd:complexContent&gt;
 *          &lt;xsd:extension base="wfs:BaseRequestType"&gt;
 *              &lt;xsd:sequence&gt;
 *                  &lt;xsd:element maxOccurs="unbounded" ref="wfsv:DifferenceQuery"/&gt;
 *              &lt;/xsd:sequence&gt;
 *              &lt;xsd:attribute default="results" name="resultType"
 *                  type="wfs:ResultTypeType" use="optional"&gt;
 *                  &lt;xsd:annotation&gt;
 *                      &lt;xsd:documentation&gt;
 *                       The resultType attribute is used to indicate
 *                       what response a wfsv should return to user once
 *                       a GetFeature request is processed.
 *                       Possible values are:
 *                          results - meaning that the full response set
 *                                    (i.e. all the feature instances)
 *                                    should be returned.
 *                          hits    - meaning that an empty response set
 *                                    should be returned (i.e. no feature
 *                                    instances should be returned) but
 *                                    the "numberOfFeatures" attribute
 *                                    should be set to the number of feature
 *                                    instances that would be returned.
 *                    &lt;/xsd:documentation&gt;
 *                  &lt;/xsd:annotation&gt;
 *              &lt;/xsd:attribute&gt;
 *              &lt;xsd:attribute default="text/xml; subtype=gml/3.1.1"
 *                  name="outputFormat" type="xsd:string" use="optional"&gt;
 *                  &lt;xsd:annotation&gt;
 *                      &lt;xsd:documentation&gt;
 *                       The outputFormat attribute is used to specify the output
 *                       format that the Versioning Web Feature Service should generate in
 *                       response to a GetLog element.
 *                       The default value of 'text/xml; subtype=gml/3.1.1'
 *                       indicates that the output is an XML document that
 *                       conforms to the Geography Markup Language (GML)
 *                       Implementation Specification V3.1.1.
 *                       For the purposes of experimentation, vendor extension,
 *                       or even extensions that serve a specific community of
 *                       interest, other acceptable output format values may be
 *                       used to specify other formats as long as those values
 *                       are advertised in the capabilities document.
 *                    &lt;/xsd:documentation&gt;
 *                  &lt;/xsd:annotation&gt;
 *              &lt;/xsd:attribute&gt;
 *              &lt;xsd:attribute name="maxFeatures" type="xsd:positiveInteger" use="optional"&gt;
 *                  &lt;xsd:annotation&gt;
 *                      &lt;xsd:documentation&gt;
 *                       The maxFeatures attribute is used to specify the maximum
 *                       number of features that a GetFeature operation should
 *                       generate (regardless of the actual number of query hits).
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
public class GetLogTypeBinding extends AbstractComplexBinding {
    private WfsvFactory wfsvFactory;

    public GetLogTypeBinding(WfsvFactory wfsvFactory) {
        this.wfsvFactory = wfsvFactory;
    }

    /**
     * @generated
     */
    public QName getTarget() {
        return WFSV.GetLogType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Class getType() {
        return GetLogType.class;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Object parse(ElementInstance instance, Node node, Object value)
        throws Exception {
        GetLogType result = wfsvFactory.createGetLogType();
        result.getDifferenceQuery().addAll(node.getChildValues(DifferenceQueryType.class));

        if (node.hasAttribute("resultType")) {
            result.setResultType((ResultTypeType) node.getAttributeValue("resultType"));
        }
        if(node.hasAttribute("version"))
            result.setVersion((String) node.getAttributeValue("version"));
        else
            result.setVersion("1.1.0");

        if (node.hasAttribute("outputFormat")) {
            result.setOutputFormat((String) node.getAttributeValue("outputFormat"));
        } else {
            if("1.0.0".equals(result.getVersion()))
                result.setOutputFormat("GML2");
            else
                result.setOutputFormat("text/xml; subtype=gml/3.1.1");
        }
        
        if(node.hasAttribute("maxFeatures")) {
            result.setMaxFeatures((BigInteger) node.getAttributeValue("maxFeatures"));
        }

        return result;
    }

}

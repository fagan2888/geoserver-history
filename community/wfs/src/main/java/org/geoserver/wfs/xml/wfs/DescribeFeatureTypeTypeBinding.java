package org.geoserver.wfs.xml.wfs;


import org.geotools.xml.*;
import org.xml.sax.helpers.NamespaceSupport;

import net.opengis.wfs.DescribeFeatureTypeType;
import net.opengis.wfs.WFSFactory;		

import javax.xml.namespace.QName;

/**
 * Binding object for the type http://www.opengis.net/wfs:DescribeFeatureTypeType.
 *
 * <p>
 *	<pre>
 *	 <code>
 *  &lt;xsd:complexType name="DescribeFeatureTypeType"&gt;       
 *  		&lt;xsd:annotation&gt;
 *              &lt;xsd:documentation&gt;             The DescribeFeatureType
 *              operation allows a client application             to request
 *              that a Web Feature Service describe one or more
 *              feature types.   A Web Feature Service must be able to
 *              generate             feature descriptions as valid GML2
 *              application schemas.              The schemas generated by
 *              the DescribeFeatureType operation can             be used by
 *              a client application to validate the output.
 *              Feature instances within the WFS interface must be specified
 *              using GML2.  The schema of feature instances specified
 *              within             the WFS interface must validate against
 *              the feature schemas              generated by the
 *              DescribeFeatureType request.          &lt;/xsd:documentation&gt;
 *      &lt;/xsd:annotation&gt;       &lt;xsd:sequence&gt;          &lt;xsd:element
 *              maxOccurs="unbounded" minOccurs="0" name="TypeName"
 *              type="xsd:QName"&gt;             &lt;xsd:annotation&gt;
 *                      &lt;xsd:documentation&gt;                   The TypeName
 *                      element is used to enumerate the feature types
 *                      to be described.  If no TypeName elements are
 *                      specified                   then all features should
 *                      be described.                &lt;/xsd:documentation&gt;
 *              &lt;/xsd:annotation&gt;          &lt;/xsd:element&gt;
 *      &lt;/xsd:sequence&gt;       &lt;xsd:attribute fixed="1.0.0" name="version"
 *          type="xsd:string" use="required"/&gt;       &lt;xsd:attribute
 *          fixed="WFS" name="service" type="xsd:string" use="required"/&gt;
 *          &lt;xsd:attribute default="XMLSCHEMA" name="outputFormat"
 *          type="xsd:string" use="optional"&gt;          &lt;xsd:annotation&gt;
 *                  &lt;xsd:documentation&gt;                The outputFormat
 *                  attribute is used to specify what schema
 *                  description language should be used to describe
 *                  features.                The default value of XMLSCHEMA
 *                  means that the Web Feature                Service must
 *                  generate a GML2 application schema that can
 *                  be used to validate the GML2 output of a GetFeature
 *                  request                or feature instances specified in
 *                  Transaction operations.             &lt;/xsd:documentation&gt;
 *          &lt;/xsd:annotation&gt;       &lt;/xsd:attribute&gt;    &lt;/xsd:complexType&gt; 
 *		
 *	  </code>
 *	 </pre>
 * </p>
 *
 * @generated
 */
public class DescribeFeatureTypeTypeBinding extends AbstractComplexBinding {

	WFSFactory wfsfactory;		
	
	public DescribeFeatureTypeTypeBinding( WFSFactory wfsfactory ) {
		this.wfsfactory = wfsfactory;
	}

	/**
	 * @generated
	 */
	public QName getTarget() {
		return WFS.DESCRIBEFEATURETYPETYPE;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *	
	 * @generated modifiable
	 */	
	public Class getType() {
		return DescribeFeatureTypeType.class;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *	
	 * @generated modifiable
	 */	
	public Object parse(ElementInstance instance, Node node, Object value) 
		throws Exception {
		
		DescribeFeatureTypeType describeFeatureType 
			= wfsfactory.createDescribeFeatureTypeType();
		
		WFSBindingUtils.service( describeFeatureType, node );
		WFSBindingUtils.version( describeFeatureType, node );
		WFSBindingUtils.outputFormat( describeFeatureType, node, "XMLSCHEMA" );
		
		QName typeName = (QName) node.getChildValue( QName.class );
		describeFeatureType.setTypeName( typeName );
		
		return describeFeatureType;
	}

}
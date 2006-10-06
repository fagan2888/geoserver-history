package org.geoserver.wfs.xml.wfs;


import org.geotools.xml.*;

import net.opengis.wfs.WFSFactory;		

import javax.xml.namespace.QName;

/**
 * Binding object for the type http://www.opengis.net/wfs:WFS_TransactionResponseType.
 *
 * <p>
 *	<pre>
 *	 <code>
 *  &lt;xsd:complexType name="WFS_TransactionResponseType"&gt;
 *      &lt;xsd:annotation&gt;
 *          &lt;xsd:documentation&gt;
 *              The WFS_TransactionResponseType defines the format of
 *              the XML document that a Web Feature Service generates 
 *              in response to a Transaction request.  The response 
 *              includes the completion status of the transaction 
 *              and the feature identifiers of any newly created
 *              feature instances.
 *           &lt;/xsd:documentation&gt;
 *      &lt;/xsd:annotation&gt;
 *      &lt;xsd:sequence&gt;
 *          &lt;xsd:element maxOccurs="unbounded" minOccurs="0"
 *              name="InsertResult" type="wfs:InsertResultType"&gt;
 *              &lt;xsd:annotation&gt;
 *                  &lt;xsd:documentation&gt;
 *                    The InsertResult element contains a list of ogc:FeatureId
 *                    elements that identify any newly created feature instances.
 *                 &lt;/xsd:documentation&gt;
 *              &lt;/xsd:annotation&gt;
 *          &lt;/xsd:element&gt;
 *          &lt;xsd:element name="TransactionResult" type="wfs:TransactionResultType"&gt;
 *              &lt;xsd:annotation&gt;
 *                  &lt;xsd:documentation&gt;
 *                    The TransactionResult element contains a Status element
 *                    indicating the completion status of a transaction.  In
 *                    the event that the transaction fails, additional element
 *                    may be included to help locate which part of the transaction
 *                    failed and why.
 *                 &lt;/xsd:documentation&gt;
 *              &lt;/xsd:annotation&gt;
 *          &lt;/xsd:element&gt;
 *      &lt;/xsd:sequence&gt;
 *      &lt;xsd:attribute fixed="1.0.0" name="version" type="xsd:string" use="required"/&gt;
 *  &lt;/xsd:complexType&gt; 
 *		
 *	  </code>
 *	 </pre>
 * </p>
 *
 * @generated
 */
public class WFS_TransactionResponseTypeBinding extends AbstractComplexBinding {

	WFSFactory wfsfactory;		
	public WFS_TransactionResponseTypeBinding( WFSFactory wfsfactory ) {
		this.wfsfactory = wfsfactory;
	}

	/**
	 * @generated
	 */
	public QName getTarget() {
		return WFS.WFS_TRANSACTIONRESPONSETYPE;
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
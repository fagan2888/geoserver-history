/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.opengis.wfs.v1_1_0;

import java.math.BigInteger;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Get Feature With Lock Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             A GetFeatureWithLock request operates identically to a
 *             GetFeature request expect that it attempts to lock the
 *             feature instances in the result set and includes a lock
 *             identifier in its response to a client.  A lock identifier
 *             is an identifier generated by a Web Feature Service that
 *             a client application can use, in subsequent operations,
 *             to reference the locked set of feature instances.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getQuery <em>Query</em>}</li>
 *   <li>{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getExpiry <em>Expiry</em>}</li>
 *   <li>{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getMaxFeatures <em>Max Features</em>}</li>
 *   <li>{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getOutputFormat <em>Output Format</em>}</li>
 *   <li>{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getResultType <em>Result Type</em>}</li>
 *   <li>{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getTraverseXlinkDepth <em>Traverse Xlink Depth</em>}</li>
 *   <li>{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getTraverseXlinkExpiry <em>Traverse Xlink Expiry</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.opengis.wfs.v1_1_0.WFSPackage#getGetFeatureWithLockType()
 * @model extendedMetaData="name='GetFeatureWithLockType' kind='elementOnly'"
 * @generated
 */
public interface GetFeatureWithLockType extends BaseRequestType{
	/**
	 * Returns the value of the '<em><b>Query</b></em>' containment reference list.
	 * The list contents are of type {@link net.opengis.wfs.v1_1_0.QueryType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Query</em>' containment reference list.
	 * @see net.opengis.wfs.v1_1_0.WFSPackage#getGetFeatureWithLockType_Query()
	 * @model type="net.opengis.wfs.v1_1_0.QueryType" containment="true" resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='Query' namespace='##targetNamespace'"
	 * @generated
	 */
	EList getQuery();

	/**
	 * Returns the value of the '<em><b>Expiry</b></em>' attribute.
	 * The default value is <code>"5"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 *                      The expiry attribute is used to set the length
	 *                      of time (expressed in minutes) that features will
	 *                      remain locked as a result of a GetFeatureWithLock
	 *                      request.  After the expiry period elapses, the
	 *                      locked resources must be released.  If the
	 *                      expiry attribute is not set, then the default
	 *                      value of 5 minutes will be enforced.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Expiry</em>' attribute.
	 * @see #isSetExpiry()
	 * @see #unsetExpiry()
	 * @see #setExpiry(BigInteger)
	 * @see net.opengis.wfs.v1_1_0.WFSPackage#getGetFeatureWithLockType_Expiry()
	 * @model default="5" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.PositiveInteger"
	 *        extendedMetaData="kind='attribute' name='expiry'"
	 * @generated
	 */
	BigInteger getExpiry();

	/**
	 * Sets the value of the '{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getExpiry <em>Expiry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expiry</em>' attribute.
	 * @see #isSetExpiry()
	 * @see #unsetExpiry()
	 * @see #getExpiry()
	 * @generated
	 */
	void setExpiry(BigInteger value);

	/**
	 * Unsets the value of the '{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getExpiry <em>Expiry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetExpiry()
	 * @see #getExpiry()
	 * @see #setExpiry(BigInteger)
	 * @generated
	 */
	void unsetExpiry();

	/**
	 * Returns whether the value of the '{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getExpiry <em>Expiry</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Expiry</em>' attribute is set.
	 * @see #unsetExpiry()
	 * @see #getExpiry()
	 * @see #setExpiry(BigInteger)
	 * @generated
	 */
	boolean isSetExpiry();

	/**
	 * Returns the value of the '<em><b>Max Features</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 *                      See definition of wfs:GetFeatureType.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Max Features</em>' attribute.
	 * @see #setMaxFeatures(BigInteger)
	 * @see net.opengis.wfs.v1_1_0.WFSPackage#getGetFeatureWithLockType_MaxFeatures()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.PositiveInteger"
	 *        extendedMetaData="kind='attribute' name='maxFeatures'"
	 * @generated
	 */
	BigInteger getMaxFeatures();

	/**
	 * Sets the value of the '{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getMaxFeatures <em>Max Features</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Features</em>' attribute.
	 * @see #getMaxFeatures()
	 * @generated
	 */
	void setMaxFeatures(BigInteger value);

	/**
	 * Returns the value of the '<em><b>Output Format</b></em>' attribute.
	 * The default value is <code>"text/xml; subtype=gml/3.1.1"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 *                      See definition of wfs:GetFeatureType.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Output Format</em>' attribute.
	 * @see #isSetOutputFormat()
	 * @see #unsetOutputFormat()
	 * @see #setOutputFormat(String)
	 * @see net.opengis.wfs.v1_1_0.WFSPackage#getGetFeatureWithLockType_OutputFormat()
	 * @model default="text/xml; subtype=gml/3.1.1" unique="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='outputFormat'"
	 * @generated
	 */
	String getOutputFormat();

	/**
	 * Sets the value of the '{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getOutputFormat <em>Output Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Format</em>' attribute.
	 * @see #isSetOutputFormat()
	 * @see #unsetOutputFormat()
	 * @see #getOutputFormat()
	 * @generated
	 */
	void setOutputFormat(String value);

	/**
	 * Unsets the value of the '{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getOutputFormat <em>Output Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOutputFormat()
	 * @see #getOutputFormat()
	 * @see #setOutputFormat(String)
	 * @generated
	 */
	void unsetOutputFormat();

	/**
	 * Returns whether the value of the '{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getOutputFormat <em>Output Format</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Output Format</em>' attribute is set.
	 * @see #unsetOutputFormat()
	 * @see #getOutputFormat()
	 * @see #setOutputFormat(String)
	 * @generated
	 */
	boolean isSetOutputFormat();

	/**
	 * Returns the value of the '<em><b>Result Type</b></em>' attribute.
	 * The default value is <code>"results"</code>.
	 * The literals are from the enumeration {@link net.opengis.wfs.v1_1_0.ResultTypeType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 *                      See definition of wfs:GetFeatureType.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Result Type</em>' attribute.
	 * @see net.opengis.wfs.v1_1_0.ResultTypeType
	 * @see #isSetResultType()
	 * @see #unsetResultType()
	 * @see #setResultType(ResultTypeType)
	 * @see net.opengis.wfs.v1_1_0.WFSPackage#getGetFeatureWithLockType_ResultType()
	 * @model default="results" unique="false" unsettable="true"
	 *        extendedMetaData="kind='attribute' name='resultType'"
	 * @generated
	 */
	ResultTypeType getResultType();

	/**
	 * Sets the value of the '{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getResultType <em>Result Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Type</em>' attribute.
	 * @see net.opengis.wfs.v1_1_0.ResultTypeType
	 * @see #isSetResultType()
	 * @see #unsetResultType()
	 * @see #getResultType()
	 * @generated
	 */
	void setResultType(ResultTypeType value);

	/**
	 * Unsets the value of the '{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getResultType <em>Result Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetResultType()
	 * @see #getResultType()
	 * @see #setResultType(ResultTypeType)
	 * @generated
	 */
	void unsetResultType();

	/**
	 * Returns whether the value of the '{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getResultType <em>Result Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Result Type</em>' attribute is set.
	 * @see #unsetResultType()
	 * @see #getResultType()
	 * @see #setResultType(ResultTypeType)
	 * @generated
	 */
	boolean isSetResultType();

	/**
	 * Returns the value of the '<em><b>Traverse Xlink Depth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 *                      See definition of wfs:GetFeatureType.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Traverse Xlink Depth</em>' attribute.
	 * @see #setTraverseXlinkDepth(String)
	 * @see net.opengis.wfs.v1_1_0.WFSPackage#getGetFeatureWithLockType_TraverseXlinkDepth()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='traverseXlinkDepth'"
	 * @generated
	 */
	String getTraverseXlinkDepth();

	/**
	 * Sets the value of the '{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getTraverseXlinkDepth <em>Traverse Xlink Depth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Traverse Xlink Depth</em>' attribute.
	 * @see #getTraverseXlinkDepth()
	 * @generated
	 */
	void setTraverseXlinkDepth(String value);

	/**
	 * Returns the value of the '<em><b>Traverse Xlink Expiry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * 
	 *                      See definition of wfs:GetFeatureType.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Traverse Xlink Expiry</em>' attribute.
	 * @see #setTraverseXlinkExpiry(BigInteger)
	 * @see net.opengis.wfs.v1_1_0.WFSPackage#getGetFeatureWithLockType_TraverseXlinkExpiry()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.xml.type.PositiveInteger"
	 *        extendedMetaData="kind='attribute' name='traverseXlinkExpiry'"
	 * @generated
	 */
	BigInteger getTraverseXlinkExpiry();

	/**
	 * Sets the value of the '{@link net.opengis.wfs.v1_1_0.GetFeatureWithLockType#getTraverseXlinkExpiry <em>Traverse Xlink Expiry</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Traverse Xlink Expiry</em>' attribute.
	 * @see #getTraverseXlinkExpiry()
	 * @generated
	 */
	void setTraverseXlinkExpiry(BigInteger value);

} // GetFeatureWithLockType

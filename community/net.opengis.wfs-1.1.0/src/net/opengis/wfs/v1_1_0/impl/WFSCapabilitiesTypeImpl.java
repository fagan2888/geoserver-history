/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.opengis.wfs.v1_1_0.impl;

import net.opengis.ows.v1_0_0.OperationsMetadataType;
import net.opengis.ows.v1_0_0.ServiceIdentificationType;
import net.opengis.ows.v1_0_0.ServiceProviderType;

import net.opengis.ows.v1_0_0.impl.CapabilitiesBaseTypeImpl;

import net.opengis.wfs.v1_1_0.FeatureTypeListType;
import net.opengis.wfs.v1_1_0.GMLObjectTypeListType;
import net.opengis.wfs.v1_1_0.WFSCapabilitiesType;
import net.opengis.wfs.v1_1_0.WFSPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Capabilities Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.opengis.wfs.v1_1_0.impl.WFSCapabilitiesTypeImpl#getFeatureTypeList <em>Feature Type List</em>}</li>
 *   <li>{@link net.opengis.wfs.v1_1_0.impl.WFSCapabilitiesTypeImpl#getServesGMLObjectTypeList <em>Serves GML Object Type List</em>}</li>
 *   <li>{@link net.opengis.wfs.v1_1_0.impl.WFSCapabilitiesTypeImpl#getSupportsGMLObjectTypeList <em>Supports GML Object Type List</em>}</li>
 *   <li>{@link net.opengis.wfs.v1_1_0.impl.WFSCapabilitiesTypeImpl#getFilterCapabilities <em>Filter Capabilities</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WFSCapabilitiesTypeImpl extends CapabilitiesBaseTypeImpl implements WFSCapabilitiesType {
	/**
	 * The cached value of the '{@link #getFeatureTypeList() <em>Feature Type List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureTypeList()
	 * @generated
	 * @ordered
	 */
	protected FeatureTypeListType featureTypeList = null;

	/**
	 * The cached value of the '{@link #getServesGMLObjectTypeList() <em>Serves GML Object Type List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServesGMLObjectTypeList()
	 * @generated
	 * @ordered
	 */
	protected GMLObjectTypeListType servesGMLObjectTypeList = null;

	/**
	 * The cached value of the '{@link #getSupportsGMLObjectTypeList() <em>Supports GML Object Type List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSupportsGMLObjectTypeList()
	 * @generated
	 * @ordered
	 */
	protected GMLObjectTypeListType supportsGMLObjectTypeList = null;

	/**
	 * The default value of the '{@link #getFilterCapabilities() <em>Filter Capabilities</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilterCapabilities()
	 * @generated
	 * @ordered
	 */
	protected static final Object FILTER_CAPABILITIES_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFilterCapabilities() <em>Filter Capabilities</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilterCapabilities()
	 * @generated
	 * @ordered
	 */
	protected Object filterCapabilities = FILTER_CAPABILITIES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WFSCapabilitiesTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return WFSPackage.eINSTANCE.getWFSCapabilitiesType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureTypeListType getFeatureTypeList() {
		return featureTypeList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFeatureTypeList(FeatureTypeListType newFeatureTypeList, NotificationChain msgs) {
		FeatureTypeListType oldFeatureTypeList = featureTypeList;
		featureTypeList = newFeatureTypeList;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WFSPackage.WFS_CAPABILITIES_TYPE__FEATURE_TYPE_LIST, oldFeatureTypeList, newFeatureTypeList);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureTypeList(FeatureTypeListType newFeatureTypeList) {
		if (newFeatureTypeList != featureTypeList) {
			NotificationChain msgs = null;
			if (featureTypeList != null)
				msgs = ((InternalEObject)featureTypeList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WFSPackage.WFS_CAPABILITIES_TYPE__FEATURE_TYPE_LIST, null, msgs);
			if (newFeatureTypeList != null)
				msgs = ((InternalEObject)newFeatureTypeList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WFSPackage.WFS_CAPABILITIES_TYPE__FEATURE_TYPE_LIST, null, msgs);
			msgs = basicSetFeatureTypeList(newFeatureTypeList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WFSPackage.WFS_CAPABILITIES_TYPE__FEATURE_TYPE_LIST, newFeatureTypeList, newFeatureTypeList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GMLObjectTypeListType getServesGMLObjectTypeList() {
		return servesGMLObjectTypeList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetServesGMLObjectTypeList(GMLObjectTypeListType newServesGMLObjectTypeList, NotificationChain msgs) {
		GMLObjectTypeListType oldServesGMLObjectTypeList = servesGMLObjectTypeList;
		servesGMLObjectTypeList = newServesGMLObjectTypeList;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WFSPackage.WFS_CAPABILITIES_TYPE__SERVES_GML_OBJECT_TYPE_LIST, oldServesGMLObjectTypeList, newServesGMLObjectTypeList);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServesGMLObjectTypeList(GMLObjectTypeListType newServesGMLObjectTypeList) {
		if (newServesGMLObjectTypeList != servesGMLObjectTypeList) {
			NotificationChain msgs = null;
			if (servesGMLObjectTypeList != null)
				msgs = ((InternalEObject)servesGMLObjectTypeList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WFSPackage.WFS_CAPABILITIES_TYPE__SERVES_GML_OBJECT_TYPE_LIST, null, msgs);
			if (newServesGMLObjectTypeList != null)
				msgs = ((InternalEObject)newServesGMLObjectTypeList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WFSPackage.WFS_CAPABILITIES_TYPE__SERVES_GML_OBJECT_TYPE_LIST, null, msgs);
			msgs = basicSetServesGMLObjectTypeList(newServesGMLObjectTypeList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WFSPackage.WFS_CAPABILITIES_TYPE__SERVES_GML_OBJECT_TYPE_LIST, newServesGMLObjectTypeList, newServesGMLObjectTypeList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GMLObjectTypeListType getSupportsGMLObjectTypeList() {
		return supportsGMLObjectTypeList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSupportsGMLObjectTypeList(GMLObjectTypeListType newSupportsGMLObjectTypeList, NotificationChain msgs) {
		GMLObjectTypeListType oldSupportsGMLObjectTypeList = supportsGMLObjectTypeList;
		supportsGMLObjectTypeList = newSupportsGMLObjectTypeList;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WFSPackage.WFS_CAPABILITIES_TYPE__SUPPORTS_GML_OBJECT_TYPE_LIST, oldSupportsGMLObjectTypeList, newSupportsGMLObjectTypeList);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSupportsGMLObjectTypeList(GMLObjectTypeListType newSupportsGMLObjectTypeList) {
		if (newSupportsGMLObjectTypeList != supportsGMLObjectTypeList) {
			NotificationChain msgs = null;
			if (supportsGMLObjectTypeList != null)
				msgs = ((InternalEObject)supportsGMLObjectTypeList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WFSPackage.WFS_CAPABILITIES_TYPE__SUPPORTS_GML_OBJECT_TYPE_LIST, null, msgs);
			if (newSupportsGMLObjectTypeList != null)
				msgs = ((InternalEObject)newSupportsGMLObjectTypeList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WFSPackage.WFS_CAPABILITIES_TYPE__SUPPORTS_GML_OBJECT_TYPE_LIST, null, msgs);
			msgs = basicSetSupportsGMLObjectTypeList(newSupportsGMLObjectTypeList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WFSPackage.WFS_CAPABILITIES_TYPE__SUPPORTS_GML_OBJECT_TYPE_LIST, newSupportsGMLObjectTypeList, newSupportsGMLObjectTypeList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getFilterCapabilities() {
		return filterCapabilities;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFilterCapabilities(Object newFilterCapabilities) {
		Object oldFilterCapabilities = filterCapabilities;
		filterCapabilities = newFilterCapabilities;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WFSPackage.WFS_CAPABILITIES_TYPE__FILTER_CAPABILITIES, oldFilterCapabilities, filterCapabilities));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case WFSPackage.WFS_CAPABILITIES_TYPE__SERVICE_IDENTIFICATION:
					return basicSetServiceIdentification(null, msgs);
				case WFSPackage.WFS_CAPABILITIES_TYPE__SERVICE_PROVIDER:
					return basicSetServiceProvider(null, msgs);
				case WFSPackage.WFS_CAPABILITIES_TYPE__OPERATIONS_METADATA:
					return basicSetOperationsMetadata(null, msgs);
				case WFSPackage.WFS_CAPABILITIES_TYPE__FEATURE_TYPE_LIST:
					return basicSetFeatureTypeList(null, msgs);
				case WFSPackage.WFS_CAPABILITIES_TYPE__SERVES_GML_OBJECT_TYPE_LIST:
					return basicSetServesGMLObjectTypeList(null, msgs);
				case WFSPackage.WFS_CAPABILITIES_TYPE__SUPPORTS_GML_OBJECT_TYPE_LIST:
					return basicSetSupportsGMLObjectTypeList(null, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case WFSPackage.WFS_CAPABILITIES_TYPE__SERVICE_IDENTIFICATION:
				return getServiceIdentification();
			case WFSPackage.WFS_CAPABILITIES_TYPE__SERVICE_PROVIDER:
				return getServiceProvider();
			case WFSPackage.WFS_CAPABILITIES_TYPE__OPERATIONS_METADATA:
				return getOperationsMetadata();
			case WFSPackage.WFS_CAPABILITIES_TYPE__UPDATE_SEQUENCE:
				return getUpdateSequence();
			case WFSPackage.WFS_CAPABILITIES_TYPE__VERSION:
				return getVersion();
			case WFSPackage.WFS_CAPABILITIES_TYPE__FEATURE_TYPE_LIST:
				return getFeatureTypeList();
			case WFSPackage.WFS_CAPABILITIES_TYPE__SERVES_GML_OBJECT_TYPE_LIST:
				return getServesGMLObjectTypeList();
			case WFSPackage.WFS_CAPABILITIES_TYPE__SUPPORTS_GML_OBJECT_TYPE_LIST:
				return getSupportsGMLObjectTypeList();
			case WFSPackage.WFS_CAPABILITIES_TYPE__FILTER_CAPABILITIES:
				return getFilterCapabilities();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case WFSPackage.WFS_CAPABILITIES_TYPE__SERVICE_IDENTIFICATION:
				setServiceIdentification((ServiceIdentificationType)newValue);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__SERVICE_PROVIDER:
				setServiceProvider((ServiceProviderType)newValue);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__OPERATIONS_METADATA:
				setOperationsMetadata((OperationsMetadataType)newValue);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__UPDATE_SEQUENCE:
				setUpdateSequence((String)newValue);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__VERSION:
				setVersion((String)newValue);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__FEATURE_TYPE_LIST:
				setFeatureTypeList((FeatureTypeListType)newValue);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__SERVES_GML_OBJECT_TYPE_LIST:
				setServesGMLObjectTypeList((GMLObjectTypeListType)newValue);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__SUPPORTS_GML_OBJECT_TYPE_LIST:
				setSupportsGMLObjectTypeList((GMLObjectTypeListType)newValue);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__FILTER_CAPABILITIES:
				setFilterCapabilities((Object)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case WFSPackage.WFS_CAPABILITIES_TYPE__SERVICE_IDENTIFICATION:
				setServiceIdentification((ServiceIdentificationType)null);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__SERVICE_PROVIDER:
				setServiceProvider((ServiceProviderType)null);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__OPERATIONS_METADATA:
				setOperationsMetadata((OperationsMetadataType)null);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__UPDATE_SEQUENCE:
				setUpdateSequence(UPDATE_SEQUENCE_EDEFAULT);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__FEATURE_TYPE_LIST:
				setFeatureTypeList((FeatureTypeListType)null);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__SERVES_GML_OBJECT_TYPE_LIST:
				setServesGMLObjectTypeList((GMLObjectTypeListType)null);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__SUPPORTS_GML_OBJECT_TYPE_LIST:
				setSupportsGMLObjectTypeList((GMLObjectTypeListType)null);
				return;
			case WFSPackage.WFS_CAPABILITIES_TYPE__FILTER_CAPABILITIES:
				setFilterCapabilities(FILTER_CAPABILITIES_EDEFAULT);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case WFSPackage.WFS_CAPABILITIES_TYPE__SERVICE_IDENTIFICATION:
				return serviceIdentification != null;
			case WFSPackage.WFS_CAPABILITIES_TYPE__SERVICE_PROVIDER:
				return serviceProvider != null;
			case WFSPackage.WFS_CAPABILITIES_TYPE__OPERATIONS_METADATA:
				return operationsMetadata != null;
			case WFSPackage.WFS_CAPABILITIES_TYPE__UPDATE_SEQUENCE:
				return UPDATE_SEQUENCE_EDEFAULT == null ? updateSequence != null : !UPDATE_SEQUENCE_EDEFAULT.equals(updateSequence);
			case WFSPackage.WFS_CAPABILITIES_TYPE__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case WFSPackage.WFS_CAPABILITIES_TYPE__FEATURE_TYPE_LIST:
				return featureTypeList != null;
			case WFSPackage.WFS_CAPABILITIES_TYPE__SERVES_GML_OBJECT_TYPE_LIST:
				return servesGMLObjectTypeList != null;
			case WFSPackage.WFS_CAPABILITIES_TYPE__SUPPORTS_GML_OBJECT_TYPE_LIST:
				return supportsGMLObjectTypeList != null;
			case WFSPackage.WFS_CAPABILITIES_TYPE__FILTER_CAPABILITIES:
				return FILTER_CAPABILITIES_EDEFAULT == null ? filterCapabilities != null : !FILTER_CAPABILITIES_EDEFAULT.equals(filterCapabilities);
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (filterCapabilities: ");
		result.append(filterCapabilities);
		result.append(')');
		return result.toString();
	}

} //WFSCapabilitiesTypeImpl

/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.opengis.ows.v1_0_0.impl;

import net.opengis.ows.v1_0_0.CodeType;
import net.opengis.ows.v1_0_0.ContactType;
import net.opengis.ows.v1_0_0.OWSPackage;
import net.opengis.ows.v1_0_0.ResponsiblePartySubsetType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Responsible Party Subset Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.opengis.ows.v1_0_0.impl.ResponsiblePartySubsetTypeImpl#getIndividualName <em>Individual Name</em>}</li>
 *   <li>{@link net.opengis.ows.v1_0_0.impl.ResponsiblePartySubsetTypeImpl#getPositionName <em>Position Name</em>}</li>
 *   <li>{@link net.opengis.ows.v1_0_0.impl.ResponsiblePartySubsetTypeImpl#getContactInfo <em>Contact Info</em>}</li>
 *   <li>{@link net.opengis.ows.v1_0_0.impl.ResponsiblePartySubsetTypeImpl#getRole <em>Role</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResponsiblePartySubsetTypeImpl extends EObjectImpl implements ResponsiblePartySubsetType {
	/**
	 * The default value of the '{@link #getIndividualName() <em>Individual Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndividualName()
	 * @generated
	 * @ordered
	 */
	protected static final String INDIVIDUAL_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIndividualName() <em>Individual Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndividualName()
	 * @generated
	 * @ordered
	 */
	protected String individualName = INDIVIDUAL_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPositionName() <em>Position Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPositionName()
	 * @generated
	 * @ordered
	 */
	protected static final String POSITION_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPositionName() <em>Position Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPositionName()
	 * @generated
	 * @ordered
	 */
	protected String positionName = POSITION_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getContactInfo() <em>Contact Info</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContactInfo()
	 * @generated
	 * @ordered
	 */
	protected ContactType contactInfo = null;

	/**
	 * The cached value of the '{@link #getRole() <em>Role</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected CodeType role = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResponsiblePartySubsetTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return OWSPackage.eINSTANCE.getResponsiblePartySubsetType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIndividualName() {
		return individualName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndividualName(String newIndividualName) {
		String oldIndividualName = individualName;
		individualName = newIndividualName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__INDIVIDUAL_NAME, oldIndividualName, individualName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPositionName(String newPositionName) {
		String oldPositionName = positionName;
		positionName = newPositionName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__POSITION_NAME, oldPositionName, positionName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContactType getContactInfo() {
		return contactInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContactInfo(ContactType newContactInfo, NotificationChain msgs) {
		ContactType oldContactInfo = contactInfo;
		contactInfo = newContactInfo;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__CONTACT_INFO, oldContactInfo, newContactInfo);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContactInfo(ContactType newContactInfo) {
		if (newContactInfo != contactInfo) {
			NotificationChain msgs = null;
			if (contactInfo != null)
				msgs = ((InternalEObject)contactInfo).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__CONTACT_INFO, null, msgs);
			if (newContactInfo != null)
				msgs = ((InternalEObject)newContactInfo).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__CONTACT_INFO, null, msgs);
			msgs = basicSetContactInfo(newContactInfo, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__CONTACT_INFO, newContactInfo, newContactInfo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CodeType getRole() {
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRole(CodeType newRole, NotificationChain msgs) {
		CodeType oldRole = role;
		role = newRole;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__ROLE, oldRole, newRole);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRole(CodeType newRole) {
		if (newRole != role) {
			NotificationChain msgs = null;
			if (role != null)
				msgs = ((InternalEObject)role).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__ROLE, null, msgs);
			if (newRole != null)
				msgs = ((InternalEObject)newRole).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__ROLE, null, msgs);
			msgs = basicSetRole(newRole, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__ROLE, newRole, newRole));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__CONTACT_INFO:
					return basicSetContactInfo(null, msgs);
				case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__ROLE:
					return basicSetRole(null, msgs);
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
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__INDIVIDUAL_NAME:
				return getIndividualName();
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__POSITION_NAME:
				return getPositionName();
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__CONTACT_INFO:
				return getContactInfo();
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__ROLE:
				return getRole();
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
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__INDIVIDUAL_NAME:
				setIndividualName((String)newValue);
				return;
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__POSITION_NAME:
				setPositionName((String)newValue);
				return;
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__CONTACT_INFO:
				setContactInfo((ContactType)newValue);
				return;
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__ROLE:
				setRole((CodeType)newValue);
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
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__INDIVIDUAL_NAME:
				setIndividualName(INDIVIDUAL_NAME_EDEFAULT);
				return;
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__POSITION_NAME:
				setPositionName(POSITION_NAME_EDEFAULT);
				return;
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__CONTACT_INFO:
				setContactInfo((ContactType)null);
				return;
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__ROLE:
				setRole((CodeType)null);
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
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__INDIVIDUAL_NAME:
				return INDIVIDUAL_NAME_EDEFAULT == null ? individualName != null : !INDIVIDUAL_NAME_EDEFAULT.equals(individualName);
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__POSITION_NAME:
				return POSITION_NAME_EDEFAULT == null ? positionName != null : !POSITION_NAME_EDEFAULT.equals(positionName);
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__CONTACT_INFO:
				return contactInfo != null;
			case OWSPackage.RESPONSIBLE_PARTY_SUBSET_TYPE__ROLE:
				return role != null;
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
		result.append(" (individualName: ");
		result.append(individualName);
		result.append(", positionName: ");
		result.append(positionName);
		result.append(')');
		return result.toString();
	}

} //ResponsiblePartySubsetTypeImpl

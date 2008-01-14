/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.opengis.ows.impl;

import java.util.Collection;

import net.opengis.ows.CodeType;
import net.opengis.ows.IdentificationType;
import net.opengis.ows.MetadataType;
import net.opengis.ows.OwsPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Identification Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.opengis.ows.impl.IdentificationTypeImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link net.opengis.ows.impl.IdentificationTypeImpl#getBoundingBoxGroup <em>Bounding Box Group</em>}</li>
 *   <li>{@link net.opengis.ows.impl.IdentificationTypeImpl#getBoundingBox <em>Bounding Box</em>}</li>
 *   <li>{@link net.opengis.ows.impl.IdentificationTypeImpl#getOutputFormat <em>Output Format</em>}</li>
 *   <li>{@link net.opengis.ows.impl.IdentificationTypeImpl#getAvailableCRSGroup <em>Available CRS Group</em>}</li>
 *   <li>{@link net.opengis.ows.impl.IdentificationTypeImpl#getAvailableCRS <em>Available CRS</em>}</li>
 *   <li>{@link net.opengis.ows.impl.IdentificationTypeImpl#getMetadata <em>Metadata</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IdentificationTypeImpl extends DescriptionTypeImpl implements IdentificationType {
    /**
     * The cached value of the '{@link #getIdentifier() <em>Identifier</em>}' containment reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getIdentifier()
     * @generated
     * @ordered
     */
	protected CodeType identifier= null;

    /**
     * The cached value of the '{@link #getBoundingBoxGroup() <em>Bounding Box Group</em>}' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getBoundingBoxGroup()
     * @generated
     * @ordered
     */
	protected FeatureMap boundingBoxGroup= null;

    /**
     * The default value of the '{@link #getOutputFormat() <em>Output Format</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getOutputFormat()
     * @generated
     * @ordered
     */
	protected static final String OUTPUT_FORMAT_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOutputFormat() <em>Output Format</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getOutputFormat()
     * @generated
     * @ordered
     */
	protected String outputFormat = OUTPUT_FORMAT_EDEFAULT;

    /**
     * The cached value of the '{@link #getAvailableCRSGroup() <em>Available CRS Group</em>}' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getAvailableCRSGroup()
     * @generated
     * @ordered
     */
	protected FeatureMap availableCRSGroup= null;

    /**
     * The default value of the '{@link #getAvailableCRS() <em>Available CRS</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getAvailableCRS()
     * @generated
     * @ordered
     */
	protected static final String AVAILABLE_CRS_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMetadata() <em>Metadata</em>}' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getMetadata()
     * @generated
     * @ordered
     */
	protected EList metadata= null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected IdentificationTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected EClass eStaticClass() {
        return OwsPackage.Literals.IDENTIFICATION_TYPE;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public CodeType getIdentifier() {
        return identifier;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public NotificationChain basicSetIdentifier(CodeType newIdentifier, NotificationChain msgs) {
        CodeType oldIdentifier = identifier;
        identifier = newIdentifier;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OwsPackage.IDENTIFICATION_TYPE__IDENTIFIER, oldIdentifier, newIdentifier);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setIdentifier(CodeType newIdentifier) {
        if (newIdentifier != identifier) {
            NotificationChain msgs = null;
            if (identifier != null)
                msgs = ((InternalEObject)identifier).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OwsPackage.IDENTIFICATION_TYPE__IDENTIFIER, null, msgs);
            if (newIdentifier != null)
                msgs = ((InternalEObject)newIdentifier).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OwsPackage.IDENTIFICATION_TYPE__IDENTIFIER, null, msgs);
            msgs = basicSetIdentifier(newIdentifier, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OwsPackage.IDENTIFICATION_TYPE__IDENTIFIER, newIdentifier, newIdentifier));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public FeatureMap getBoundingBoxGroup() {
        if (boundingBoxGroup == null) {
            boundingBoxGroup = new BasicFeatureMap(this, OwsPackage.IDENTIFICATION_TYPE__BOUNDING_BOX_GROUP);
        }
        return boundingBoxGroup;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EList getBoundingBox() {
        return ((FeatureMap)getBoundingBoxGroup()).list(OwsPackage.Literals.IDENTIFICATION_TYPE__BOUNDING_BOX);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getOutputFormat() {
        return outputFormat;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setOutputFormat(String newOutputFormat) {
        String oldOutputFormat = outputFormat;
        outputFormat = newOutputFormat;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OwsPackage.IDENTIFICATION_TYPE__OUTPUT_FORMAT, oldOutputFormat, outputFormat));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public FeatureMap getAvailableCRSGroup() {
        if (availableCRSGroup == null) {
            availableCRSGroup = new BasicFeatureMap(this, OwsPackage.IDENTIFICATION_TYPE__AVAILABLE_CRS_GROUP);
        }
        return availableCRSGroup;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String getAvailableCRS() {
        return (String)getAvailableCRSGroup().get(OwsPackage.Literals.IDENTIFICATION_TYPE__AVAILABLE_CRS, true);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setAvailableCRS(String newAvailableCRS) {
        ((FeatureMap.Internal)getAvailableCRSGroup()).set(OwsPackage.Literals.IDENTIFICATION_TYPE__AVAILABLE_CRS, newAvailableCRS);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EList getMetadata() {
        if (metadata == null) {
            metadata = new EObjectContainmentEList(MetadataType.class, this, OwsPackage.IDENTIFICATION_TYPE__METADATA);
        }
        return metadata;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case OwsPackage.IDENTIFICATION_TYPE__IDENTIFIER:
                return basicSetIdentifier(null, msgs);
            case OwsPackage.IDENTIFICATION_TYPE__BOUNDING_BOX_GROUP:
                return ((InternalEList)getBoundingBoxGroup()).basicRemove(otherEnd, msgs);
            case OwsPackage.IDENTIFICATION_TYPE__BOUNDING_BOX:
                return ((InternalEList)getBoundingBox()).basicRemove(otherEnd, msgs);
            case OwsPackage.IDENTIFICATION_TYPE__AVAILABLE_CRS_GROUP:
                return ((InternalEList)getAvailableCRSGroup()).basicRemove(otherEnd, msgs);
            case OwsPackage.IDENTIFICATION_TYPE__METADATA:
                return ((InternalEList)getMetadata()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OwsPackage.IDENTIFICATION_TYPE__IDENTIFIER:
                return getIdentifier();
            case OwsPackage.IDENTIFICATION_TYPE__BOUNDING_BOX_GROUP:
                if (coreType) return getBoundingBoxGroup();
                return ((FeatureMap.Internal)getBoundingBoxGroup()).getWrapper();
            case OwsPackage.IDENTIFICATION_TYPE__BOUNDING_BOX:
                return getBoundingBox();
            case OwsPackage.IDENTIFICATION_TYPE__OUTPUT_FORMAT:
                return getOutputFormat();
            case OwsPackage.IDENTIFICATION_TYPE__AVAILABLE_CRS_GROUP:
                if (coreType) return getAvailableCRSGroup();
                return ((FeatureMap.Internal)getAvailableCRSGroup()).getWrapper();
            case OwsPackage.IDENTIFICATION_TYPE__AVAILABLE_CRS:
                return getAvailableCRS();
            case OwsPackage.IDENTIFICATION_TYPE__METADATA:
                return getMetadata();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case OwsPackage.IDENTIFICATION_TYPE__IDENTIFIER:
                setIdentifier((CodeType)newValue);
                return;
            case OwsPackage.IDENTIFICATION_TYPE__BOUNDING_BOX_GROUP:
                ((FeatureMap.Internal)getBoundingBoxGroup()).set(newValue);
                return;
            case OwsPackage.IDENTIFICATION_TYPE__BOUNDING_BOX:
                getBoundingBox().clear();
                getBoundingBox().addAll((Collection)newValue);
                return;
            case OwsPackage.IDENTIFICATION_TYPE__OUTPUT_FORMAT:
                setOutputFormat((String)newValue);
                return;
            case OwsPackage.IDENTIFICATION_TYPE__AVAILABLE_CRS_GROUP:
                ((FeatureMap.Internal)getAvailableCRSGroup()).set(newValue);
                return;
            case OwsPackage.IDENTIFICATION_TYPE__AVAILABLE_CRS:
                setAvailableCRS((String)newValue);
                return;
            case OwsPackage.IDENTIFICATION_TYPE__METADATA:
                getMetadata().clear();
                getMetadata().addAll((Collection)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void eUnset(int featureID) {
        switch (featureID) {
            case OwsPackage.IDENTIFICATION_TYPE__IDENTIFIER:
                setIdentifier((CodeType)null);
                return;
            case OwsPackage.IDENTIFICATION_TYPE__BOUNDING_BOX_GROUP:
                getBoundingBoxGroup().clear();
                return;
            case OwsPackage.IDENTIFICATION_TYPE__BOUNDING_BOX:
                getBoundingBox().clear();
                return;
            case OwsPackage.IDENTIFICATION_TYPE__OUTPUT_FORMAT:
                setOutputFormat(OUTPUT_FORMAT_EDEFAULT);
                return;
            case OwsPackage.IDENTIFICATION_TYPE__AVAILABLE_CRS_GROUP:
                getAvailableCRSGroup().clear();
                return;
            case OwsPackage.IDENTIFICATION_TYPE__AVAILABLE_CRS:
                setAvailableCRS(AVAILABLE_CRS_EDEFAULT);
                return;
            case OwsPackage.IDENTIFICATION_TYPE__METADATA:
                getMetadata().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public boolean eIsSet(int featureID) {
        switch (featureID) {
            case OwsPackage.IDENTIFICATION_TYPE__IDENTIFIER:
                return identifier != null;
            case OwsPackage.IDENTIFICATION_TYPE__BOUNDING_BOX_GROUP:
                return boundingBoxGroup != null && !boundingBoxGroup.isEmpty();
            case OwsPackage.IDENTIFICATION_TYPE__BOUNDING_BOX:
                return !getBoundingBox().isEmpty();
            case OwsPackage.IDENTIFICATION_TYPE__OUTPUT_FORMAT:
                return OUTPUT_FORMAT_EDEFAULT == null ? outputFormat != null : !OUTPUT_FORMAT_EDEFAULT.equals(outputFormat);
            case OwsPackage.IDENTIFICATION_TYPE__AVAILABLE_CRS_GROUP:
                return availableCRSGroup != null && !availableCRSGroup.isEmpty();
            case OwsPackage.IDENTIFICATION_TYPE__AVAILABLE_CRS:
                return AVAILABLE_CRS_EDEFAULT == null ? getAvailableCRS() != null : !AVAILABLE_CRS_EDEFAULT.equals(getAvailableCRS());
            case OwsPackage.IDENTIFICATION_TYPE__METADATA:
                return metadata != null && !metadata.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (boundingBoxGroup: ");
        result.append(boundingBoxGroup);
        result.append(", outputFormat: ");
        result.append(outputFormat);
        result.append(", availableCRSGroup: ");
        result.append(availableCRSGroup);
        result.append(')');
        return result.toString();
    }

} //IdentificationTypeImpl
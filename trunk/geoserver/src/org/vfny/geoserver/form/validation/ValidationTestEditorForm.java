/*
 * Created on Jan 22, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.vfny.geoserver.form.validation;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.vfny.geoserver.config.validation.ArgumentConfig;
import org.vfny.geoserver.config.validation.PlugInConfig;
import org.vfny.geoserver.config.validation.TestConfig;
import org.vfny.geoserver.config.validation.ValidationConfig;

/**
 * ValidationTestEditorForm purpose.
 * <p>
 * Description of ValidationTestEditorForm ...
 * </p>
 * 
 * @author rgould, Refractions Research, Inc.
 * @author $Author: emperorkefka $ (last modification)
 * @version $Id: ValidationTestEditorForm.java,v 1.5 2004/04/21 21:30:51 emperorkefka Exp $
 */
public class ValidationTestEditorForm extends ActionForm {

	/**
	 * 
	 * @uml.property name="name" multiplicity="(0 1)"
	 */
	private String name;

	/**
	 * 
	 * @uml.property name="description" multiplicity="(0 1)"
	 */
	private String description;

	/**
	 * 
	 * @uml.property name="plugInName" multiplicity="(0 1)"
	 */
	private String plugInName;

	/**
	 * 
	 * @uml.property name="request"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private HttpServletRequest request;

	/**
	 * 
	 * @uml.property name="attributeKeys"
	 * @uml.associationEnd elementType="java.lang.String" multiplicity="(0 -1)"
	 */
	private List attributeKeys;

	/**
	 * 
	 * @uml.property name="attributeHelps" multiplicity="(0 1)"
	 */
	private List attributeHelps;

	/**
	 * 
	 * @uml.property name="attributeValues"
	 * @uml.associationEnd elementType="java.lang.String" multiplicity="(0 -1)"
	 */
	private List attributeValues;
    
    
    public void reset(ActionMapping arg0, HttpServletRequest request) {
        super.reset(arg0, request);
        this.request = request;
        
        TestConfig testConfig = (TestConfig) request.getSession().getAttribute(TestConfig.CURRENTLY_SELECTED_KEY);
        
        name = testConfig.getName();
        description = testConfig.getDescription();
        plugInName = testConfig.getPlugIn().getName();
        
        attributeKeys = new ArrayList();
        attributeHelps = new ArrayList();
        attributeValues = new ArrayList();
        ArgumentConfig.loadPropertyLists(testConfig,request.getLocale(),attributeKeys,attributeHelps,attributeValues);
    }
    
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        
        final TestConfig victim = (TestConfig) request.getSession().getAttribute(TestConfig.CURRENTLY_SELECTED_KEY);
        // Only used to validate values!
        
        ActionErrors errors = new ActionErrors();
        for(int i=0; i<attributeKeys.size();i++){
            String key = (String) attributeKeys.get( i );
            String text = (String) attributeValues.get( i );
            PropertyDescriptor property = victim.getPropertyDescriptor(key);
            
            if( text == null || text.length() == 0){
                if( property.isPreferred() ){
                    errors.add( key, new ActionError( "validation.test.property.required", key ));
                }                
                else {
                    // assume null is ok 
                }                                
            }
            else {
                try {
                    Object value = victim.createArg( key, text );
                    if( value == null && property.isPreferred() ){
                        errors.add( key, new ActionError( "validation.test.property.required", key ));                        
                    }                    
                }
                catch (Throwable t) {
                    errors.add( key, new ActionError( "validation.test.property.invalid", key, t ));
                }                
            }
        }
        return errors;
    }   
    /**
     * Translate text representation of arguments to real values.
     * <p>
     * Victim is required for access to BeanInfo
     * </p>
     */
    public Map toArgumentMap( TestConfig victim ) throws Exception{
        Map map = new HashMap();
        for(int i=0; i<attributeKeys.size();i++){
            String key = (String) attributeKeys.get( i );
            String text = (String) attributeValues.get( i );
            PropertyDescriptor property = victim.getPropertyDescriptor(name);
            
            if( text == null || text.length() == 0){
                if( property.isPreferred() ){
                    throw new IllegalArgumentException(
                        "Required non empty value for "+key 
                    );
                }     
            }
            Object value = victim.createArg( key, text );
            if( value == null ){
                if( property.isPreferred() ){
                    throw new IllegalArgumentException(
                        "Required non empty value for "+key 
                    );
                }
            }
            else {
                map.put( key, value );
            }
        }
        return map;
    }

	/**
	 * List of attribtue keys as text.
	 * <p>
	 * These keys are really the propertyName associated with a BeanInfo
	 * </p>
	 * 
	 * @uml.property name="attributeKeys"
	 */
	public List getAttributeKeys() {
		return attributeKeys;
	}

	/**
	 * List of attribtue vales as text.
	 * <p>
	 * To convert this value to a real java object you will need to use
	 * a BeanInfo Property descriptor.
	 * </p>
	 * 
	 * @uml.property name="attributeValues"
	 */
	public List getAttributeValues() {
		return attributeValues;
	}

    /** Help text gernated from PropertyDescriptor.getShortDescription() */
    public String[] getAttributeHelps() {
    	return (String[]) attributeHelps.toArray(new String[attributeHelps.size()]);
    }
    public String getAttributeKey(int index) {
    	return (String) attributeKeys.get(index);
    }

	/**
	 * 
	 * @uml.property name="attributeValues"
	 */
	public void setAttributeValues(List list) {
		attributeValues = list;
	}

	/**
	 * 
	 * @uml.property name="attributeKeys"
	 */
	public void setAttributeKeys(List list) {
		attributeKeys = list;
	}

	/**
	 * 
	 * @uml.property name="attributeHelps"
	 */
	public void setAttributeHelps(List list) {
		attributeHelps = list;
	}

	/**
	 * Access description property.
	 * 
	 * @return Returns the description.
	 * 
	 * @uml.property name="description"
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description to description.
	 * 
	 * @param description The description to set.
	 * 
	 * @uml.property name="description"
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Access name property.
	 * 
	 * @return Returns the name.
	 * 
	 * @uml.property name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name to name.
	 * 
	 * @param name The name to set.
	 * 
	 * @uml.property name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Access plugInName property.
	 * 
	 * @return Returns the plugInName.
	 * 
	 * @uml.property name="plugInName"
	 */
	public String getPlugInName() {
		return plugInName;
	}

    
    public String getPlugInDescription() {
        ValidationConfig validationConfig = (ValidationConfig) this.getServlet().getServletContext().getAttribute(ValidationConfig.CONFIG_KEY);
        PlugInConfig config = validationConfig.getPlugIn(plugInName);
        return config.getDescription();
    }

}

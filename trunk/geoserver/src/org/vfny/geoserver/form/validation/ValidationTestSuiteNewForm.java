/*
 * Created on Jan 23, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.vfny.geoserver.form.validation;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * ValidationTestSuiteNewForm purpose.
 * <p>
 * Description of ValidationTestSuiteNewForm ...
 * </p>
 * 
 * <p>
 * Capabilities:
 * </p>
 * <ul>
 * <li>
 * Feature: description
 * </li>
 * </ul>
 * <p>
 * Example Use:
 * </p>
 * <pre><code>
 * ValidationTestSuiteNewForm x = new ValidationTestSuiteNewForm(...);
 * </code></pre>
 * 
 * @author User, Refractions Research, Inc.
 * @author $Author: jive $ (last modification)
 * @version $Id: ValidationTestSuiteNewForm.java,v 1.1 2004/01/31 00:27:28 jive Exp $
 */
public class ValidationTestSuiteNewForm extends ActionForm {

	/**
	 * 
	 * @uml.property name="newName" multiplicity="(0 1)"
	 */
	private String newName;

    
    public void reset(ActionMapping arg0, HttpServletRequest request) {
        super.reset(arg0, request);
        
        newName = "";
    }
    
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        return errors;
    }

	/**
	 * Access newName property.
	 * 
	 * @return Returns the newName.
	 * 
	 * @uml.property name="newName"
	 */
	public String getNewName() {
		return newName;
	}

	/**
	 * Set newName to newName.
	 * 
	 * @param newName The newName to set.
	 * 
	 * @uml.property name="newName"
	 */
	public void setNewName(String newName) {
		this.newName = newName;
	}

}

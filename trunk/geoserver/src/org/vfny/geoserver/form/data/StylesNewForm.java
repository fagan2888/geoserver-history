/* Copyright (c) 2001, 2003 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.form.data;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Gather enough information to reate a new Style for editing.
 * 
 * @author jgarnett, Refractions Research, Inc.
 * @author $Author: jive $ (last modification)
 * @version $Id: StylesNewForm.java,v 1.1 2004/02/28 07:45:00 jive Exp $
 */
public class StylesNewForm extends ActionForm {

	/**
	 * StyleID entered by user
	 * 
	 * @uml.property name="styleID" multiplicity="(0 1)"
	 */
	private String styleID;

    
    public void reset(ActionMapping arg0, HttpServletRequest request) {
        super.reset(arg0, request);
        styleID = "";
    }

    /**
     * Implementation of validate.
     * 
     * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
     * 
     * @param mapping
     * @param request
     * @return Any ActionErrors produced by validation
     */
    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if ((styleID == null) || styleID.equals("")) {
            errors.add("styleID",
            new ActionError("error.styleID.required", styleID));
        } else if (!Pattern.matches("^\\w*$", styleID)) {
            errors.add("styleID",
            new ActionError("error.styleID.invalid", styleID));
        }      
        return errors;
    }

	/**
	 * Access styleID property.
	 * 
	 * @return Returns the styleID.
	 * 
	 * @uml.property name="styleID"
	 */
	public String getStyleID() {
		return styleID;
	}

	/**
	 * Set styleID to styleID.
	 * 
	 * @param styleID The styleID to set.
	 * 
	 * @uml.property name="styleID"
	 */
	public void setStyleID(String styleID) {
		this.styleID = styleID;
	}

}
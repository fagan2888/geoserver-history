/*
 * Created on Jan 22, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.geotools.validation.attributes;

import java.beans.*;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import org.geotools.validation.*;

/**
 * GazetteerNameValidationBeanInfo purpose.
 * <p>
 * Description of GazetteerNameValidationBeanInfo ...
 * </p>
 * 
 * @author dzwiers, Refractions Research, Inc.
 * @author $Author: jive $ (last modification)
 * @version $Id: GazetteerNameValidationBeanInfo.java,v 1.1 2004/01/31 00:24:06 jive Exp $
 */
public class GazetteerNameValidationBeanInfo extends DefaultFeatureValidationBeanInfo {
	
	/**
	 * GazetteerNameValidationBeanInfo constructor.
	 * <p>
	 * Description
	 * </p>
	 * 
	 */
	public GazetteerNameValidationBeanInfo() {
		super();
	}
	
	/**
	 * Implementation of getPropertyDescriptors.
	 * 
	 * @see java.beans.BeanInfo#getPropertyDescriptors()
	 * 
	 * @return
	 */
	public PropertyDescriptor[] getPropertyDescriptors(){
			PropertyDescriptor[] pd2 = super.getPropertyDescriptors();
			ResourceBundle resourceBundle = getResourceBundle(GazetteerNameValidation.class);
			if(pd2 == null)
				pd2 = new PropertyDescriptor[0];
			PropertyDescriptor[] pd = new PropertyDescriptor[pd2.length + 2];
			int i=0;
			for(;i<pd2.length;i++)
				pd[i] = pd2[i];
			try{
				pd[i] = createPropertyDescriptor("attrName",GazetteerNameValidation.class,resourceBundle);
				pd[i].setExpert(false);
				pd[i+1] = createPropertyDescriptor("gazetteer",GazetteerNameValidation.class,resourceBundle);
				pd[i+1].setExpert(true);
			}catch(IntrospectionException e){
				pd = pd2;
				// TODO error, log here
				e.printStackTrace();
			}
		return pd;
	}
}

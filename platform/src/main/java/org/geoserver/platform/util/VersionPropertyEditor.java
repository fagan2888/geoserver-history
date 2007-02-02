package org.geoserver.platform.util;

import java.beans.PropertyEditorManager;
import java.beans.PropertyEditorSupport;

import org.geotools.util.Version;

/**
 * Property editor for the {@link Version} class.
 * <p>
 * Registering this property editor allows versions to be used in a spring 
 * context like:
 * <pre>
 * <code>
 * &lt;bean id="..." class="..."&gt;
 *    &lt;constructor-arg value="1.0.0"/&gt;
 * &lt;bean&gt;
 * </code>
 * </pre>
 * </p>
 * @author Justin Deoliveira, The Open Planning Project, jdeolive@openplans.org
 *
 */
public class VersionPropertyEditor extends PropertyEditorSupport {

	static {
		//register with property editor manager
		PropertyEditorManager.registerEditor( Version.class, VersionPropertyEditor.class );
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		setValue( new Version( text ) );
	}
	
}

package org.geoserver.web;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

/**
 * Helper class to test components that need a form around them to be tested
 * (typically custom panels with form components inside).
 * <p>
 * The panel will be placed into a form named "form", the panel itself will be
 * named "content"
 */
public class FormTestPage extends WebPage {

    private static final String PANEL = "panel";
    private static final String FORM = "form";

    public FormTestPage(ComponentBuilder builder) {
        Form form = new Form(FORM);
        form.add(builder.buildComponent(PANEL));
        add(form);
    }

    public interface ComponentBuilder extends Serializable {
        Component buildComponent(String id);
    }
}

package org.geoserver.web.wicket;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class RichEditableLabel extends Panel{
    private Form form;
    private Object backup;
    private Label label;

    public RichEditableLabel(String id, IModel model){
        super(id, model);

        setupLabel();
        setupForm();
        setOutputMarkupId(true);
    }

    public void setupLabel(){
        label = new Label("label", getModel());
        label.add(new AjaxEventBehavior("onclick"){
            protected void onEvent(AjaxRequestTarget target){
                form.setVisible(true);
                label.setVisible(false);
                backup = RichEditableLabel.this.getModel().getObject();
                target.addComponent(RichEditableLabel.this);
            }
        });
        label.setOutputMarkupId(true);
        add(label);
    }

    private void setupForm(){
        form = new Form("form");
        form.add(new TextField("input", getModel()));
        AjaxLink okay = new AjaxLink("okay"){
            public void onClick(AjaxRequestTarget target){}
        };

        okay.add(new AjaxFormSubmitBehavior(form, "onclick"){
            protected void onSubmit(AjaxRequestTarget target){
                form.setVisible(false);
                label.setVisible(true);
                target.addComponent(RichEditableLabel.this);
            }

            protected void onError(AjaxRequestTarget target){
                // do nothing for now. Do we want this to provide a visual cue about the error?
            }
        });

        form.add(okay);

        form.add(new AjaxLink("cancel"){
            public void onClick(AjaxRequestTarget target){
                form.setVisible(false);
                label.setVisible(true);
                RichEditableLabel.this.getModel().setObject(backup);
                target.addComponent(RichEditableLabel.this);
            }
        });

        form.setVisible(false);
        form.setOutputMarkupId(true);
        add(form);
    }
}

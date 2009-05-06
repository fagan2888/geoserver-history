/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.web.data.store.panel;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.geoserver.web.data.workspace.WorkspaceChoiceRenderer;
import org.geoserver.web.data.workspace.WorkspacesModel;

/**
 * A label + workspace dropdown form panel
 */
@SuppressWarnings("serial")
public class WorkspacePanel extends Panel {

    public WorkspacePanel(final String id, final IModel workspaceModel,
            final IModel paramLabelModel, final boolean required) {
        // make the value of the combo field the model of this panel, for easy
        // value retriaval
        super(id, workspaceModel);

        // the label
        Label label = new Label("paramName", paramLabelModel);
        add(label);

        // the drop down field, with a decorator for validations
        DropDownChoice choice = new DropDownChoice("paramValue", workspaceModel,
                new WorkspacesModel(), new WorkspaceChoiceRenderer());
        choice.setRequired(required);
        // set the label to be the paramLabelModel otherwise a validation error would look like
        // "Parameter 'paramValue' is required"
        choice.setLabel(paramLabelModel);

        FormComponentFeedbackBorder feedback = new FormComponentFeedbackBorder("border");
        feedback.add(choice);
        add(feedback);
    }
}

package org.geoserver.web.data.resource;

import java.io.IOException;
import java.util.logging.Level;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.geoserver.catalog.AttributeTypeInfo;
import org.geoserver.catalog.FeatureTypeInfo;
import org.geoserver.web.GeoServerApplication;
import org.geoserver.web.data.layer.SQLViewEditPage;
import org.geoserver.web.wicket.GeoServerAjaxFormLink;
import org.geotools.jdbc.VirtualTable;

@SuppressWarnings("serial")
public class FeatureResourceConfigurationPanel extends ResourceConfigurationPanel {

    ModalWindow reloadWarningDialog;
    
    public FeatureResourceConfigurationPanel(String id, final IModel model) {
        super(id, model);
        
        final Fragment attributePanel = new Fragment("attributePanel", "attributePanelFragment", this);
        attributePanel.setOutputMarkupId(true);
        add(attributePanel);
        
        // just use the direct attributes, this is not editable atm
        ListView attributes = new ListView("attributes", new Model() {
            @Override
            public Object getObject() {
                FeatureTypeInfo typeInfo = (FeatureTypeInfo) model.getObject();
                try {
                    // we cannot call typyInfo.getAttributes() directly as the virtual table 
                    // attached to the typeInfo might have been modified, and it's still only
                    // visible if we pass the modification proxy around it (attributes() will use
                    // this and bypass the modification proxy
                    return GeoServerApplication.get().getCatalog().getResourcePool().getAttributes(typeInfo);
                } catch (IOException e) {
                    throw new WicketRuntimeException(e);
                }
            }
        }) {

            @Override
            protected void populateItem(ListItem item) {
                
                // odd/even style
                item.add(new SimpleAttributeModifier("class",
                        item.getIndex() % 2 == 0 ? "even" : "odd"));

                // dump the attribute information we have
                AttributeTypeInfo attribute = (AttributeTypeInfo) item.getModelObject();
                item.add(new Label("name", attribute.getName()));
                item.add(new Label("minmax", attribute.getMinOccurs() + "/" + attribute.getMaxOccurs()));
                try {
                    // working around a serialization issue
                    FeatureTypeInfo typeInfo = (FeatureTypeInfo) model.getObject();
                    org.opengis.feature.type.PropertyDescriptor pd = typeInfo.getFeatureType().getDescriptor(attribute.getName());
                    String typeName = pd.getType().getBinding().getSimpleName();
                    item.add(new Label("type", typeName));
                    item.add(new Label("nillable", pd.isNillable() + ""));
                } catch(IOException e) {
                    item.add(new Label("type", "?"));
                    item.add(new Label("nillable", "?"));
                }
            }
            
        };
        attributePanel.add(attributes);
        
        // reload links
        WebMarkupContainer reloadContainer = new WebMarkupContainer("reloadContainer");
        attributePanel.add(reloadContainer);
        GeoServerAjaxFormLink reload = new GeoServerAjaxFormLink("reload") {
            @Override
            protected void onClick(AjaxRequestTarget target, Form form) {
                GeoServerApplication app = (GeoServerApplication) getApplication();
                
                FeatureTypeInfo ft = (FeatureTypeInfo)getResourceInfo();
                app.getCatalog().getResourcePool().clear(ft);
                app.getCatalog().getResourcePool().clear(ft.getStore());
                target.addComponent(attributePanel);
            }
        };
        reloadContainer.add(reload);
        
        GeoServerAjaxFormLink warning = new GeoServerAjaxFormLink("reloadWarning") {
            @Override
            protected void onClick(AjaxRequestTarget target, Form form) {
                reloadWarningDialog.show(target);
            }
        };
        reloadContainer.add(warning);
        
        add(reloadWarningDialog = new ModalWindow("reloadWarningDialog"));
        reloadWarningDialog.setPageCreator(new ModalWindow.PageCreator() {
            public Page createPage() {
                return new ReloadWarningDialog(
                    new StringResourceModel("featureTypeReloadWarning", FeatureResourceConfigurationPanel.this, null));
            }
        });
        reloadWarningDialog.setTitle(new StringResourceModel("warning", (Component)  null, null));
        reloadWarningDialog.setInitialHeight(100);
        reloadWarningDialog.setInitialHeight(200);
        
        // sql view handling
        WebMarkupContainer sqlViewContainer = new WebMarkupContainer("editSqlContainer");
        attributePanel.add(sqlViewContainer);
        sqlViewContainer.add(new Link("editSql") {

            @Override
            public void onClick() {
                FeatureTypeInfo typeInfo = (FeatureTypeInfo) model.getObject();
                try {
                    setResponsePage(new SQLViewEditPage(typeInfo, ((ResourceConfigurationPage) this.getPage())));
                } catch(Exception e) {
                    LOGGER.log(Level.SEVERE, "Failure opening the sql view edit page", e);
                    error(e.getMessage());
                }
            }
            
           
        });
        
        // which one do we show, reload or edit?
        FeatureTypeInfo typeInfo = (FeatureTypeInfo) model.getObject();
        reloadContainer.setVisible(typeInfo.getMetadata().get(FeatureTypeInfo.JDBC_VIRTUAL_TABLE, VirtualTable.class) == null);
        sqlViewContainer.setVisible(!reloadContainer.isVisible());
    }
    
    static class ReloadWarningDialog extends WebPage {
        public ReloadWarningDialog(StringResourceModel message) {
            add(new Label("message", message));
        }
    }
}

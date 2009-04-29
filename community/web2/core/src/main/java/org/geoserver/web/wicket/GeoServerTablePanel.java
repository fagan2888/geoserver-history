/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.web.wicket;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.geoserver.web.wicket.GeoServerDataProvider.Property;

/**
 * An abstract filterable, sortable, pageable table with associated
 * filtering form and paging navigator.<p>
 * The construction of the page is driven by the properties returned
 * by a {@link GeoServerDataProvider}, subclasses only need to build a component
 * for each property by implementing the {@link #getComponentForProperty(String, IModel, Property)}
 * method
 * @author Andrea Aime - OpenGeo
 *
 * @param <T>
 */
public abstract class GeoServerTablePanel<T> extends Panel {
    
    
    private static final int DEFAULT_ITEMS_PER_PAGE = 10;

    // filter form components
    TextField filter;

    Label matched;

    // table components
    DataView dataView;

    WebMarkupContainer listContainer;
    
    WebMarkupContainer navigatorTopContainer;
    GeoServerPagingNavigator navigatorTop;
    WebMarkupContainer navigatorBottomContainer;
    GeoServerPagingNavigator navigatorBottom;

    GeoServerDataProvider<T> dataProvider;
    
    Form filterForm;

    

    public GeoServerTablePanel(String id, final GeoServerDataProvider<T> dataProvider) {
        super(id);
        this.dataProvider = dataProvider;

        // layer container used for ajax-y udpates of the table
        listContainer = new WebMarkupContainer("listContainer");

        // build the filter form
        filterForm = new Form("filterForm");
        add(filterForm);
        filterForm.add(filter = new TextField("filter", new Model()));
        filter.setOutputMarkupId(true);
        AjaxButton filterSubmit = filterSubmitButton();
        filterForm.add(filterSubmit);
        AjaxButton filterResetButton = filterResetButton();
        AjaxButton filterReset = filterResetButton;
        filterForm.add(filterReset);

        // add the filter match label
        filterForm.add(matched = new Label("filterMatch", new ResourceModel("showingAllRecords")));
        matched.setOutputMarkupId(true);

        // setup the table
        listContainer.setOutputMarkupId(true);
        add(listContainer);
        dataView = new DataView("items", dataProvider) {

            @Override
            protected void populateItem(Item item) {
                final IModel itemModel = item.getModel();
                
                // odd/even style
                item.add(new SimpleAttributeModifier("class",
                        item.getIndex() % 2 == 0 ? "even" : "odd"));

                // create one component per viewable property
                item.add(new ListView("itemProperties", dataProvider
                        .getVisibleProperties()) {

                    @Override
                    protected void populateItem(ListItem item) {
                        Property<T> property = (Property<T>) item
                                .getModelObject();
                       
                        Component component = getComponentForProperty(
                                "component", itemModel, property);
                        
                        // add some checks for the id, the error message
                        // that wicket returns in case of mismatch is not
                        // that helpful
                        if (!"component".equals(component.getId()))
                            throw new IllegalArgumentException(
                                    "getComponentForProperty asked "
                                            + "to build a component "
                                            + "with id = 'component' "
                                            + "for property '"
                                            + property.getName()
                                            + "', but got '"
                                            + component.getId() + "' instead");
                        item.add(component);
                    }

                });
            }

        };
        listContainer.add(dataView);

        // add the sorting links
        listContainer.add(new ListView("sortableLinks", dataProvider
                .getVisibleProperties()) {

            @Override
            protected void populateItem(ListItem item) {
                Property<T> property = (Property<T>) item.getModelObject();
                
                // Simple i18n
                String pageName = this.getPage().getClass().getSimpleName();
                ResourceModel resMod = new ResourceModel(pageName+".th."+property.getName(), property.getName());
                
                if (property.getComparator() != null) {
                    Fragment f = new Fragment("header", "sortableHeader", item);
                    AjaxLink link = sortLink(dataProvider, item);
                    link.add(new Label("label", resMod));
                    f.add(link);
                    item.add(f);
                } else {
                    item.add(new Label("header", resMod));
                }
            }

        });

        // add the paging navigator and set the items per page
        dataView.setItemsPerPage(DEFAULT_ITEMS_PER_PAGE);
        
        // add the top navigator
        filterForm.add(navigatorTopContainer = new WebMarkupContainer("navigatorTopContainer"));
        navigatorTopContainer.setOutputMarkupId(true);
        navigatorTopContainer.add(navigatorTop = new GeoServerPagingNavigator("navigatorTop", dataView));
        
        // add the bottom navigator
        add(navigatorBottomContainer = new WebMarkupContainer("navigatorBottomContainer"));
        navigatorBottomContainer.setOutputMarkupId(true);
        navigatorBottomContainer.add(navigatorBottom = new GeoServerPagingNavigator("navigatorBottom", dataView));
    }
    
    public void setItemsPerPage(int items) {
        dataView.setItemsPerPage(items);
    }
    
    public GeoServerTablePanel<T> setFilterable( boolean filterable ) {
        filterForm.setVisible( filterable );
        return this;
    }
    
    AjaxLink sortLink(final GeoServerDataProvider<T> dataProvider,
            ListItem item) {
        return new AjaxLink("link", item.getModel()) {

            @Override
            public void onClick(AjaxRequestTarget target) {
                SortParam currSort = dataProvider.getSort();
                Property<T> property = (Property<T>) getModelObject();
                if (currSort == null
                        || !property.getName().equals(
                                currSort.getProperty())) {
                    dataProvider.setSort(new SortParam(property
                            .getName(), true));
                } else {
                    dataProvider.setSort(new SortParam(property
                            .getName(), !currSort.isAscending()));
                }
                target.addComponent(listContainer);
            }

        };
    }


    private AjaxButton filterResetButton() {
        return new AjaxButton("resetFilter") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                updateFilter(target, "");
            }

        };
    }

    private AjaxButton filterSubmitButton() {
        return new AjaxButton("applyFilter") {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form) {
                updateFilter(target, filter.getModelObjectAsString().trim());
            }

        };
    }
    
    private void updateFilter(AjaxRequestTarget target, String flatKeywords) {
        if ("".equals(flatKeywords)) {
            dataProvider.setKeywords(null);
            filter.setModelObject("");
            dataView.setCurrentPage(0);
            matched.setModel(new ResourceModel("showingAllRecords"));
        } else {
            String[] keywords = flatKeywords.split("\\s+");
            dataProvider.setKeywords(keywords);
            dataView.setCurrentPage(0);
            ParamResourceModel paramResMod = new ParamResourceModel(
                    "matchedXOutOfY", this, 
                    Integer.valueOf(dataProvider.size()), 
                    Integer.valueOf(dataProvider.fullSize()));
            matched.setModel(paramResMod);
        }

        target.addComponent(listContainer);
        target.addComponent(navigatorTopContainer);
        target.addComponent(navigatorBottomContainer);
        target.addComponent(matched);
        target.addComponent(filter);
    }
    
    /**
     * Turns filtering capabilities on/off.
     */
    public void setFilterVisible(boolean filterVisible) {
        filterForm.setVisible(filterVisible);
    }

    /**
     * Returns the component that will represent a property of a table item.
     * Usually it should be a label, or a link, but you can return pretty much
     * everything.
     * 
     * @param itemModel
     * @param property
     * @return
     */
    protected abstract Component getComponentForProperty(String id,
            IModel itemModel, Property<T> property);

}

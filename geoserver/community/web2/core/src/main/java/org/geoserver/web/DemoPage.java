package org.geoserver.web;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;
import java.util.List;

public class DemoPage extends GeoServerBasePage{
    public DemoPage(){
        List<DemoLinkInfo> links = getGeoServerApplication().getBeansOfType(DemoLinkInfo.class);
        add(new ListView("demoList", links){
            public void populateItem(ListItem item){
                final DemoLinkInfo info = (DemoLinkInfo)item.getModelObject();
                item.add(new BookmarkablePageLink("theLink", info.getComponentClass())
                .add(new Label("theTitle", new StringResourceModel(info.getTitleKey(), (Component)null, null))));
                item.add(new Label("theDescription", new StringResourceModel(info.getDescriptionKey(), (Component)null, null)));
            }
        });

    }
}

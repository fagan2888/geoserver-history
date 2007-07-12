/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.geoserver.template;

import com.vividsolutions.jts.geom.Geometry;

import freemarker.ext.beans.ArrayModel;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.CollectionModel;
import freemarker.ext.beans.DateModel;
import freemarker.ext.beans.EnumerationModel;
import freemarker.ext.beans.IteratorModel;
import freemarker.ext.beans.MapModel;
import freemarker.ext.beans.NumberModel;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.ext.beans.SimpleMapModel;
import freemarker.ext.beans.StringModel;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelAdapter;
import freemarker.template.TemplateModelException;
import org.geotools.feature.AttributeType;
import org.geotools.feature.Feature;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureType;
import org.geotools.feature.GeometryAttributeType;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * Wraps a {@link Feature} in the freemarker {@link BeansWrapper} interface
 * allowing a template to be directly applied to a {@link Feature} or
 * {@link FeatureCollection}.
 * <p>
 * When a {@link FeatureCollection} is being processed by the template, it is
 * available via the <code>$features</code> variable, which can be broken down into single features and attributes following this hierarchy:
 * <ul>
 *   <li>features -> feature</li>
 *     <ul>
 *       <li>fid (String)</li>
 *       <li>typeName (String)</li>
 *       <li>attributes -> attribute</li>
 *       <ul>
 *         <li>value (Object)</li>
 *         <li>name (String)</li>
 *         <li>type (String)</li>
 *         <li>isGeometry (Boolean)</li>
 *       </ul>
 *     </ul>
 * </ul>
 * Example of a template processing a feature collection which will print
 * out the features id of every feature in the collection.
 * <pre><code>
 *  &lt;#list features as feature&gt;
 *  FeatureId: ${feature.fid}
 *  &lt;/#list&gt;
 * </code></pre>
 * </p>
 * <p>
 * To use this wrapper,use the {@link Configuration#setObjectWrapper(freemarker.template.ObjectWrapper)}
 * method:
 * <pre>
 *         <code>
 *  //features we want to apply template to
 *  FeatureCollection features = ...;
 *
 *  //create the configuration and set the wrapper
 *  Configuration cfg = new Configuration();
 *  cfg.setObjectWrapper( new FeatureWrapper() );
 *
 *  //get the template and go
 *  Template template = cfg.getTemplate( "foo.ftl" );
 *  template.process( features, System.out );
 *
 *         </code>
 * </pre>
 * </p>
 * </p>
 * @author Justin Deoliveira, The Open Planning Project, jdeolive@openplans.org
 * @author Andrea Aime, TOPP
 *
 */
public class FeatureWrapper extends BeansWrapper {
    public FeatureWrapper() {
        setSimpleMapWrapper(true);
    }

    public TemplateModel wrap(Object object) throws TemplateModelException {
        //check for feature collection
        if (object instanceof FeatureCollection) {
            //create a model with just one variable called 'features'
            SimpleHash map = new SimpleHash();
            map.put("features", new CollectionModel((FeatureCollection) object, this));
            map.put("type", wrap(((FeatureCollection) object).getSchema()));

            return map;
        } else if (object instanceof FeatureType) {
            FeatureType ft = (FeatureType) object;
            
            // create a variable "attributes" which his a list of all the 
            // attributes, but at the same time, is a map keyed by name
            Map attributeMap = new LinkedHashMap();
            for (int i = 0; i < ft.getAttributeCount(); i++) {
                AttributeType type = ft.getAttributeType(i);

                Map attribute = new HashMap();
                attribute.put("name", type.getLocalName());
                attribute.put("type", type.getBinding().getName());
                attribute.put("isGeometry", Boolean.valueOf(type instanceof GeometryAttributeType));

                attributeMap.put(type.getLocalName(), attribute);
            }

            // build up the result, feature type is represented by its name an attributes
            SimpleHash map = new SimpleHash();
            map.put("attributes", new SequenceMapModel(attributeMap, this));
            map.put("name", ft.getTypeName());
            return map;
        } else if (object instanceof Feature) {
            Feature feature = (Feature) object;

            //create the model
            SimpleHash map = new SimpleHash();

            //first add the feature id
            map.put("fid", feature.getID());
            map.put("typeName", feature.getFeatureType().getTypeName());

            //next add variables for each attribute, variable name = name of attribute
            SimpleSequence attributes = new SimpleSequence();
            Map attributeMap = new LinkedHashMap();

            for (int i = 0; i < feature.getNumberOfAttributes(); i++) {
                AttributeType type = feature.getFeatureType().getAttributeType(i);

                Map attribute = new HashMap();
                Object value = feature.getAttribute(i);
                if ( value != null ) {
                    //some special case checks
                    if ( value instanceof Date ) {
                          Date date = (Date) value;
                          attribute.put("value", DateFormat.getInstance().format( date ) );
                    } else {
                         attribute.put("value", value);  
                    }
                    attribute.put("isGeometry", Boolean.valueOf(value instanceof Geometry));
                } else {
                    //nulls throw tempaltes off, use empty string
                    attribute.put( "value", "" );
                    attribute.put("isGeometry", Boolean.valueOf(Geometry.class.isAssignableFrom(type.getType())));
                }

                attribute.put("name", type.getName());
                attribute.put("type", type.getType().getName());

                map.put(type.getName(), attribute);
                attributeMap.put(type.getName(), attribute);
                attributes.add(attribute);
            }

            // create a variable "attributes" which his a list of all the 
            // attributes, but at the same time, is a map keyed by name
            map.put("attributes", new SequenceMapModel(attributeMap, this));

            return map;
        }
        
        return super.wrap(object);
    }
}

/* Copyright (c) 2001, 2003 TOPP - www.openplans.org.  All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
/*
 * Created on Jan 20, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.geotools.validation.xml;

import org.geotools.validation.Validation;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Contains the information required for Validation creation.
 * 
 * <p>
 * Currently just used for configuration, may need to be public for dynamic
 * configuration.
 * </p>
 *
 * @see http://vwfs.refractions.net/docs/Validating_Web_Feature_Server.pdf
 */
class PlugIn {
    Map defaults;
    String plugInName;
    String plugInDescription;
    BeanInfo beanInfo;
    Map propertyMap;

    PlugIn(Map config) throws ValidationException {
        this(get(config, "name"), get(config, "bean", Validation.class),
            get(config, "description"), config);
    }

    PlugIn(String name, Class type, String description, Map config)
        throws ValidationException {
        if ((type == null)
                || (!Validation.class.isAssignableFrom(type)
                && type.isInterface())) {
            throw new ValidationException("Not a validation test '" + name
                + "' plugIn:" + type);
        }

        try {
            beanInfo = Introspector.getBeanInfo(type);
        } catch (IntrospectionException e) {
            throw new ValidationException("Could not use the '" + name
                + "' plugIn:" + type);
        }

        defaults = config;
        plugInName = name;
        plugInDescription = description;

        propertyMap = propertyMap(beanInfo);
    }

    protected PropertyDescriptor propertyInfo(String name) {
        return (PropertyDescriptor) propertyMap.get(name);
    }

    protected static Map propertyMap(BeanInfo info) {
        PropertyDescriptor[] properties = info.getPropertyDescriptors();
        Map lookup = new HashMap(properties.length);

        for (int i = 0; i < properties.length; i++) {
            lookup.put(properties[i].getName(), properties[i]);
        }

        return lookup;
    }

    /**
     * Create a Validation based on provided <code>test</code> definition.
     * 
     * <p>
     * Creates the required Java Bean and configures according to the provided
     * test definition, using this plugIn's defaults.
     * </p>
     *
     * @param name Map defining User's test.
     * @param description DOCUMENT ME!
     * @param args DOCUMENT ME!
     *
     * @return Validation ready for use by the ValidationProcessor
     *
     * @throws ValidationException when an error occurs
     */
    public Validation createValidation(String name, String description, Map args)
        throws ValidationException {
        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
        Class type = beanDescriptor.getBeanClass();

        Constructor create;

        try {
            create = type.getConstructor(new Class[0]);
        } catch (SecurityException e) {
            throw new ValidationException("Could not create '" + plugInName
                + "' as " + type.getName(), e);
        } catch (NoSuchMethodException e) {
            throw new ValidationException("Could not create '" + plugInName
                + "' as " + type.getName(), e);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Could not create '" + plugInName
                + "' as " + type.getName(), e);
        }

        Validation validate;

        try {
            validate = (Validation) create.newInstance(new Object[0]);
        } catch (InstantiationException e) {
            throw new ValidationException("Could not create '" + name
                + "' as plugIn " + plugInName, e);
        } catch (IllegalAccessException e) {
            throw new ValidationException("Could not create '" + name
                + "' as plugIn " + plugInName, e);
        } catch (InvocationTargetException e) {
            throw new ValidationException("Could not create '" + name
                + "' as plugIn " + plugInName, e);
        }

        validate.setName(name);
        validate.setDescription(description);
        configure(validate, defaults);
        configure(validate, args);

        return validate;
    }

    protected void configure(Object bean, Map config)
        throws ValidationException {
        if ((config == null) || (config.size() == 0)) {
            return;
        }

        PropertyDescriptor property;

        for (Iterator i = config.entrySet().iterator(); i.hasNext();) {
            Map.Entry entry = (Map.Entry) i.next();
            property = propertyInfo((String) entry.getKey());

            if (property == null) {
                // error here
                continue;
            }

            try {
                property.getWriteMethod().invoke(bean,
                    new Object[] { entry.getValue() });
            } catch (IllegalArgumentException e) {
                throw new ValidationException("test failed to configure "
                    + plugInName + " " + entry.getKey(), e);
            } catch (IllegalAccessException e) {
                throw new ValidationException("test failed to configure "
                    + plugInName + " " + entry.getKey(), e);
            } catch (InvocationTargetException e) {
                throw new ValidationException("test failed to configure "
                    + plugInName + " " + entry.getKey(), e);
            }
        }
    }

    /**
     * get purpose.
     * 
     * <p>
     * Gets a String from a map of Strings
     * </p>
     *
     * @param map Map the map to extract the string from
     * @param key String the key for the map.
     *
     * @return String the value in the map.
     *
     * @see Map
     */
    private static String get(Map map, String key) {
        if (map.containsKey(key)) {
            return (String) map.get(key);
        }

        return null;
    }

    /**
     * get purpose.
     * 
     * <p>
     * Gets a Class from a map given the specified key. If the Class is not
     * found the default Class is returned.
     * </p>
     *
     * @param map Map the map to extract the file from
     * @param key String the key to extract the value for
     * @param defaultType The default value should the key not exist.
     *
     * @return Class an boolean as described above.
     */
    private static Class get(Map map, String key, Class defaultType) {
        if (!map.containsKey(key)) {
            return defaultType;
        }

        Object value = map.get(key);

        if (value instanceof Class) {
            return (Class) value;
        }

        if (value instanceof String) {
            try {
                return Class.forName((String) value);
            } catch (ClassNotFoundException e) {
                // error
            }
        }

        return defaultType;
    }
}

/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.security.impl;

import static org.geoserver.security.impl.DataAccessRule.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.geoserver.catalog.Catalog;
import org.geoserver.config.GeoServerDataDirectory;
import org.geoserver.platform.GeoServerExtensions;
import org.geoserver.security.AccessMode;
import org.geoserver.security.CatalogMode;
import org.geotools.util.logging.Logging;

/**
 * Allows one to manage the rules used by the per layer security subsystem
 * TODO: consider splitting the persistence of properties into two strategies,
 * and in memory one, and a file system one (this class is so marginal that
 * I did not do so right away, in memory access is mostly handy for testing)
 */
public class DataAccessRuleDAO extends AbstractAccessRuleDAO<DataAccessRule> {

    
    static {
        LOGGER = Logging.getLogger(DataAccessRuleDAO.class);
    }

    /**
     * property file name
     */
    static final String LAYERS = "layers.properties";
    
    /**
     * The catalog
     */
    Catalog rawCatalog;

    /**
     * Default to the highest security mode
     */
    CatalogMode catalogMode = CatalogMode.HIDE;

    /**
     * Returns the instanced contained in the Spring context for the UI to use
     * @return
     */
    public static DataAccessRuleDAO get() {
       return GeoServerExtensions.bean(DataAccessRuleDAO.class); 
    }

    /**
     * Builds a new dao
     * 
     * @param rawCatalog
     */
    public DataAccessRuleDAO(GeoServerDataDirectory dd, Catalog rawCatalog) throws IOException {
        super(dd, LAYERS);
        this.rawCatalog = rawCatalog;
    }
    
    /**
     * Builds a new dao with a custom security dir. Used mostly for testing purposes
     * 
     * @param rawCatalog
     */
    DataAccessRuleDAO(Catalog rawCatalog, File securityDir) {
        super(securityDir, LAYERS);
        this.rawCatalog = rawCatalog;
    }

    /**
     * The way the catalog should react to unauthorized access
     * 
     * @return
     */
    public CatalogMode getMode() {
        checkPropertyFile(false);
        return catalogMode;
    }
    
    /**
     * Parses the rules contained in the property file
     * 
     * @param props
     * @return
     */
    protected void loadRules(Properties props) {
        TreeSet<DataAccessRule> result = new TreeSet<DataAccessRule>();
        catalogMode = CatalogMode.HIDE;
        for (Map.Entry<Object,Object> entry : props.entrySet()) {
            String ruleKey = (String) entry.getKey();
            String ruleValue = (String) entry.getValue();

            // check for the mode
            if ("mode".equalsIgnoreCase(ruleKey)) {
                try {
                    catalogMode = CatalogMode.valueOf(ruleValue.toUpperCase());
                } catch (Exception e) {
                    LOGGER.warning("Invalid security mode " + ruleValue + " acceptable values are "
                            + Arrays.asList(CatalogMode.values()));
                }
            } else {
                DataAccessRule rule = parseDataAccessRule(ruleKey, ruleValue);
                if (rule != null) {
                    if (result.contains(rule))
                        LOGGER.warning("Rule " + ruleKey + "." + ruleValue
                                + " overwrites another rule on the same path");
                    result.add(rule);
                }
            }
        }
        
        // make sure the two basic rules if the set is empty
        if(result.size() == 0) {
            result.add(new DataAccessRule(DataAccessRule.READ_ALL));
            result.add(new DataAccessRule(DataAccessRule.WRITE_ALL));
        }
        
        rules = result;
    }

    /**
     * Parses a single layer.properties line into a {@link DataAccessRule}, returns false if the
     * rule is not valid
     * 
     * @return
     */
    DataAccessRule parseDataAccessRule(String ruleKey, String ruleValue) {
        final String rule = ruleKey + "=" + ruleValue;

        // parse
        String[] elements = parseElements(ruleKey);
        if(elements.length != 3) {
            LOGGER.warning("Invalid rule " + rule + ", the expected format is workspace.layer.mode=role1,role2,...");
            return null;
        }
        String workspace = elements[0];
        String layerName = elements[1];
        String modeAlias = elements[2];
        Set<String> roles = parseRoles(ruleValue);

        // perform basic checks on the elements
        if (elements.length != 3) {
            LOGGER.warning("Invalid rule '" + rule
                    + "', the standard form is [namespace].[layer].[mode]=[role]+ "
                    + "Rule has been ignored");
            return null;
        }

        // emit warnings for unknown workspaces, layers, but don't skip the rule,
        // people might be editing the catalog structure and will edit the access rule
        // file afterwards
        if (!ANY.equals(workspace) && rawCatalog.getWorkspaceByName(workspace) == null)
            LOGGER.warning("Namespace/Workspace " + workspace + " is unknown in rule " + rule);
        if (!ANY.equals(layerName) && rawCatalog.getLayerByName(layerName) == null)
            LOGGER.warning("Layer " + workspace + " is unknown in rule + " + rule);

        // check the access mode sanity
        AccessMode mode = AccessMode.getByAlias(modeAlias);
        if (mode == null) {
            LOGGER.warning("Unknown access mode " + modeAlias + " in " + ruleKey
                    + ", skipping rule " + rule);
            return null;
        }

        // check ANY usage sanity
        if (ANY.equals(workspace)) {
            if (!ANY.equals(layerName)) {
                LOGGER.warning("Invalid rule " + rule + ", when namespace "
                        + "is * then also layer must be *. Skipping rule " + rule);
                return null;
            }
        }

        // build the rule
        return new DataAccessRule(workspace, layerName, mode, roles);
    }
    
    /**
     * Turns the rules list into a property bag
     * @return
     */
    protected Properties toProperties() {
        Properties props = new Properties();
        props.put("mode", catalogMode.toString());
        for (DataAccessRule rule : rules) {
        	String key = rule.getWorkspace().replaceAll("\\.", "\\\\.") + "." 
        	             + rule.getLayer().replaceAll("\\.", "\\\\.") + "." 
        	             + rule.getAccessMode().getAlias();
        	props.put(key, rule.getValue());
        }
        return props;
    }

    /**
     * Parses workspace.layer.mode into an array of strings
     * 
     * @param path
     * @return
     */
    static String[] parseElements(String path) {
        String[] rawParse = path.trim().split("\\s*\\.\\s*");
        List<String> result = new ArrayList<String>();
        String prefix = null;
        for (String raw : rawParse) {
            if(prefix != null)
                raw = prefix + "."  + raw;
            // just assume the escape is invalid char besides \. and check it once only
            if (raw.endsWith("\\")) {
                prefix = raw.substring(0, raw.length() - 1);
            } else {
                result.add(raw);
                prefix = null;
            }
        }
        
        return (String[]) result.toArray(new String[result.size()]);
    }

	public void setCatalogMode(CatalogMode catalogMode) {
		this.catalogMode = catalogMode;
	}
	
	public static CatalogMode getByAlias(String alias){
		for(CatalogMode mode: CatalogMode.values()){
			if(mode.name().equals(alias)){
				return mode;
			}
		}
		return null;
	}

}

/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */

package org.geoserver.xacml.geoxacml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.geoserver.platform.GeoServerExtensions;
import org.geoserver.xacml.role.DefaultRoleAssignmentAuthority;
import org.geoserver.xacml.role.RoleAssignmentAuthority;
import org.geotools.xacml.geoxacml.config.GeoXACML;
import org.geotools.xacml.geoxacml.finder.impl.GeoSelectorModule;
import org.geotools.xacml.transport.XACMLLocalTransportFactory;
import org.geotools.xacml.transport.XACMLTransport;
import org.geotools.xacml.transport.XACMLTransportFactory;
import org.vfny.geoserver.global.GeoserverDataDirectory;


import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import com.sun.xacml.finder.AttributeFinder;
import com.sun.xacml.finder.AttributeFinderModule;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.PolicyFinderModule;
import com.sun.xacml.finder.impl.CurrentEnvModule;

/**
 * @author Christian Mueller
 * 
 *         GeoXAMCL Configuration for Geoserver environment
 * 
 */
public class GeoXACMLConfig {

    private static PDP pdp;

    private static Object pdpLock = new Object();

    private static XACMLTransportFactory transportFactory;

    private static Object transportFactoryLock = new Object();

    private static RoleAssignmentAuthority raa;

    private static Object raaLock = new Object();

    private static String repositoryBaseDir = null;
    
    static public void reset() {
        synchronized (pdpLock) {
            pdp=null;
        }
        synchronized (transportFactoryLock) {
            transportFactory=null;
        }

    }
    
    static public void setPolicyRepsoitoryBaseDir(String baseDir) {
        repositoryBaseDir=baseDir;
    }

    
    /**
     * @return a XAMCL PDP (Policy Declision Point) for the geoserver environment
     */
    static protected PDP getPDP() {

        synchronized (pdpLock) {
            if (pdp != null)
                return pdp;

            // initialize the geotools part
            GeoXACML.initialize();

            DataDirPolicyFinderModlule policyModule = new DataDirPolicyFinderModlule();
            if (repositoryBaseDir!=null)
                policyModule.setBaseDir(repositoryBaseDir);

            PolicyFinder policyFinder = new PolicyFinder();
            Set<PolicyFinderModule> policyModules = new HashSet<PolicyFinderModule>();
            policyModules.add(policyModule);
            policyFinder.setModules(policyModules);

            // for current time, current date ....
            CurrentEnvModule envModule = new CurrentEnvModule();

            // xpath support
            GeoSelectorModule selectorModule = new GeoSelectorModule();

            AttributeFinder attrFinder = new AttributeFinder();
            List<AttributeFinderModule> attrModules = new ArrayList<AttributeFinderModule>();
            attrModules.add(envModule);
            attrModules.add(selectorModule);
            attrFinder.setModules(attrModules);

            pdp = new PDP(new PDPConfig(attrFinder, policyFinder, null));
            return pdp;
        }
    }

    /**
     * Use GeoserverExtensions to create a {@link RoleAssignmentAuthority} If nothing is configured,
     * use {@link DefaultRoleAssignmentAuthority}
     * 
     * @return a RoleAssignmentAuthorty
     */
    static public RoleAssignmentAuthority getRoleAssignmentAuthority() {
        synchronized (raaLock) {
            if (raa != null)
                return raa;
            raa = GeoServerExtensions.bean(RoleAssignmentAuthority.class);
            if (raa == null) {
                raa = new DefaultRoleAssignmentAuthority();
            }
            return raa;
        }

    }

    static public XACMLTransport getXACMLTransport() {
        return getXACMLTransportFacytory().getXACMLTransport();
    }

    static private XACMLTransportFactory getXACMLTransportFacytory() {
        if (transportFactory != null)
            return transportFactory;

        synchronized (transportFactoryLock) {
            if (transportFactory != null)
                return transportFactory;
            transportFactory = GeoServerExtensions.bean(XACMLTransportFactory.class);
            if (transportFactory == null)
                transportFactory = new XACMLLocalTransportFactory(getPDP(), true);
            return transportFactory;
        }

    }
    
    public static void createDefaultRepositoryIfNotExisting() {
        File geoServerDataDir = GeoserverDataDirectory.getGeoserverDataDirectory();
        
        if (geoServerDataDir==null) {
            return;
        }
        
        createDirectoryIfNotExisting(new File(geoServerDataDir,DataDirPolicyFinderModlule.BASE_DIR));
        String byRequestDir = DataDirPolicyFinderModlule.BASE_DIR+"/"+DataDirPolicyFinderModlule.BY_REQUEST_DIR;
        String byReferenceDir = DataDirPolicyFinderModlule.BASE_DIR+"/"+DataDirPolicyFinderModlule.BY_REFERENCE_DIR;
        String commonDir = byReferenceDir+"/common";
        String anonymousDir=byReferenceDir+"/anonymous";
        String authenticatedDir=byReferenceDir+"/authenticated";
        
        createDirectoryIfNotExisting(new File(geoServerDataDir,byRequestDir));
        createDirectoryIfNotExisting(new File(geoServerDataDir,byReferenceDir));
        createDirectoryIfNotExisting(new File(geoServerDataDir,commonDir));
        createDirectoryIfNotExisting(new File(geoServerDataDir,anonymousDir));
        createDirectoryIfNotExisting(new File(geoServerDataDir,authenticatedDir));

        copyFileIfNotExisting(geoServerDataDir, commonDir+"/PermitAll.xml");
        copyFileIfNotExisting(geoServerDataDir, commonDir+"/DenyAll.xml");
        copyFileIfNotExisting(geoServerDataDir, anonymousDir+"/PAnonymous.xml");
        copyFileIfNotExisting(geoServerDataDir, authenticatedDir+"/PAuthenticated.xml");
        
        copyFileIfNotExisting(geoServerDataDir, byRequestDir+"/Admin.xml");
        copyFileIfNotExisting(geoServerDataDir, byRequestDir+"/Catalog.xml");
        copyFileIfNotExisting(geoServerDataDir, byRequestDir+"/Anonymous.xml");
        copyFileIfNotExisting(geoServerDataDir, byRequestDir+"/Authenticated.xml");
    }

    private static void createDirectoryIfNotExisting(File dir) {
        if (dir.exists()) return;
        dir.mkdir();
    }
    
    private static void copyFileIfNotExisting(File geoServerDataDir, String relativePath) {
        File file =  new File(geoServerDataDir,relativePath);
        if (file.exists()) return ;
        
        URL sourceURL = GeoXACMLConfig.class.getResource("/geoserverdatadir/"+relativePath);
        try {
        InputStream in = sourceURL.openStream();
        OutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[4096];
        int bytesread = 0;
        while ((bytesread = in.read(buffer))>0) 
            out.write(buffer, 0, bytesread);
        in.close();
        out.close();
        } catch (Exception e) {
            XACMLUtil.getXACMLLogger().severe("Could not create default repository file "+ relativePath);
            throw new RuntimeException(e);
        }
    }

    
}

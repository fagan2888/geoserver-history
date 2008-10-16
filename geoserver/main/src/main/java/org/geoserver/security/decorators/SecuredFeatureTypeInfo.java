/* Copyright (c) 2001 - 2007 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.security.decorators;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.DataStoreInfo;
import org.geoserver.catalog.FeatureTypeInfo;
import org.geoserver.security.SecureCatalogImpl;
import org.geoserver.security.SecureCatalogImpl.Response;
import org.geoserver.security.SecureCatalogImpl.WrapperPolicy;
import org.geotools.data.FeatureLocking;
import org.geotools.data.FeatureSource;
import org.geotools.data.FeatureStore;
import org.geotools.factory.Hints;
import org.opengis.util.ProgressListener;

/**
 * Wraps a {@link FeatureTypeInfo} so that it will return a secured FeatureSource
 * 
 * @author Andrea Aime - TOPP
 */
public class SecuredFeatureTypeInfo extends DecoratingFeatureTypeInfo {

    WrapperPolicy policy;

    public SecuredFeatureTypeInfo(FeatureTypeInfo info, WrapperPolicy policy) {
        super(info);
        this.policy = policy;
    }

    // --------------------------------------------------------------------------
    // WRAPPED METHODS TO ENFORCE SECURITY POLICY
    // --------------------------------------------------------------------------

    public FeatureSource getFeatureSource(ProgressListener listener, Hints hints)
            throws IOException {
        final FeatureSource fs = delegate.getFeatureSource(listener, hints);
        if (fs == null)
            return null;

        boolean challenge = policy.response == Response.CHALLENGE;
        if (policy == WrapperPolicy.METADATA) {
            throw SecureCatalogImpl.unauthorizedAccess(this.getName());
        } else if (!challenge) {
            return new ReadOnlyFeatureSource(fs, false);
        } else {
            if (fs instanceof FeatureLocking)
                return new ReadOnlyFeatureLocking((FeatureLocking) fs, true);
            else if (fs instanceof FeatureStore)
                return new ReadOnlyFeatureStore((FeatureStore) fs, true);
            else
                return new ReadOnlyFeatureSource(fs, true);
        }
    }

    public DataStoreInfo getStore() {
        final DataStoreInfo store = delegate.getStore();
        if (store == null)
            return null;
        else
            return new SecuredDataStoreInfo(store, policy);
    }

    public void setCatalog(Catalog catalog) {
        delegate.setCatalog(catalog);
    }

    public void setAlias(List<String> aliases) {
        delegate.setAlias(aliases);
    }
}

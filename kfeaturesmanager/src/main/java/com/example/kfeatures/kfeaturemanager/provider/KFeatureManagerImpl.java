package com.example.kfeatures.kfeaturemanager.provider;

import com.example.kfeatures.kfeaturemanager.api.KFeatureManager;
import java.util.Hashtable;
import org.apache.karaf.features.FeaturesService;
import org.osgi.framework.*;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KFeatureManagerImpl implements KFeatureManager, BundleActivator {

    static Logger log = LoggerFactory.getLogger(KFeatureManagerImpl.class);

    private ServiceRegistration<KFeatureManager> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Registering service.");
        registration = context.registerService(
                KFeatureManager.class,
                new KFeatureManagerImpl(),
                new Hashtable<String, String>());
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Unregistering service.");
        registration.unregister();
    }

    @Override
    public void installFeature(String featureName) throws Exception {
        BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        ServiceReference<FeaturesService> ref = context.getServiceReference(FeaturesService.class);
        if (ref != null) {
            FeaturesService featuresService = context.getService(ref);
            if (featuresService != null) {
                featuresService.installFeature(featureName);
                context.ungetService(ref);
            } else {
                System.out.println("No features service found");
            }
        } else {
            System.out.println("No features service ref found");
        }
    }
}

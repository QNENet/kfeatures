package com.example.kfeatures.kfeaturemanager.provider;

import com.example.kfeatures.kfeaturemanager.api.KFeatureManager;
import java.util.Hashtable;
import org.apache.karaf.features.FeaturesService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KFeatureManagerImpl implements KFeatureManager, BundleActivator {

    static Logger log = LoggerFactory.getLogger(KFeatureManagerImpl.class);

    private ServiceReference<KFeatureManager> reference;
    private ServiceRegistration<KFeatureManager> registration;
    private BundleContext context;
    private ServiceTracker featuresServiceTracker;


    @Override
    public void start(BundleContext context) throws Exception {
        this.context = context;
        System.out.println("Registering service.");
        registration = context.registerService(
                KFeatureManager.class,
                new KFeatureManagerImpl(),
                new Hashtable<String, String>());
        reference = registration.getReference();

        featuresServiceTracker = new ServiceTracker(context, FeaturesService.class.getName(), null);
        featuresServiceTracker.open();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Unregistering service.");
        registration.unregister();
        featuresServiceTracker.close();
    }

    @Override
    public void installFeature(String featureName) throws Exception {
        FeaturesService service = (FeaturesService) featuresServiceTracker.getService();
        if (service != null) service.installFeature(featureName);
    }
}

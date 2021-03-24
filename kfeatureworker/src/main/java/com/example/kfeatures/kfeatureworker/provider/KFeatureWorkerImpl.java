package com.example.kfeatures.kfeatureworker.provider;

import com.example.kfeatures.kfeaturemanager.api.KFeatureManager;
import com.example.kfeatures.kfeatureworker.api.KFeatureWorker;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true)
public class KFeatureWorkerImpl implements KFeatureWorker {

    static Logger log = LoggerFactory.getLogger(KFeatureWorkerImpl.class);

    @Reference(cardinality = ReferenceCardinality.OPTIONAL)
    KFeatureManager featureManager;

    @Activate
    public void activate() throws Exception {

        if (featureManager != null) {
            featureManager.installFeature("webconsole");
        }

        log.info("Hello from -> " + getClass().getSimpleName());
    }

    @Deactivate
    public void deactivate() {
        log.info("Goodbye from -> " + getClass().getSimpleName());
    }

}

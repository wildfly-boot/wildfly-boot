/**
 * Copyright 2015-2016 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.swarm.netflix.ribbon.runtime;

import org.jboss.msc.service.*;
import org.wildfly.swarm.netflix.ribbon.RibbonJGroupsArchive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Bob McWhirter
 */
public class ApplicationAdvertiserActivator implements ServiceActivator {
    @Override
    public void activate(ServiceActivatorContext context) throws ServiceRegistryException {

        ServiceTarget target = context.getServiceTarget();

        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(RibbonJGroupsArchive.RIBBON_APP_CONF_PATH );

        if (in == null) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String appName = null;

            while ((appName = reader.readLine()) != null) {
                appName = appName.trim();
                if (!appName.isEmpty()) {
                    ApplicationAdvertiser advertiser = new ApplicationAdvertiser(appName);

                    target.addService(ServiceName.of("netflix", "ribbon", "advertise", appName), advertiser)
                            .addDependency(ClusterManager.SERVICE_NAME, ClusterManager.class, advertiser.getClusterManagerInjector())
                            .install();
                }
            }

        } catch (IOException e) {
            throw new ServiceRegistryException(e);
        }


    }
}

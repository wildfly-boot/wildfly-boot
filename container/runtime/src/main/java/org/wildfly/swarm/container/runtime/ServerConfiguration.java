/**
 * Copyright 2015 Red Hat, Inc, and individual contributors.
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
package org.wildfly.swarm.container.runtime;

import java.util.Collections;
import java.util.List;

import org.jboss.dmr.ModelNode;
import org.jboss.msc.service.ServiceActivator;
import org.jboss.shrinkwrap.api.Archive;
import org.wildfly.swarm.container.Fraction;

/**
 * @author Bob McWhirter
 */
public interface ServerConfiguration<T extends Fraction> {

    Class<T> getType();

    T defaultFraction();

    default List<ServiceActivator> getServiceActivators(T fraction) {
        return Collections.emptyList();
    }

    default void prepareArchive(Archive a) {

    }

    default List<Archive> getImplicitDeployments(T fraction) throws Exception {
        return Collections.emptyList();
    }

    default List<ModelNode> getList(T fraction) throws Exception {
        return Collections.emptyList();
    }

    default boolean isIgnorable() {
        return false;
    }

    /**
     * Priority is useful when two ServerConfigurations handle the same
     * Fraction type T. The ServerConfiguration with the highest priority
     * will win and the others will not get used.
     *
     * @return 0, the default priority
     */
    default int priority() {
        return 0;
    }

}

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
package org.wildfly.swarm.netflix.ribbon;

import org.jboss.shrinkwrap.api.asset.NamedAsset;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Bob McWhirter
 */
public class RibbonJGroupsConfigAsset implements NamedAsset {
    public static final String NAME = "WEB-INF/classes/config.properties";

    public RibbonJGroupsConfigAsset() {

    }

    @Override
    public InputStream openStream() {
        StringBuilder str = new StringBuilder();

        str.append( "ribbon.NIWSServerListClassName:org.wildfly.swarm.runtime.netflix.ribbon.ClusterServerList\n");
        str.append( "ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RoundRobinRule\n");

        return new ByteArrayInputStream( str.toString().getBytes() );
    }

    @Override
    public String getName() {
        return NAME;
    }
}

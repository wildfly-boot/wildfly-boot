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
package org.wildfly.swarm.plugin.gradle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import groovy.lang.Closure;
import groovy.util.ConfigObject;

/**
 * @author Bob McWhirter
 */
public class SwarmExtension {
    private String mainClass;

    private Boolean bundleDependencies = true;

    private Properties properties = new Properties();

    private File propertiesFile;

    private List<File> moduleDirs = new ArrayList<>();

    public SwarmExtension() {

    }

    public void properties(Closure<Properties> closure) {
        ConfigObject config = new ConfigObject();
        closure.setResolveStrategy(Closure.DELEGATE_ONLY);
        closure.setDelegate(config);
        closure.call();
        config.flatten(this.properties);
    }

    public Properties getProperties() {
        return this.properties;
    }

    public String getMainClassName() {
        return this.mainClass;
    }

    public void setMainClassName(String mainClass) {
        this.mainClass = mainClass;
    }

    public Boolean getBundleDependencies() {
        return bundleDependencies;
    }

    public void setBundleDependencies(Boolean bundleDependencies) {
        this.bundleDependencies = bundleDependencies;
    }

    public File getPropertiesFile() {
        return propertiesFile;
    }

    public void setPropertiesFile(final File propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    public List<File> getModuleDirs() {
        return moduleDirs;
    }

    public void setModuleDirs(final List<File> moduleDirs) {
        this.moduleDirs.clear();
        this.moduleDirs.addAll(moduleDirs);
    }
}

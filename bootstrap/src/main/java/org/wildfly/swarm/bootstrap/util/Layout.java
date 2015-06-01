package org.wildfly.swarm.bootstrap.util;

import org.jboss.modules.Module;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoadException;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

/**
 * @author Bob McWhirter
 */
public class Layout {

    private static ClassLoader BOOTSTRAP_CLASSLOADER = null;

    public static boolean isFatJar() throws IOException {
        Path root = getRoot();

        if ( Files.isRegularFile( root ) ) {
            try ( JarFile jar = new JarFile( root.toFile() ) ) {
                ZipEntry propsEntry = jar.getEntry("META-INF/wildfly-swarm.properties");
                if ( propsEntry != null ) {
                    try ( InputStream in = jar.getInputStream( propsEntry ) ) {
                        Properties props = new Properties();
                        props.load(in);
                        if ( props.containsKey( "wildfly.swarm.app.artifact" ) ) {
                            System.setProperty( "wildfly.swarm.app.artifact", props.getProperty( "wildfly.swarm.app.artifact" ) );
                        }
                    }
                    return true;
                }
            }
        }

        return false;
    }

    public static Path getRoot() throws IOException {
        URL location = Layout.class.getProtectionDomain().getCodeSource().getLocation();
        if ( location.getProtocol().equals( "file" ) ) {
            try {
                URI locationURI = location.toURI();
                Path locationPath = Paths.get(locationURI);
                File locationFile = locationPath.toFile();
                String locationFilePathString = locationFile.getPath();
                return Paths.get(locationFilePathString);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        throw new IOException("Unable to determine root" );
    }

    public static Manifest getManifest() throws IOException {
        Path root = getRoot();
        if ( isFatJar() ) {
            try ( JarFile jar = new JarFile( root.toFile()) ) {
                ZipEntry entry = jar.getEntry("META-INF/MANIFEST.MF");
                if ( entry != null ) {
                    InputStream in = jar.getInputStream(entry);
                    return new Manifest(in);
                }
            }
        }

        return null;
    }

    public synchronized static ClassLoader getBootstrapClassLoader() throws ModuleLoadException {
        if ( BOOTSTRAP_CLASSLOADER == null ) {
            try {
                BOOTSTRAP_CLASSLOADER = Module.getBootModuleLoader().loadModule(ModuleIdentifier.create("org.wildfly.swarm.bootstrap")).getClassLoader();
            } catch (ModuleLoadException e) {
                BOOTSTRAP_CLASSLOADER = Layout.class.getClassLoader();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return BOOTSTRAP_CLASSLOADER;
    }
}

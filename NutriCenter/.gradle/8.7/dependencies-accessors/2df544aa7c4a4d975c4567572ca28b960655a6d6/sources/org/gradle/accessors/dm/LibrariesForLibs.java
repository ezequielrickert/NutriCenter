package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final ComLibraryAccessors laccForComLibraryAccessors = new ComLibraryAccessors(owner);
    private final OrgLibraryAccessors laccForOrgLibraryAccessors = new OrgLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Group of libraries at <b>com</b>
     */
    public ComLibraryAccessors getCom() {
        return laccForComLibraryAccessors;
    }

    /**
     * Group of libraries at <b>org</b>
     */
    public OrgLibraryAccessors getOrg() {
        return laccForOrgLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class ComLibraryAccessors extends SubDependencyFactory {
        private final ComSparkjavaLibraryAccessors laccForComSparkjavaLibraryAccessors = new ComSparkjavaLibraryAccessors(owner);

        public ComLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.sparkjava</b>
         */
        public ComSparkjavaLibraryAccessors getSparkjava() {
            return laccForComSparkjavaLibraryAccessors;
        }

    }

    public static class ComSparkjavaLibraryAccessors extends SubDependencyFactory {
        private final ComSparkjavaSparkLibraryAccessors laccForComSparkjavaSparkLibraryAccessors = new ComSparkjavaSparkLibraryAccessors(owner);

        public ComSparkjavaLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>com.sparkjava.spark</b>
         */
        public ComSparkjavaSparkLibraryAccessors getSpark() {
            return laccForComSparkjavaSparkLibraryAccessors;
        }

    }

    public static class ComSparkjavaSparkLibraryAccessors extends SubDependencyFactory {

        public ComSparkjavaSparkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>com.sparkjava:spark-core</b> coordinates and
         * with version reference <b>com.sparkjava.spark.core</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("com.sparkjava.spark.core");
        }

    }

    public static class OrgLibraryAccessors extends SubDependencyFactory {
        private final OrgHibernateLibraryAccessors laccForOrgHibernateLibraryAccessors = new OrgHibernateLibraryAccessors(owner);
        private final OrgHsqldbLibraryAccessors laccForOrgHsqldbLibraryAccessors = new OrgHsqldbLibraryAccessors(owner);

        public OrgLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.hibernate</b>
         */
        public OrgHibernateLibraryAccessors getHibernate() {
            return laccForOrgHibernateLibraryAccessors;
        }

        /**
         * Group of libraries at <b>org.hsqldb</b>
         */
        public OrgHsqldbLibraryAccessors getHsqldb() {
            return laccForOrgHsqldbLibraryAccessors;
        }

    }

    public static class OrgHibernateLibraryAccessors extends SubDependencyFactory {
        private final OrgHibernateHibernateLibraryAccessors laccForOrgHibernateHibernateLibraryAccessors = new OrgHibernateHibernateLibraryAccessors(owner);

        public OrgHibernateLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>org.hibernate.hibernate</b>
         */
        public OrgHibernateHibernateLibraryAccessors getHibernate() {
            return laccForOrgHibernateHibernateLibraryAccessors;
        }

    }

    public static class OrgHibernateHibernateLibraryAccessors extends SubDependencyFactory {

        public OrgHibernateHibernateLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>org.hibernate:hibernate-core</b> coordinates and
         * with version reference <b>org.hibernate.hibernate.core</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("org.hibernate.hibernate.core");
        }

        /**
         * Dependency provider for <b>java8</b> with <b>org.hibernate:hibernate-java8</b> coordinates and
         * with version reference <b>org.hibernate.hibernate.java8</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJava8() {
            return create("org.hibernate.hibernate.java8");
        }

    }

    public static class OrgHsqldbLibraryAccessors extends SubDependencyFactory {

        public OrgHsqldbLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>hsqldb</b> with <b>org.hsqldb:hsqldb</b> coordinates and
         * with version reference <b>org.hsqldb.hsqldb</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getHsqldb() {
            return create("org.hsqldb.hsqldb");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final ComVersionAccessors vaccForComVersionAccessors = new ComVersionAccessors(providers, config);
        private final OrgVersionAccessors vaccForOrgVersionAccessors = new OrgVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com</b>
         */
        public ComVersionAccessors getCom() {
            return vaccForComVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org</b>
         */
        public OrgVersionAccessors getOrg() {
            return vaccForOrgVersionAccessors;
        }

    }

    public static class ComVersionAccessors extends VersionFactory  {

        private final ComSparkjavaVersionAccessors vaccForComSparkjavaVersionAccessors = new ComSparkjavaVersionAccessors(providers, config);
        public ComVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.sparkjava</b>
         */
        public ComSparkjavaVersionAccessors getSparkjava() {
            return vaccForComSparkjavaVersionAccessors;
        }

    }

    public static class ComSparkjavaVersionAccessors extends VersionFactory  {

        private final ComSparkjavaSparkVersionAccessors vaccForComSparkjavaSparkVersionAccessors = new ComSparkjavaSparkVersionAccessors(providers, config);
        public ComSparkjavaVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.com.sparkjava.spark</b>
         */
        public ComSparkjavaSparkVersionAccessors getSpark() {
            return vaccForComSparkjavaSparkVersionAccessors;
        }

    }

    public static class ComSparkjavaSparkVersionAccessors extends VersionFactory  {

        public ComSparkjavaSparkVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>com.sparkjava.spark.core</b> with value <b>2.9.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCore() { return getVersion("com.sparkjava.spark.core"); }

    }

    public static class OrgVersionAccessors extends VersionFactory  {

        private final OrgHibernateVersionAccessors vaccForOrgHibernateVersionAccessors = new OrgHibernateVersionAccessors(providers, config);
        private final OrgHsqldbVersionAccessors vaccForOrgHsqldbVersionAccessors = new OrgHsqldbVersionAccessors(providers, config);
        public OrgVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.hibernate</b>
         */
        public OrgHibernateVersionAccessors getHibernate() {
            return vaccForOrgHibernateVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.org.hsqldb</b>
         */
        public OrgHsqldbVersionAccessors getHsqldb() {
            return vaccForOrgHsqldbVersionAccessors;
        }

    }

    public static class OrgHibernateVersionAccessors extends VersionFactory  {

        private final OrgHibernateHibernateVersionAccessors vaccForOrgHibernateHibernateVersionAccessors = new OrgHibernateHibernateVersionAccessors(providers, config);
        public OrgHibernateVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.org.hibernate.hibernate</b>
         */
        public OrgHibernateHibernateVersionAccessors getHibernate() {
            return vaccForOrgHibernateHibernateVersionAccessors;
        }

    }

    public static class OrgHibernateHibernateVersionAccessors extends VersionFactory  {

        public OrgHibernateHibernateVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.hibernate.hibernate.core</b> with value <b>6.5.0.Final</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCore() { return getVersion("org.hibernate.hibernate.core"); }

        /**
         * Version alias <b>org.hibernate.hibernate.java8</b> with value <b>6.5.0.Final</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJava8() { return getVersion("org.hibernate.hibernate.java8"); }

    }

    public static class OrgHsqldbVersionAccessors extends VersionFactory  {

        public OrgHsqldbVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>org.hsqldb.hsqldb</b> with value <b>2.7.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getHsqldb() { return getVersion("org.hsqldb.hsqldb"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

    }

    public static class PluginAccessors extends PluginFactory {

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

    }

}

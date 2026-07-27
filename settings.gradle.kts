rootProject.name = "estrogen"

pluginManagement {
    repositories {

        gradlePluginPortal()

        maven(url = "https://maven.msrandom.net/repository/cloche")
        maven(url = "https://maven.msrandom.net/repository/root")
        maven(url = "https://maven.is-immensely.gay/nightly")
        if (providers.gradleProperty("use_maven_local").orElse("false").get().toBoolean()) {
            mavenLocal()
        }
    }
}

dependencyResolutionManagement {
    versionCatalogs.create("libs") {
        from(files("libs.versions.toml"))
    }

}
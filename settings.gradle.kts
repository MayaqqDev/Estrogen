rootProject.name = "estrogen"

pluginManagement {
    repositories {

        gradlePluginPortal()

        maven(url = "https://maven.msrandom.net/repository/cloche")
        maven(url = "https://maven.msrandom.net/repository/root")
        maven(url = "https://maven.is-immensely.gay/nightly")
        mavenLocal()
    }
}

dependencyResolutionManagement {
    versionCatalogs.create("libs") {
        from(files("libs.versions.toml"))
    }

}
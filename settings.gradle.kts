rootProject.name = "estrogen"

pluginManagement {
    repositories {
        mavenLocal()

        gradlePluginPortal()

        maven(url = "https://maven.msrandom.net/repository/cloche")
        maven(url = "https://maven.msrandom.net/repository/root")

    }
}

dependencyResolutionManagement {
    versionCatalogs.create("libs") {
        from(files("libs.versions.toml"))
    }

}
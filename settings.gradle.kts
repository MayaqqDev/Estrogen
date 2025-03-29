rootProject.name = "estrogen"

pluginManagement {
    repositories {
        mavenLocal()

        gradlePluginPortal()

        maven(url = "https://maven.msrandom.net/repository/cloche")
    }
}

dependencyResolutionManagement {
    versionCatalogs.create("libs") {
        from(files("libs.versions.toml"))
    }

}
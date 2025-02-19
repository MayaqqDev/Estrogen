repositories {}

val minecraft_version = project.property("minecraft_version") as String

dependencies {
    // We depend on fabric loader here to use the fabric @Environment annotations and get the mixin dependencies
    // Do NOT use other classes from fabric loader
    "modImplementation"("net.fabricmc:fabric-loader:${project.property("fabric_loader_version")}")

    // Fapi
    modApi("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_api_version")}+${minecraft_version}")

    // Create
    modCompileOnly("com.simibubi.create:create-fabric-${minecraft_version}:${project.property("create_fabric_version")}+mc${minecraft_version}")

    // Trinkets
    "modImplementation"("earth.terrarium.baubly:baubly-common-${minecraft_version}:${project.property("baubly_version")}") { isTransitive = false }

    // Ears
    implementation("com.unascribed:ears-api:${project.property("ears_version")}")

    // Figura
    modCompileOnly("org.figuramc:figura-common-mojmap:${project.property("figura_version")}+${minecraft_version}")

    // Create New Age
    modCompileOnly("maven.modrinth:create-new-age:${project.property("new_age_version")}")

    // Mixin Extras
    implementation(annotationProcessor("io.github.llamalad7:mixinextras-common:${project.property("mixin_extras_version")}")!!)

    // JEI
    modCompileOnly("mezz.jei:jei-${minecraft_version}-common-api:${project.property("jei_version")}")

    // 3D skin layer
    modCompileOnly("maven.modrinth:3dskinlayers:${project.property("skinlayers_version")}")

    // Iris Shaders (needed for check)
    modCompileOnly("maven.modrinth:iris:${project.property("iris_version")}+${minecraft_version}")

    // Cobblemon
    modCompileOnly("com.cobblemon:mod:${project.property("cobblemon_version")}+${minecraft_version}")
}

architectury {
    common("fabric", "forge")
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

sourceSets {
    main {
        // Platform specific generated files
        resources {
            srcDirs("src/main/generated")
            exclude(".cache/**")
            exclude("**/.fabric")
            exclude("**/.forge")

        }
    }
}

loom {
    val common = project(":common")
    accessWidenerPath = common.file("src/main/resources/estrogen.accesswidener")
}
plugins {
    id "com.github.johnrengelman.shadow" version "7.1.2"
}
architectury {
    platformSetupLoomIde()
    forge()
}

configurations {
    common
    shadowCommon // Don't use shadow from the shadow plugin because we don't want IDEA to index this.
    compileClasspath.extendsFrom common
    runtimeClasspath.extendsFrom common
    developmentForge.extendsFrom common
}

repositories {}

val minecraft_version = project.property("minecraft_version") as String

dependencies {
    //Forge
    forge("net.minecraftforge:forge:${minecraft_version}-${project.property("forge_version")}")

    //Create
    modImplementation("com.simibubi.create:create-${minecraft_version}:${project.property("create_version")}:slim") { transitive = false }

    //Registrate
    modImplementation("com.tterrag.registrate:Registrate:${project.property("registrate_version")}")

    //Flywheel
    modImplementation("com.jozufozu.flywheel:flywheel-forge-${minecraft_version}:${project.property("flywheel_version")}")

    //Curios
    include(modImplementation("earth.terrarium.baubly:baubly-forge-${minecraft_version}:${project.property("baubly_version")}")) transitive false
    modImplementation("top.theillusivec4.curios:curios-forge:${project.property("curios_version")}+${minecraft_version}")

    //Mixin Extras
    compileOnly(annotationProcessor("io.github.llamalad7:mixinextras-common:${project.property("mixin_extras_version")}"))
    implementation(include("io.github.llamalad7:mixinextras-forge:${project.property("mixin_extras_version")}"))

    // Jei
    compileOnlyApi("mezz.jei:jei-${minecraft_version}-forge-api:${project.property("jei_version")}}")

    //Item Viewers
    switch (project.property("item_viewer").toLowerCase()) {
        case "jei": modLocalRuntime("curse.maven:jei-238222:${project.property("jei_file_id_forge")}"); break
        case "disabled": break
        default: println("Invalid item viewer: ${item_viewer} must be JEI or disabled"); break
    }

    // Resourceful Lib (This is needed because it does not unpackage correctly on forge)
    forgeRuntimeLibrary("com.teamresourceful:yabn:1.0.3")
    forgeRuntimeLibrary("com.teamresourceful:bytecodecs:1.0.2")

    // forge sucks so much
    forgeRuntimeLibrary("com.teamresourceful:resourceful-cosmetics-4j:${project.property("resourceful_cosmetics_version")}")

    //DevAuth
    if (project.property("devauth_enabled") == "true") {
        modRuntimeOnly("me.djtheredstoner:DevAuth-forge-latest:${project.property("devauth_version")}")
    }
    // Ad Astra Testing
    if (project.property("ad_astra_testing") == "true") {
        modRuntimeOnly("maven.modrinth:ad-astra:${project.property("ad_astra_version_forge")}")
        modRuntimeOnly("maven.modrinth:resourceful-config:${project.property("resourceful_config_version_forge")}")
    }
    
    //Forge stuff
    common(project(path: ":common", configuration: "namedElements")) { transitive false }
    shadowCommon(project(path: ":common", configuration: "transformProductionForge")) { transitive = false }
}

processResources {
    Project common = project(":common")

    inputs.property "version", project.version
    inputs.property "contributors", rootProject.contributors
    inputs.property "minecraft_version", rootProject.minecraft_version

    filesMatching("META-INF/mods.toml") {
        expand "version": project.version,
                "contributors": rootProject.contributors,
                "minecraft_version": rootProject.minecraft_version
    }
}

shadowJar {
    exclude "fabric.mod.json"
    configurations = [project.configurations.shadowCommon]
    archiveClassifier.set("dev-shadow")
}

// The Production jar
remapJar {
    inputFile.set shadowJar.archiveFile
    dependsOn shadowJar
    archiveClassifier.set("forge")
}

tasks.register("buildBothForge") {
    remapJar.destinationDirectory = project.rootProject.file("build/libs")
    dependsOn remapJar
}

jar {
    archiveClassifier.set("dev")
}

sourcesJar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    def commonSources = project(":common").sourcesJar
    dependsOn commonSources
    from commonSources.archiveFile.map { zipTree(it) }
}

components.java {
    withVariantsFromConfiguration(project.configurations.shadowRuntimeElements) {
        skip()
    }
}

loom {
    Project common = project(":common")
    accessWidenerPath = common.file("src/main/resources/estrogen.accesswidener")

    forge {
        convertAccessWideners = true
        extraAccessWideners.add loom.accessWidenerPath.get().asFile.name

        mixinConfig("estrogen-common.mixins.json")
        mixinConfig("estrogen.mixins.json")
    }
}

sourceSets {
    main {
        resources {
            srcDirs += [
                    'src/main/generated'
            ]
            exclude ".cache/**"
        }
    }
}

publishing {
    publications {
        mavenForge(MavenPublication) {
            artifactId = rootProject.archives_base_name + "-" + project.name
            from components.java
        }
    }

    repositories {}
}
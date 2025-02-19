plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val minecraft_version = project.property("minecraft_version") as String

architectury {
    platformSetupLoomIde()
    fabric()
}

val common: Configuration by configurations.creating {
    configurations.compileClasspath.get().extendsFrom(this)
    configurations.runtimeClasspath.get().extendsFrom(this)
    configurations["developmentFabric"].extendsFrom(this)
}

repositories {}

sourceSets {
    create("datagen") {
        runtimeClasspath += sourceSets["main"].runtimeClasspath
        compileClasspath += sourceSets["main"].compileClasspath
    }
}

dependencies {
    // Fabric
    "modImplementation"("net.fabricmc:fabric-loader:${project.property("fabric_loader_version")}")

    // Fapi
    "modApi"("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_api_version")}+${minecraft_version}")

    //Create
    "modImplementation"("com.simibubi.create:create-fabric-${minecraft_version}:${project.property("create_fabric_version")}+mc${minecraft_version}")

    //Trinkets
    include("modImplementation"("earth.terrarium.baubly:baubly-fabric-${minecraft_version}:${project.property("baubly_version")}"){ isTransitive = false }!!)
    "modImplementation"("dev.emi:trinkets:${project.property("trinkets_version")}")
    project.property("cardinal_modules").toString().replace(" ", "").split(",").forEach { module ->
        "modApi"("dev.onyxstudios.cardinal-components-api:cardinal-components-${module}:${project.property("cardinal_version")}")
    }

    // REI
    "modCompileOnly"("me.shedaniel:RoughlyEnoughItems-api:${project.property("rei_version")}")
    "modCompileOnly"("me.shedaniel:RoughlyEnoughItems-default-plugin:${project.property("rei_version")}")

    // EMI
    "modCompileOnly"("dev.emi:emi-fabric:${project.property("emi_version")}+${minecraft_version}:api")

    // JEI
    "modCompileOnly"("mezz.jei:jei-${minecraft_version}-fabric-api:${project.property("jei_version")}")

    //Mod Menu
    "modImplementation"("com.terraformersmc:modmenu:${project.property("modmenu_version")}")

    //Mixin Extras
    "implementation"("include"("annotationProcessor"("io.github.llamalad7:mixinextras-fabric:${project.property("mixin_extras_version")}")!!)!!)

    // Item Viewers
    when (project.property("item_viewer").toString().lowercase()) {
        "rei" -> "modLocalRuntime"("me.shedaniel:RoughlyEnoughItems-fabric:${project.property("rei_version")}") { exclude(group = "net.fabricmc") }
        "emi" -> "modLocalRuntime"("dev.emi:emi-fabric:${project.property("emi_version")}+${minecraft_version}")
        "jei" -> "modLocalRuntime"("curse.maven:jei-238222:${project.property("jei_file_id_fabric")}")
        "disabled" -> {}
        else -> println("Invalid item viewer: ${project.property("item_viewer")} must be REI, EMI or disabled")
    }

    //DevAuth
    if (project.property("devauth_enabled") == "true") {
        "modRuntimeOnly"("me.djtheredstoner:DevAuth-fabric:${project.property("devauth_version")}")
    }

    // Ad Astra Testing
    if (project.property("ad_astra_testing") == "true") {
        "modRuntimeOnly"("maven.modrinth:ad-astra:${project.property("ad_astra_version_fabric")}")
        "modRuntimeOnly"("maven.modrinth:resourceful-config:${project.property("resourceful_config_version_fabric")}")
    }

    if (System.getenv("XDG_BACKEND") == "wayland") {
        "modRuntimeOnly"("maven.modrinth:waygl:${project.property("waygl_version")}")
        "modRuntimeOnly"("maven.modrinth:fabric-language-kotlin:${project.property("fabric_language_kotlin_version")}")
        "modRuntimeOnly"("dev.isxander:yet-another-config-lib:${project.property("yacl_version")}")
    }

    if (project.property("skinlayer_enabled") == "true") {
        "modRuntimeOnly"("maven.modrinth:3dskinlayers:${project.property("skinlayers_version")}")
    }

    common(project(":common", configuration = "namedElements")) {
        isTransitive = false
    }
    shadowCommon(project(path = ":common", configuration = "transformProductionFabric")) {
        isTransitive = false
    }
}

tasks.processResources {
    val common = project(":common")

    inputs.property("version", project.property("version"))
    inputs.property("contributors", project.property("contributors"))
    inputs.property("minecraft_version", minecraft_version)

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.property("version"),
            "contributors" to project.property("contributors"),
            "minecraft_version" to minecraft_version
        )
    }
}

tasks.shadowJar {
    configurations = [project.configurations.shadowCommon]
    archiveClassifier.set("dev-shadow")
}

// The Production jar
tasks.remapJar {
    inputFile.set(shadowJar.archiveFile)
    injectAccessWidener = true
    dependsOn(shadowJar)
    archiveClassifier.set("fabric")
}

tasks.register("buildBothFabric") {
    remapJar.destinationDirectory = project.rootProject.file("build/libs")
    dependsOn(remapJar)
}

jar {
    archiveClassifier.set("dev")
}

sourcesJar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    val commonSources = project(":common").sourcesJar
    dependsOn commonSources
    from(commonSources.archiveFile.map { zipTree(it) })
}

components.java {
    withVariantsFromConfiguration(project.configurations.shadowRuntimeElements) {
        skip()
    }
}

publishing {
    publications {
        mavenFabric(MavenPublication) {
            artifactId = rootProject.archives_base_name + "-" + project.name
            from(components.java)
        }
    }

    repositories {}
}

loom {
    val common = project(":common")
    val forge = project(":forge")
    // Datagen, while this is in fabric only, it generates files for forge too
    runs {
        datagen {
            client()
            name("Data Generation (client)")
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${common.file("src/main/generated")}")
            vmArg("-Destrogen.datagen.fabric-output-dir=${file("src/main/generated")}")
            vmArg("-Destrogen.datagen.forge-output-dir=${forge.file("src/main/generated")}")
            vmArg("-Dfabric-api.datagen.modid=estrogen-datagen")
            vmArg("-Dporting_lib.datagen.existing_resources=${common.file("src/main/resources")}")

            runDir("build/datagen")

            source(sourceSets.getByName("datagen"))
        }
    }
    runs {
        mixinDebug {
            inherit client
            name("Mixin Debug")
            vmArg("-Dmixin.debug.export=true")
        }
    }

    accessWidenerPath = common.file("src/main/resources/estrogen.accesswidener")
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/generated")
            exclude(".cache/**")
        }
    }
}
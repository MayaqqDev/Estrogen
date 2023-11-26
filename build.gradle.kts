plugins {
    id ("fabric-loom") version '1.0-SNAPSHOT'
    id ("maven-publish")
}

version = "$project.mod_version"
group = "$project.maven_group"

println(project.archivesBaseName + ": " + version)

repositories {
    maven("https://maven.quiltmc.org/repository/release/") // Quilt Mappings

    maven("https://mvn.devos.one/snapshots/") // Create
    // Create Dependencies
    maven("https://cursemaven.com") // ForgeConfigApiPort
    maven("https://maven.jamieswhiteshirt.com/libs-release") // Reach Entity Attributes
    maven("https://maven.tterrag.com/") // Flywheel
    maven("https://mvn.devos.one/snapshots/") // Milk Lib
    maven("https://maven.ladysnake.org/releases") // Cardinal Components

    maven("https://api.modrinth.com/maven") // LazyDFU, Trinkets, CreateFabricReiBugFix
    maven("https://maven.terraformersmc.com/") // ModMenu, EMI, Cloth Config
    maven("https://maven.shedaniel.me") // REI, Cloth config
    maven("https://repo.unascribed.com") // Ears
    maven("https://jitpack.io/")// Mixin extras, Porting Lib
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1") // DevAuth
}

dependencies {
    // Base
    minecraft("com.mojang:minecraft:${project.minecraft_version}")
    //mappings ("net.fabricmc:yarn:${project.yarn_mappings}:v2")
    mappings ("org.quiltmc:quilt-mappings:${project.quilt_mappings}:intermediary-v2")
    modImplementation ("net.fabricmc:fabric-loader:${project.loader_version}")
    modImplementation ("net.fabricmc.fabric-api:fabric-api:${project.fabric_version}")

    // Create
    modImplementation ("com.simibubi.create:create-fabric-${project.minecraft_version}:${project.create_version}+mc${project.minecraft_version}")

    // Trinkets
    modImplementation ("maven.modrinth:trinkets:${project.trinkets_version}")

    // Cardinal Components
    project.cardinal_modules.replaceAll(" ", "").split(",").each { module ->
        modApi ("dev.onyxstudios.cardinal-components-api:cardinal-components-${module}:${project.cardinal_version}")
    }

    // Integrations
    modCompileOnly ("com.unascribed:ears-api:${project.ears_version}")

    // Config
    modApi("me.shedaniel.cloth:cloth-config-fabric:${project.cloth_config_version}") {
        exclude(group: "net.fabricmc.fabric-api")
    }
    modImplementation ("com.terraformersmc:modmenu:${project.modmenu_version}")

    // REI
    modCompileOnly ("me.shedaniel:RoughlyEnoughItems-api-fabric:${project.rei_version}")
        modImplementation("dev.architectury:architectury-fabric:${project.architectury_version}")

    // EMI
        modCompileOnly "dev.emi:emi-fabric:${emi_version}"

    // REI
    switch (project.item_viewer.toLowerCase()) {
        case "rei":
            modLocalRuntime("me.shedaniel:RoughlyEnoughItems-fabric:${project.rei_version}")
            modLocalRuntime("maven.modrinth:createfabricreibugfix:${project.createreibugfix_version}"); break
        case "emi": modLocalRuntime("dev.emi:emi-fabric:${emi_version}"); break
        case "disabled": break
        default: println("Invalid item viewer: ${project.item_viewer} must be REI, EMI or disabled"); break
    }

    // Mixin extras
    implementation("com.github.LlamaLad7:MixinExtras:${project.mixin_extras_version}")
    annotationProcessor("com.github.LlamaLad7:MixinExtras:${project.mixin_extras_version}")

    // Dev Only
    modRuntimeOnly("me.djtheredstoner:DevAuth-fabric:${project.devauth_version}")
}

processResources {
    inputs.property "version", project.version
    inputs.property "minecraft_version", project.minecraft_version
    inputs.property "loader_version", project.loader_version
    filteringCharset "UTF-8"

    filesMatching("fabric.mod.json") {
        expand "version": project.version,
                "minecraft_version": project.minecraft_version,
                "loader_version": project.loader_version
    }
}

def targetJavaVersion = 17
tasks.withType(JavaCompile).configureEach {
    it.options.encoding = "UTF-8"
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible()) {
        it.options.release = targetJavaVersion
    }
}

java {
    def javaVersion = JavaVersion.toVersion(targetJavaVersion)
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
    archivesBaseName = project.archives_base_name

    withSourcesJar()
}

jar {
    from("LICENSE") {
        rename { "${it}_${project.archivesBaseName}"}
    }
}

publishing {
    publications {
        mavenJava(MavenPublication) {
            from components.java
        }
    }

    repositories { }
}

loom {
    runs {
        datagenClient {
            inherit client
            name ("Data Generation")
            vmArg ("-Dfabric-api.datagen")
            vmArg ("-Dfabric-api.datagen.output-dir=${file("src/main/generated")}")
            vmArg ("-Dfabric-api.datagen.modid=${mod_id}")

            runDir ("build/datagen")
        }
    }
    runs {
        mixinDebug {
            inherit client
            name "Mixin Debug"
            vmArg("-Dmixin.debug.export=true")
        }
    }
}

sourceSets {
    main {
        resources {
            srcDirs += [
                    'src/main/generated'
            ]
        }
    }
}

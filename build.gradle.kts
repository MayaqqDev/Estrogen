@file:Suppress("PropertyName", "UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.cloche)
    kotlin("jvm") version libs.versions.kotlin
    kotlin("plugin.serialization") version libs.versions.kotlin
    `maven-publish`
}

repositories {
    maven(url = "https://maven.parchmentmc.org") { name = "Parchment" }
    maven(url = "https://maven.fabricmc.net") { name = "FabricMC" }
    maven(url = "https://maven.terraformersmc.com/releases/") { name = "TerraformersMC" }
    maven(url = "https://thedarkcolour.github.io/KotlinForForge/") { name = "KotlinForForge" }
    maven(url = "https://maven.minecraftforge.net/") { name = "Forge" }
    maven(url = "https://maven.teamresourceful.com/repository/maven-public/") { name = "Team Resourceful" }
    maven(url = "https://maven.shedaniel.me") { name = "Shedaniel" }
    maven(url = "https://maven.blamejared.com/") { name = "Blamejared" }
    maven(url = "https://maven.tterrag.com") { name = "Tterrag" }
    maven(url = "https://maven.theillusivec4.top/") { name = "TheIllusivec4" }
    maven(url = "https://mvn.devos.one/snapshots/") { name = "Devos Maven"; description = "Create Fabric, Porting Lib, Forge Tags, Milk Lib & Fabric Registrate" }
    maven(url = "https://cursemaven.com") { name = "Curseforge Maven"; description = "Forge Config API Port" }
    maven(url = "https://maven.cafeteria.dev/releases") { name = "Cafeteria Maven"; description = "Fake Player API" }
    maven(url = "https://maven.jamieswhiteshirt.com/libs-release") { name = "JamiesWhiteShirt Maven"; description = "Reach Entity Attributes" }
    maven(url = "https://maven.ladysnake.org/releases") { name = "Ladysnake Maven"; description = "Trinkets" }
    maven(url = "https://repo.unascribed.com") { name = "Unascribed Maven"; description = "Ears" }
    maven(url = "https://api.modrinth.com/maven") { name = "Modrinth Maven"; description = "Jukeboxfix, Ad Astra" }
    maven(url = "https://maven.figuramc.org/releases") { name = "Figura Maven"; description = "Figura" }
    maven(url = "https://maven.is-immensely.gay/nightly") { name = "Sappho Company"; description = "Critter" }
    maven(url = "https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1") { name = "DevAuth maven"; description = "DevAuth" }
    maven(url = "https://maven.isxander.dev/releases") { name = "Xander maven"; description = "YACL" }
    maven(url = "https://maven.impactdev.net/repository/development/") { name = "ImpactDev Maven"; description = "Cobblemon" }
    maven(url = "https://maven.squiddev.cc") { name = "Squid Maven"; description = "Create needs CC: Tweaked for some reason" }
    maven(url = "https://maven.msrandom.net/repository/root") { name = "Ashley"}
    maven(url = "https://jitpack.io/") { name = "Jitpack maven"; description = "Mixin Extras & Fabric ASM" } //NOTE: LEAVE THIS AS LAST
    mavenLocal()
    mavenCentral()
}

val item_viewer_forge: String by project
val item_viewer_fabric: String by project

cloche {
    metadata {
        modId = "estrogen"
        name = "Estrogen"
        description = "Create Addon Based around expressing yourself, fluid handling and expanding your factory. This mod adds a lot of new Items, Blocks, Mechanics and more!"
        license = "LGPL-3.0"
        icon = "assets/estrogen/icon.png"
        url = "https://github.com/MayaqqDev/Estrogen"
        sources = "https://github.com/MayaqqDev/Estrogen"
    }

    mappings {
        official()
        parchment(libs.versions.parchment.get())
    }

    common {
        mixins.from(file("src/common/main/estrogen.mixins.json"))

        dependencies {
            compileOnly(libs.mixin)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            modImplementation(libs.baubly)
            modCompileOnly(libs.ears)
            modCompileOnly(libs.figura)
            modCompileOnly(libs.cobblemon)
            modCompileOnly(libs.createNewAge)
            implementation(libs.mixinExtras)
            annotationProcessor(libs.mixinExtras)
            modCompileOnly(libs.rei.api)
            modCompileOnly(libs.rei.plugin)
        }
    }

    fabric {
        loaderVersion = libs.versions.fabric.get()
        minecraftVersion = libs.versions.minecraft.get()

        include(libs.fabric.baubly)
        include(files(project.relativePath("libs/Kritter-0.0.1-fabric.jar")))

        includedClient() // includedClient() is not a run
        runs {
            client() // this is just the client run not client sourceset
            server()
        }

        dependencies {
            modApi(libs.fabric.api)
            modApi(libs.fabric.kotlin)
            modApi.bundle(libs.bundles.fabric.cardinalComponents)
            //modImplementation(libs.fabric.create)
            modImplementation(libs.fabric.baubly)
            modImplementation(libs.fabric.trinkets)
            modImplementation(files(project.relativePath("libs/Kritter-0.0.1-fabric.jar")))
            modCompileOnly("${libs.fabric.emi.get()}:api") // No clue how to do the :api thing in the version catalog directly
            modCompileOnly(libs.fabric.jei.api)
            modImplementation(libs.fabric.modmenu)
            modCompileOnly(libs.fabric.iris)

            when(item_viewer_fabric) {
                "REI" -> modRuntimeOnly(libs.fabric.rei) { exclude(group = "net.fabricmc") }
                "EMI" -> modRuntimeOnly(libs.fabric.emi)
                "JEI" -> modRuntimeOnly(libs.fabric.jei)
                "disabled" -> {}
                else -> error("Invalid item viewer for Fabric: $item_viewer_forge")
            }

            // modRuntimeOnly(libs.fabric.devauth)
        }

        metadata {
            entrypoint("main") {
                adapter.set("kotlin")
                value.set("dev.mayaqq.estrogen.fabric.EstrogenFabric::init")
            }
            entrypoint("client") {
                adapter.set("kotlin")
                value.set("dev.mayaqq.estrogen.fabric.client.EstrogenClientFabric::init")
            }
        }
    }

    forge {
        loaderVersion = libs.versions.forge.get()
        minecraftVersion = libs.versions.minecraft.get()

        include(libs.forge.baubly)
        include(libs.forge.mixinExtras)
        include(files(project.relativePath("libs/Kritter-0.0.1-forge.jar")))

        runs {
            client()
            server()
        }

        dependencies {
            api(libs.forge.kotlin)
            //modImplementation(libs.forge.create)
            modImplementation(libs.forge.flywheel)
            modImplementation(libs.forge.baubly)
            modImplementation(files(project.relativePath("libs/Kritter-0.0.1-forge.jar")))
            implementation(libs.forge.mixinExtras)
            compileOnlyApi(libs.forge.jei.api)

            when(item_viewer_forge) {
                "EMI" -> modRuntimeOnly(libs.forge.emi)
                "JEI" -> modRuntimeOnly(libs.forge.jei)
                "disabled" -> {}
                else -> error("Invalid item viewer for Forge: $item_viewer_forge")
            }

            // modRuntimeOnly(libs.forge.devauth)
        }
    }
}


java {
    withSourcesJar()
}

tasks.withType<KotlinCompile> {
//    explicitApiMode = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Warning
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_0
        freeCompilerArgs = listOf("-Xmulti-platform", "-Xno-check-actual", "-Xexpect-actual-classes")
    }
}

publishing {
    publications {
        create<MavenPublication>("mod") {
            from(components["java"])
        }
    }

    repositories {
        val username = "sapphoCompanyUsername".let { System.getenv(it) ?: findProperty(it) }?.toString()
        val password = "sapphoCompanyPassword".let { System.getenv(it) ?: findProperty(it) }?.toString()
        if (username != null && password != null) {
            maven("https://maven.is-immensely.gay/${properties["maven_category"]}") {
                name = "sapphoCompany"
                credentials {
                    this.username = username
                    this.password = password
                }
            }
        } else {
            println("Sappho Company credentials not present.")
        }
    }
}
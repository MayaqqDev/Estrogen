@file:Suppress("PropertyName")

import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("earth.terrarium.cloche") version "0.8.1"
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.serialization") version "2.1.0"
    `maven-publish`
}

repositories {
    maven(url = "https://maven.parchmentmc.org") { name = "Parchment" }
    maven(url = "https://maven.fabricmc.net") { name = "FabricMC" }
    maven(url = "https://maven.terraformersmc.com/releases/") { name = "TerraformersMC" }
    maven(url = "https://thedarkcolour.github.io/KotlinForForge/") { name = "KotlinForForge" }
    maven(url = "https://maven.minecraftforge.net/") { name = "Forge" }
    maven(url = "https://maven.teamresourceful.com/repository/maven-public/") { name = "Team Resourceful" }
    maven(url = "https://maven.shedanielme") { name = "Shedaniel" }
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
    maven(url = "https://maven.msrandom.net/repository/root") { name = "Ashley"}
    maven(url = "https://jitpack.io/") { name = "Jitpack maven"; description = "Mixin Extras & Fabric ASM" } //NOTE: LEAVE THIS AS LAST
    mavenLocal()
    mavenCentral()
}

val mc_version: String by project
val fabric_version: String by project
val forge_version: String by project
val mixin_version: String by project
val fapi_version: String by project
val flk_version: String by project
val kff_version: String by project
val parchment_version: String by project
val create_forge_version: String by project
val create_fabric_version: String by project
val flywheel_version: String by project
val baubly_version: String by project
val mixin_extras_version: String by project
val jei_version: String by project
val jei_file_id_forge: String by project
val devauth_version: String by project
val trinkets_version: String by project
val cardinal_version: String by project
val rei_version: String by project
val emi_version: String by project
val modmenu_version: String by project
val jei_file_id_fabric: String by project

val item_viewer_forge: String by project
val item_viewer_fabric: String by project

val cardinal_modules: String by project

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
        parchment(parchment_version)
    }

    common {
        mixins.from(file("src/common/main/estrogen.mixins.json"))

        dependencies {
            compileOnly("org.spongepowered:mixin:$mixin_version")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

        }
    }

    fabric("fabric:$mc_version") {
        loaderVersion = fabric_version
        minecraftVersion = mc_version

        include("earth.terrarium.baubly:baubly-fabric-$mc_version:$baubly_version")

        runs {
            includedClient()
            server()
        }

        dependencies {

            fabricApi("$fapi_version+$mc_version")
            modApi("net.fabricmc:fabric-language-kotlin:$flk_version")

            modImplementation("com.simibubi.create:create-fabric-$mc_version:$create_fabric_version+mc$mc_version")

            modImplementation("earth.terrarium.baubly:baubly-fabric-$mc_version:$baubly_version") { isTransitive = false }
            modImplementation("dev.emi:trinkets:$trinkets_version")
            cardinal_modules.replace(" ", "").split(",").forEach { module ->
                modApi("dev.onyxstudios.cardinal-components-api:cardinal-components-$module:$cardinal_version")
            }

            modCompileOnly("me.shedaniel:RoughlyEnoughItems-api:$rei_version")
            modCompileOnly("me.shedaniel:RoughlyEnoughItems-default-plugin:$rei_version")

            modCompileOnly("dev.emi:emi-fabric:$emi_version+$mc_version}:api")

            modCompileOnly("mezz.jei:jei-$mc_version-fabric-api:$jei_version")

            modImplementation("com.terraformersmc:modmenu:$modmenu_version")

            when(item_viewer_forge) {
                "REI" -> modRuntimeOnly("me.shedaniel:RoughlyEnoughItems-fabric:$rei_version") { exclude(group = "net.fabricmc") }
                "EMI" -> modRuntimeOnly("dev.emi:emi-fabric:$emi_version+$mc_version")
                "JEI" -> modRuntimeOnly("curse.maven:jei-238222:$jei_file_id_fabric")
                "disabled" -> {}
                else -> error("Invalid item viewer for Forge: $item_viewer_forge")
            }
        }

        metadata {
            entrypoint("main") {
                adapter.set("kotlin")
                value.set("dev.mayaqq.estrogen.EstrogenFabric::init")
            }
            entrypoint("client") {
                adapter.set("kotlin")
                value.set("dev.mayaqq.estrogen.client.EstrogenClientFabric::init")
            }
        }
    }

    forge("forge:$mc_version") {
        loaderVersion = forge_version
        minecraftVersion = mc_version

        include("earth.terrarium.baubly:baubly-forge-$mc_version:$baubly_version")
        include("io.github.llamalad7:mixinextras-forge:$mixin_extras_version")

        runs {
            client()
            server()
        }

        dependencies {
            api("thedarkcolour:kotlinforforge:$kff_version")
            modImplementation("com.simibubi.create:create-$mc_version:$create_forge_version:slim") { isTransitive = false }
            //Maybe registrate if it for some reason needs it?
            modImplementation("com.jozufozu.flywheel:flywheel-forge-$mc_version:$flywheel_version")
            modImplementation("earth.terrarium.baubly:baubly-forge-$mc_version:$baubly_version")
            annotationProcessor("io.github.llamalad7:mixinextras-common:$mixin_extras_version")
            compileOnly("io.github.llamalad7:mixinextras-common:$mixin_extras_version")

            implementation("io.github.llamalad7:mixinextras-forge:$mixin_extras_version")

            compileOnlyApi("mezz.jei:jei-$mc_version-forge-api:$jei_version")

            when(item_viewer_forge) {
                "JEI" -> modRuntimeOnly("curse.maven:jei-238222:$jei_file_id_forge")
                "disabled" -> {}
                else -> error("Invalid item viewer for Forge: $item_viewer_forge")
            }

            modRuntimeOnly("me.djtheredstoner:DevAuth-forge-latest:$devauth_version")
        }
    }
}


java {
    withSourcesJar()
}

tasks.withType<KotlinCompile> {
    explicitApiMode = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Warning
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
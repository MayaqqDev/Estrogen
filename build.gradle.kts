@file:Suppress("PropertyName", "UnstableApiUsage")

import net.msrandom.stubs.GenerateStubApi
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
    maven(url = "https://maven.createmod.net/") { name = "Create" }
    maven(url = "https://maven.tterrag.com") { name = "Tterrag" }
    maven(url = "https://maven.theillusivec4.top/") { name = "TheIllusivec4" }
    maven(url = "https://mvn.devos.one/snapshots/") { name = "Devos Maven"; description = "Create Fabric, Porting Lib, Forge Tags, Milk Lib & Fabric Registrate" }
    maven(url = "https://cursemaven.com") { name = "Curseforge Maven"; description = "Forge Config API Port" }
    maven(url = "https://maven.is-immensely.gay/nightly") { name = "Sappho Company"; description = "Critter, Cynosure" }
    maven(url = "https://maven.is-immensely.gay/releases") { name = "Sappho Company"; description = "Kittyconfig" }
    maven(url = "https://maven.cafeteria.dev/releases") { name = "Cafeteria Maven"; description = "Fake Player API" }
    maven(url = "https://maven.jamieswhiteshirt.com/libs-release") { name = "JamiesWhiteShirt Maven"; description = "Reach Entity Attributes" }
    maven(url = "https://maven.ladysnake.org/releases") { name = "Ladysnake Maven"; description = "Trinkets" }
    maven(url = "https://repo.unascribed.com") { name = "Unascribed Maven"; description = "Ears" }
    maven(url = "https://api.modrinth.com/maven") { name = "Modrinth Maven"; description = "Jukeboxfix, Ad Astra" }
    maven(url = "https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1") { name = "DevAuth maven"; description = "DevAuth" }
    maven(url = "https://maven.isxander.dev/releases") { name = "Xander maven"; description = "YACL" }
    maven(url = "https://maven.impactdev.net/repository/development/") { name = "ImpactDev Maven"; description = "Cobblemon" }
    maven(url = "https://maven.squiddev.cc") { name = "Squid Maven"; description = "Create needs CC: Tweaked for some reason" }
    maven(url = "https://maven.msrandom.net/repository/root") { name = "Ashley"}
    maven(url = "https://maven.figuramc.org/releases") { name = "Figura Maven"; description = "Figura" } // Second last cs figura misconfigured their maven
    maven(url = "https://jitpack.io/") { name = "Jitpack maven"; description = "Mixin Extras & Fabric ASM" } //NOTE: LEAVE THIS AS LAST
    mavenLocal()
    mavenCentral()
}

val item_viewer: String by project

val devauth_enabled: String by project

dependencies {
    ksp(libs.kittyconfig.ksp)
}

cloche {
    metadata {
        modId = "estrogen"
        name = "Estrogen"
        description = "Create Addon Based around expressing yourself, fluid handling and expanding your factory. This mod adds a lot of new Items, Blocks, Mechanics and more!"
        license = "LGPL-3.0"
        icon = "assets/estrogen/icon.png"
        url = "https://github.com/MayaqqDev/Estrogen"
        sources = "https://github.com/MayaqqDev/Estrogen"
        author("Mayaqq")
        contributor("https://github.com/MayaqqDev/Estrogen/wiki/Credits")
    }

    mappings {
        official()
        parchment(libs.versions.parchment)
    }

    common {
        mixins.from(file("src/main/estrogen.mixins.json"))

        accessWideners.from(file("src/main/estrogen.accessWidener"))

        dependencies {
            compileOnly(libs.mixin)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            api(libs.flywheel.api)
//            modImplementation(libs.baubly)
            modCompileOnly(libs.ears)
            modCompileOnly(libs.figura)
            modCompileOnly(libs.createNewAge)
            modImplementation(libs.kittyconfig)
            implementation(libs.mixinExtras)
            annotationProcessor(libs.mixinExtras)

            modCompileOnly(libs.kritter)
            modCompileOnly(libs.cynosure)

            implementation(libs.mixinConstrains)
        }
    }

    fabric {
        mixins.from(file("src/main/estrogen.mixins.json"))
        accessWideners.from(file("src/main/estrogen.accessWidener"))

        loaderVersion = libs.versions.fabric
        minecraftVersion = libs.versions.minecraft

        include(libs.fabric.baubly) { exclude(group = "me.shedaniel") }
        include(libs.fabric.kritter)
        include(libs.fabric.flywheel)
        include(libs.fabric.kittyconfig)

        include(libs.mixinConstrains)

        includedClient() // includedClient() is not a run
        runs {
            client()
            server()
        }

        metadata {
            metadata {
                custom("modmenu", mapOf(
                    "links" to mapOf(
                        "estrogen.credits" to "https://github.com/MayaqqDev/Estrogen/wiki/Credits",
                        "modmenu.discord" to "https://discord.gg/hue",
                        "modmenu.kofi" to "https://ko-fi.com/mayaqq",
                        "modmenu.curseforge" to "https://www.curseforge.com/minecraft/mc-mods/estrogen",
                        "modmenu.modrinth" to "https://modrinth.com/mod/estrogen",
                        "modmenu.wiki" to "https://github.com/MayaqqDev/Estrogen/wiki"
                    )
                ))
                custom("cynosure", mapOf(
                    "autosubscription" to true
                ))
                custom("catalogue", mapOf(
                    "icon" to mapOf("item" to "estrogen:estrogen_pill"),
                    "banner" to "icon.png",
                    "background" to "estrogen_background.png",
                    "configFactory" to "dev.mayaqq.estrogen.fabric.integrations.catalogue.CatalogueCompat"
                ))
            }
        }

        dependencies {
            fabricApi(libs.versions.fapi)
            modApi(libs.fabric.kotlin)
            modApi.bundle(libs.bundles.fabric.cardinalComponents)
            modImplementation(libs.fabric.baubly) { exclude(group = "me.shedaniel") }
            modImplementation(libs.fabric.trinkets)
            modCompileOnly(libs.fabric.emi)
            modCompileOnly(libs.fabric.rei)
            modCompileOnly(libs.fabric.jei)
            modImplementation(libs.fabric.modmenu)
            modCompileOnly(libs.fabric.iris)
            modCompileOnlyApi(libs.fabric.flywheel.api)
            modImplementation(libs.fabric.flywheel)
            modImplementation(libs.fabric.cynosure)
            modImplementation(libs.fabric.kritter)
            modApi(libs.fabric.kittyconfig)

            when(item_viewer) {
                "REI" -> modRuntimeOnly(libs.fabric.rei) { exclude(group = "net.fabricmc") }
                "EMI" -> modRuntimeOnly(libs.fabric.emi)
                "JEI" -> modRuntimeOnly(libs.fabric.jei)
                "disabled" -> {}
                else -> error("Invalid item viewer for Fabric: $item_viewer")
            }

            if (devauth_enabled.toBoolean()) modRuntimeOnly(libs.fabric.devauth)
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
            entrypoint("emi") {
                adapter.set("kotlin")
                value.set("dev.mayaqq.estrogen.compat.emi.EmiEstrogenPlugin")
            }
            entrypoint("rei_client") {
                adapter.set("kotlin")
                value.set("dev.mayaqq.estrogen.compat.rei.ReiEstrogenPlugin")
            }
            entrypoint("jei_mod_plugin") {
                adapter.set("kotlin")
                value.set("dev.mayaqq.estrogen.compat.jei.JeiEstrogenPlugin")
            }
        }
    }

    forge {
        mixins.from(file("src/main/estrogen.mixins.json"))
        accessWideners.from(file("src/main/estrogen.accessWidener"))

        loaderVersion = libs.versions.forge.get()
        minecraftVersion = libs.versions.minecraft.get()

        include(libs.forge.baubly) { exclude(group = "me.shedaniel") }
        include(libs.forge.mixinExtras)
        include(libs.forge.kritter)
        include(libs.mixinConstrains)

        metadata {
            blurLogo = false
            modProperty("catalogueItemIcon", "estrogen:estrogen_pill")
            modProperty("catalogueBackground", "estrogen_background.png")
        }

        runs {
            client()
            server()
        }

        dependencies {
            api(libs.forge.kotlin)
            modCompileOnlyApi(libs.forge.flywheel.api)
            modImplementation(libs.forge.flywheel)
            modImplementation(libs.forge.baubly) { exclude(group = "me.shedaniel") }
            modCompileOnly(libs.forge.rei)
            implementation(libs.forge.mixinExtras)
            compileOnlyApi(libs.forge.jei)
            modCompileOnly(libs.forge.emi)
            modImplementation(libs.forge.cynosure)
            modImplementation(libs.forge.kritter)
            modApi(libs.forge.kittyconfig)

            when(item_viewer) {
                "EMI" -> modRuntimeOnly(libs.forge.emi)
                "REI" -> modRuntimeOnly(libs.forge.rei)
                "JEI" -> modRuntimeOnly(libs.forge.jei)
                "disabled" -> {}
                else -> error("Invalid item viewer for Forge: $item_viewer")
            }

            if (devauth_enabled.toBoolean()) modRuntimeOnly(libs.forge.devauth)
        }
    }
}

/*
val fixedAttribute = Attribute.of("fixed-jar", Boolean::class.javaObjectType)

dependencies {
    registerTransform(FixMultiRelease::class) {
        from.attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, ArtifactTypeDefinition.JAR_TYPE).attribute(fixedAttribute, false)
        to.attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, ArtifactTypeDefinition.JAR_TYPE).attribute(fixedAttribute, true)
    }

    artifactTypes {
        named(ArtifactTypeDefinition.JAR_TYPE) {
            attributes.attribute(fixedAttribute, false)
        }
    }
}

configurations.named("forgeRuntimeClasspath") {
    attributes {
        attribute(fixedAttribute, true)
    }
}
*/

java {
    withSourcesJar()
}

tasks.withType<KotlinCompile> {
//    explicitApiMode = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Warning
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_0
        //freeCompilerArgs = listOf("-Xmulti-platform", "-Xno-check-actual", "-Xexpect-actual-classes")
    }
}

tasks.named("createCommonApiStub", GenerateStubApi::class) {
    excludes.add(libs.kritter.get().group)
    excludes.add(libs.cynosure.get().group)
}

tasks.named { it == "accessWidenForgeMinecraft" }.all {

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
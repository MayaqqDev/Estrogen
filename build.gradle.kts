@file:Suppress("PropertyName", "UnstableApiUsage")

import dev.mayaqq.multijarfixer.FixMultiRelease
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put
import net.msrandom.minecraftcodev.core.utils.toPath
import net.msrandom.stubs.GenerateStubApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlin.io.path.absolutePathString

plugins {
    alias(libs.plugins.modpublish)
    alias(libs.plugins.cloche)
    kotlin("jvm") version libs.versions.kotlin
    kotlin("plugin.serialization") version libs.versions.kotlin
    // Need to explicitly set ksp versions cs cloche loads an old version by default
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
    `maven-publish`
}

repositories {
    cloche {
        librariesMinecraft()
        mavenNeoforged()
        mavenForge()
        mavenFabric()
        mavenNeoforgedMeta()
        mavenParchment()
    }
    maven(url = "https://maven.terraformersmc.com/releases/") { name = "TerraformersMC" }
    maven(url = "https://thedarkcolour.github.io/KotlinForForge/") { name = "KotlinForForge" }
    maven(url = "https://maven.teamresourceful.com/repository/maven-public/") { name = "Team Resourceful" }
    maven(url = "https://maven.shedaniel.me") { name = "Shedaniel" }
    maven(url = "https://maven.blamejared.com/") { name = "Blamejared" }
    maven(url = "https://maven.createmod.net/") { name = "Create" }
    maven(url = "https://maven.tterrag.com") { name = "Tterrag" }
    maven(url = "https://repo.nyon.dev/releases") { name = "KotlinLangForge" }
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
    mavenCentral()
    mavenLocal()
}

val item_viewer: String by project
val mod_name: String by project
val modVersion = providers.gradleProperty("version").get()
val devauth_enabled: String by project

cloche {
    metadata {
        modId = "estrogen"
        name = "Estrogen"
        description = "A mod adding Estrogen, Dashing, fluid handling mechanics and much more!"
        license = "LGPL-3.0"
        icon = "icon.png"
        url = "https://github.com/MayaqqDev/Estrogen"
        sources = "https://github.com/MayaqqDev/Estrogen"
        author("Mayaqq")
        contributor("https://github.com/MayaqqDev/Estrogen/wiki/Credits")

        dependency {
            modId = "cynosure"
            version {
                start = "1.0.0"
            }
        }
    }

    mappings {
        official()
        parchment(libs.versions.parchment)
    }

    common {
        mixins.from(file("src/main/estrogen.mixins.json"))

        accessWideners.from(file("src/main/estrogen.accesswidener"))

        dependencies {
            compileOnly(libs.mixin)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            api(libs.flywheel.api)
            modCompileOnly(libs.ears)
            modCompileOnly(libs.figura)
            modCompileOnly(libs.createNewAge)
            implementation(libs.mixinExtras)
            annotationProcessor(libs.mixinExtras)
            implementation(libs.cosmetics)

            modImplementation(libs.cynosure)

            localRuntime(libs.tca)
        }
    }

    fabric {
        mixins.from(file("src/fabric/estrogen-fabric.mixins.json"))

        loaderVersion = libs.versions.fabric
        minecraftVersion = libs.versions.minecraft


        includedClient() // includedClient() is not a run
        runs {
            client()
            server {
                runDir("runServer")
            }
            data {
                jvmArgs("-Dfabric-api.datagen.output-dir=${file("build/generated/resources/main")}")
                jvmArgs("-Destrogen.datagen.fabric-output-dir=${file("build/generated/resources/fabric")}")
                jvmArgs("-Destrogen.datagen.neoforge-output-dir=${file("build/generated/resources/neoforge")}")
            }
        }

        data {
            this.withMetadataJson {
                 this.withElement {
                     return@withElement buildJsonObject {
                         this@withElement.forEach { this.put(it.key,it.value) }
                         val newEntrypoints = buildJsonObject {
                             this@withElement["entrypoints"]!!.jsonObject.forEach { this.put(it.key,it.value) }
                             put("fabric-datagen", buildJsonArray {
                                 add(buildJsonObject {
                                     put("adapter","kotlin")
                                     put("value","dev.mayaqq.estrogen.datagen.EstrogenDatagen")
                                 })
                             })
                         }
                         put("entrypoints",newEntrypoints)
                     }
                 }
            }
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
                custom("cynosure:datapacks", listOf("vanillamode"))
            }

            dependency {
                modId = "fabric-api"
            }
            dependency {
                modId = "fabric-language-kotlin"
            }
            dependency {
                modId = "trinkets"
            }
        }

        dependencies {
            fabricApi(libs.versions.fapi)
            modApi(libs.fabric.kotlin)
            modApi.bundle(libs.bundles.fabric.cardinalComponents)
            modImplementation(libs.fabric.trinkets)
            modCompileOnly(libs.fabric.emi)
            modCompileOnly(libs.fabric.rei)
            modCompileOnly(libs.fabric.jei)
            modImplementation(libs.fabric.modmenu)
            modCompileOnly(libs.fabric.iris)
            modCompileOnlyApi(libs.fabric.flywheel.api)
            modImplementation(libs.fabric.flywheel)
            modImplementation(libs.fabric.kritter)
            modImplementation(libs.fabric.rlib)
            modImplementation(libs.fabric.csr)

            localRuntime(libs.jcpp)
            localRuntime(libs.glsltransformer)

            include(libs.fabric.rlib) { exclude(group = "com.teamresourceful", module = "bytecodecs") }
            include(libs.fabric.flywheel) { isTransitive = false }
            include(libs.fabric.csr) { isTransitive = false }
            include(libs.cosmetics)

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
            entrypoint("modmenu") {
                adapter.set("kotlin")
                value.set("dev.mayaqq.estrogen.fabric.compat.ModMenuIntegration")
            }
            entrypoint("estrogen") {
                adapter.set("kotlin")
                value.set("dev.mayaqq.estrogen.Estrogen")
            }
            entrypoint("crv") {
                adapter.set("kotlin")
                value.set("dev.mayaqq.estrogen.compat.recipeviewers.EstrogenRecipeViewerPlugin")
            }
        }
    }

    neoforge {
        mixins.from(file("src/forge/estrogen-forge.mixins.json"))

        loaderVersion = libs.versions.neoforge.get()
        minecraftVersion = libs.versions.minecraft.get()

        datagenDirectory.set(file("build/generated/resources/neoforge"))

        metadata {
            modLoader = "kotlinforforge"
            loaderVersion("5.0")
            blurLogo = false
            modProperty("catalogueItemIcon", "estrogen:estrogen_pill")
            modProperty("catalogueBackground", "estrogen_background.png")
            modProperty("cynosure:datapacks", listOf("vanillamode"))

            dependency {
                modId = "curios"
            }
        }

        runs {
            client {
            }
            server {
                runDir("runServer")
                jvmArgs("--nogui")
            }
            data() // NEEDED FOR GENERATED DATA TO ATTACH ON FORGE! SCREAM AT ASHLEY FOR THIS
        }

        data()

        dependencies {
            api(libs.forge.kotlin)
            modCompileOnlyApi(libs.forge.flywheel.api)
            modImplementation(libs.forge.flywheel)
            modCompileOnly(libs.forge.rei)
            implementation(libs.forge.mixinExtras)
            compileOnlyApi(libs.forge.jei)
            modCompileOnly(libs.forge.emi)
            modImplementation(libs.forge.kritter)
            modCompileOnly(libs.forge.oculus)
            legacyClasspath(libs.cosmetics)
            modImplementation(libs.forge.rlib)
            modImplementation(libs.forge.csr)
            modCompileOnlyApi(libs.forge.curios.api())

            include(libs.forge.rlib) { exclude(group = "com.teamresourceful", module = "bytecodecs") }
            include(libs.forge.mixinExtras) { isTransitive = false }
            include(libs.forge.flywheel) { isTransitive = false }
            include(libs.forge.csr) { isTransitive = false }
            include(libs.cosmetics)

            modRuntimeOnly(libs.forge.curios)

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

// Fix Forge attributes (remove when?)
/* Not needed anymore?
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

// Java ags
java {
    withSourcesJar()
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

// Kotlin args
kotlin {
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_0
        freeCompilerArgs = listOf("-Xmulti-platform", "-Xno-check-actual", "-Xexpect-actual-classes")
    }
    jvmToolchain(21)
}

// Remove Kotlin dependencies from common stub
/*
tasks.named("createCommonApiStub", GenerateStubApi::class) {
    excludes.add(libs.kritter.get().group)
    excludes.add(libs.cynosure.get().group)
}
 */

// Lemme just disable compiling java to fix issues
tasks.compileJava {
    enabled = false
}
tasks.compileKotlin {
    enabled = false
}

// Disable Forge Datagen, needed for cloche to take in the paths but don't want it to override the fabric generated files
tasks.named("runNeoforgeData") {
    enabled = false
}

minecraftRuns.configureEach {
    jvmArgs("-Dlog4j.configurationFile=\"${project.layout.projectDirectory.file("gradle/log4j.config.xml").toPath().absolutePathString()}\"")
}

fun Provider<MinimalExternalModuleDependency>.api(): String {
    return "${this.get().module}:${this.get().version}:api"
}

// Publishing
publishing {
    publications {
        create<MavenPublication>("mod") {
            from(components["java"])
        }
    }

    repositories {
        val username = System.getenv("MAVEN_USERNAME")
        val password = System.getenv(("MAVEN_PASSWORD"))
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

// Platform Publishing
publishMods {
    val loaders = arrayOf(
        PublishMetadata(
            "Fabric",
            arrayOf("fabric", "quilt"),
            arrayOf("cynosure", "trinkets"),
            cloche.targets["fabric"].finalJar.flatMap(Jar::getArchiveFile),
            "-fabric"
        ),
        PublishMetadata(
            "Neoforge",
            arrayOf("neoforge"),
            arrayOf("cynosure", "curios"),
            cloche.targets["neoforge"].finalJar.flatMap(Jar::getArchiveFile),
            "-neoforge"
        )
    )
    val mcVersion = "1.21.1"
    changelog = file("CHANGELOG.md").readText().replace("@VERSION@", modVersion)
    type = ALPHA

    val optionsCurseforge = curseforgeOptions {
        accessToken = System.getenv("CURSEFORGE_TOKEN")
        minecraftVersions.add(mcVersion)
        projectId = "850410"
        javaVersions.add(JavaVersion.VERSION_21)
        clientRequired = true
        serverRequired = true
    }

    val optionsModrinth = modrinthOptions {
        accessToken = System.getenv("MODRINTH_TOKEN")
        projectId = "HhIJW8n1"
        minecraftVersions.add(mcVersion)
    }

    loaders.forEach { loader ->
        loader.apply {
            curseforge("curseforge$loaderName") {
                from(optionsCurseforge)
                modLoaders.addAll(*modloaders)
                file = jar
                displayName = "$mod_name $modVersion $loaderName"
                version = "$modVersion$suffix"
                requires(*requires)
            }

            modrinth("modrinth$loaderName") {
                from(optionsModrinth)
                modLoaders.addAll(*modloaders)
                file = jar
                displayName = "$mod_name $modVersion $loaderName"
                version = "$modVersion$suffix"
                requires(*requires)
            }
        }
    }
}

class PublishMetadata(val loaderName: String, val modloaders: Array<String>, val requires: Array<String>, val jar: Provider<RegularFile>, val suffix: String)
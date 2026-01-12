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
    alias(libs.plugins.kittyconfig)
    // Need to explicitly set ksp versions cs cloche loads an old version by default
    id("com.google.devtools.ksp") version "2.2.10-2.0.2"
    id("dev.isxander.secrets") version "0.1.0"
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
                start = "0.1.12"
            }
        }
        dependency {
            modId = "kittyconfig"
        }
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
            modCompileOnly(libs.ears)
            modCompileOnly(libs.figura)
            modCompileOnly(libs.createNewAge)
            modImplementation(libs.kittyconfig)
            implementation(libs.mixinExtras)
            annotationProcessor(libs.mixinExtras)
            implementation(libs.cosmetics)

            modCompileOnly(libs.kritter)
            modImplementation(libs.cynosure)

            localRuntime("net.minecrell:terminalconsoleappender:1.3.0")
        }
    }

    fabric {
        mixins.from(file("src/main/estrogen.mixins.json"), file("src/fabric/estrogen-fabric.mixins.json"))
        accessWideners.from(file("src/main/estrogen.accessWidener"))

        loaderVersion = libs.versions.fabric
        minecraftVersion = libs.versions.minecraft


        includedClient() // includedClient() is not a run
        runs {
            client {
                jvmArgs("-Dlog4j.configurationFile=\"${project.layout.projectDirectory.file("gradle/log4j.config.xml").toPath().absolutePathString()}\"")
            }
            server {
                runDir("runServer")
                jvmArgs("-Dlog4j.configurationFile=\"${project.layout.projectDirectory.file("gradle/log4j.config.xml").toPath().absolutePathString()}\"")
            }
            data {
                jvmArgs("-Dfabric-api.datagen.output-dir=${file("build/generated/resources/main")}")
                jvmArgs("-Destrogen.datagen.fabric-output-dir=${file("build/generated/resources/fabric")}")
                jvmArgs("-Destrogen.datagen.forge-output-dir=${file("build/generated/resources/forge")}")
                jvmArgs("-Dlog4j.configurationFile=\"${project.layout.projectDirectory.file("gradle/log4j.config.xml").toPath().absolutePathString()}\"")
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

        withMetadataJson {
            withElement {
                buildJsonObject {
                    this@withElement.forEach { put(it.key, it.value) }
                    put("accessWidener", "estrogen.accessWidener")
                }
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
            //modCompileOnly(libs.fabric.cobblemon)
            modCompileOnlyApi(libs.fabric.flywheel.api)
            modImplementation(libs.fabric.flywheel)
            //modImplementation(libs.fabric.cynosure)
            modImplementation(libs.fabric.kritter)
            modApi(libs.fabric.botarium)

            localRuntime("org.anarres:jcpp:1.4.14")
            localRuntime("io.github.douira:glsl-transformer:2.0.1")

            include(libs.fabric.baubly) { exclude(group = "me.shedaniel"); isTransitive = false }
            include(libs.fabric.flywheel) { isTransitive = false }
            include(libs.fabric.botarium) { isTransitive = false }
            include(libs.cosmetics)
            include(libs.kittyconfig) {
                isTransitive = false
                artifact {
                    classifier = "fabric-1.20.1"
                }
            }

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

    forge {
        mixins.from(file("src/main/estrogen.mixins.json"), file("src/forge/estrogen-forge.mixins.json"))
        accessWideners.from(file("src/main/estrogen.accessWidener"))

        loaderVersion = libs.versions.forge.get()
        minecraftVersion = libs.versions.minecraft.get()

        datagenDirectory.set(file("build/generated/resources/forge"))

        metadata {
            modLoader = "kotlinforforge"
            loaderVersion("4.0")
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
                jvmArgs("-Dlog4j.configurationFile=\"${project.layout.projectDirectory.file("gradle/log4j.config.xml").toPath().absolutePathString()}\"")
            }
            server {
                runDir("runServer")
                jvmArgs("--nogui")
                jvmArgs("-Dlog4j.configurationFile=\"${project.layout.projectDirectory.file("gradle/log4j.config.xml").toPath().absolutePathString()}\"")
            }
            data() // NEEDED FOR GENERATED DATA TO ATTACH ON FORGE! SCREAM AT ASHLEY FOR THIS
        }

        data()

        dependencies {
            api(libs.forge.kotlin)
            modCompileOnlyApi(libs.forge.flywheel.api)
            modImplementation(libs.forge.flywheel)
            modImplementation(libs.forge.baubly) { exclude(group = "me.shedaniel") }
            modCompileOnly(libs.forge.rei)
            implementation(libs.forge.mixinExtras)
            compileOnlyApi(libs.forge.jei)
            modCompileOnly(libs.forge.emi)
            //modCompileOnly(libs.forge.cobblemon)
            //modImplementation(libs.forge.cynosure)
            modImplementation(libs.forge.kritter)
            modApi(libs.forge.botarium)
            modCompileOnly(libs.forge.oculus)
            legacyClasspath(libs.cosmetics)

            include(libs.forge.baubly) { exclude(group = "me.shedaniel"); isTransitive = false }
            include(libs.forge.mixinExtras) { isTransitive = false }
            include(libs.forge.flywheel) { isTransitive = false }
            include(libs.forge.botarium) { isTransitive = false }
            include(libs.cosmetics)
            include(libs.kittyconfig) {
                isTransitive = false
                artifact {
                    classifier = "forge-1.20.1"
                }
            }

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

java {
    withSourcesJar()
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_0
        freeCompilerArgs = listOf("-Xmulti-platform", "-Xno-check-actual", "-Xexpect-actual-classes")
    }
    jvmToolchain(17)
}

tasks.withType<KotlinCompile> {
//    explicitApiMode = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Warning
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_0
        freeCompilerArgs = listOf("-Xmulti-platform", "-Xno-check-actual", "-Xexpect-actual-classes")
    }
}

tasks.named("createCommonApiStub", GenerateStubApi::class) {
    excludes.add(libs.kritter.get().group)
    excludes.add(libs.cynosure.get().group)
    excludes.add(libs.kittyconfig.get().group)
}

//Lemme just disable compiling java to fix issues
tasks.compileJava {
    enabled = false
}
tasks.compileKotlin {
    enabled = false
}

publishing {
    publications {
        create<MavenPublication>("mod") {
            from(components["java"])
        }
    }

    repositories {
        val username = try { onePassword["op://nmnrp3mc2nkriiiwwk4f7q73jm/Sappho Maven/username"] } catch (_: Exception) { null }
        val password = try { onePassword["op://nmnrp3mc2nkriiiwwk4f7q73jm/Sappho Maven/password"] } catch (_: Exception) { null }
        if (username != null && password != null) {
                maven("https://maven.is-immensely.gay/${properties["maven_category"]}") {
                name = "sapphoCompany"
                credentials {
                    this.username = username.get()
                    this.password = password.get()
                }
            }
        } else {
            println("Sappho Company credentials not present.")
        }
    }
}

tasks.named("runForgeData") {
    enabled = false
}


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
            "Forge",
            arrayOf("forge"),
            arrayOf("cynosure", "curios"),
            cloche.targets["forge"].finalJar.flatMap(Jar::getArchiveFile),
            "-forge"
        )
    )
    val mcVersion = "1.20.1"
    changelog = file("CHANGELOG.md").readText().replace("@VERSION@", modVersion)
    type = ALPHA

    val optionsCurseforge = curseforgeOptions {
        accessToken = onePassword["op://nmnrp3mc2nkriiiwwk4f7q73jm/Curseforge/Mod Publish Api Token"]
        minecraftVersions.add(mcVersion)
        projectId = "850410"
        javaVersions.add(JavaVersion.VERSION_17)
        clientRequired = true
        serverRequired = true
    }

    val optionsModrinth = modrinthOptions {
        accessToken = onePassword["op://nmnrp3mc2nkriiiwwk4f7q73jm/Modrinth/Mod Publish Api Token"]
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
@file:Suppress("PropertyName", "UnstableApiUsage")

import dev.mayaqq.multijarfixer.FixMultiRelease
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put
import net.msrandom.minecraftcodev.core.utils.toPath
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
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
    maven(url = "https://libraries.minecraft.net/") // Gotta be on top to download correct libraries on macos :ioa:
    cloche {
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
            /*version {
                start = "0.1.15"
            }*/
        }
    }

    val root = common {
        mixins.from(file("src/main/estrogen.mixins.json"))
        accessWideners.from(file("src/main/estrogen.accessWidener"))

        dependencies {
            compileOnly(libs.mixin)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            modCompileOnly(libs.ears)
            modCompileOnly(libs.figura)
            modCompileOnly(libs.createNewAge)
            implementation(libs.mixinExtras)
            annotationProcessor(libs.mixinExtras)
            implementation(libs.cosmetics)

            modImplementation(libs.cynosure)

            localRuntime("net.minecrell:terminalconsoleappender:1.3.0")
        }
    }

    val fabricCommon = common("common:fabric") {
        dependsOn(root)
        mixins.from(file("src/common/fabric/main/estrogen.fabric.mixins.json"))

        dependencies {
            modApi(libs.fabric.kotlin)
            modCompileOnly(libs.fabric.emi.get1201())
            modCompileOnly(libs.fabric.rei.get1201())
            modCompileOnly(libs.fabric.jei.get1201())
            modCompileOnly(libs.fabric.iris)

            include(libs.cosmetics)
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
    }

    val forgeLike = common("common:forgeLike") {
        dependsOn(root)
        mixins.from(file("src/common/forgeLike/main/estrogen.forge.mixins.json"))

        metadata {
            dependency {
                modId = "curios"
            }
        }
    }

    val common1201 = common("common:1.20.1") {
        dependsOn(root)
        mixins.from(file("src/common/1.20.1/main/estrogen-1.20.1.mixins.json"))

        dependencies {
            api(libs.flywheel.api.get1201())
        }
    }

    val common1211 = common("common:1.21.1") {
        dependsOn(root)
        mixins.from(file("src/common/1.21.1/main/estrogen-1.21.1.mixins.json"))

        dependencies {
            api(libs.flywheel.api.get1211())
        }
    }

    fabric("fabric:1.20.1") {
        dependsOn(common1201, fabricCommon)
        mixins.from(file("src/fabric/estrogen-fabric-1.20.1.mixins.json"))

        loaderVersion = libs.versions.fabric
        minecraftVersion = libs.versions.minecraft.get1201()

        mappings {
            official()
            parchment(libs.versions.parchment.get1201())
        }


        includedClient() // includedClient() is not a run
        runs {
            client {
            }
            server {
                runDir("runServer")
            }
            data {
                jvmArgs("-Dfabric-api.datagen.output-dir=${file("build/generated/resources/main")}")
                jvmArgs("-Destrogen.datagen.fabric-output-dir=${file("build/generated/resources/fabric")}")
                jvmArgs("-Destrogen.datagen.forge-output-dir=${file("build/generated/resources/forge")}")
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
                         put("entrypoints", newEntrypoints)
                     }
                 }
            }
        }

        dependencies {
            fabricApi(libs.versions.fapi.get1201())
            modApi.bundle(libs.bundles.fabric.cardinalComponents.get1201())
            modImplementation(libs.fabric.trinkets.get1201())
            modImplementation(libs.fabric.modmenu.get1201())
            //modImplementation(libs.fabric.cynosure)
            modImplementation(libs.fabric.kritter.get1201())
            modCompileOnlyApi(libs.fabric.flywheel.api.get1201())
            modImplementation(libs.fabric.flywheel.get1201())

            localRuntime("org.anarres:jcpp:1.4.14")
            localRuntime("io.github.douira:glsl-transformer:2.0.1")

            include(libs.fabric.flywheel.get1201()) { isTransitive = false }

            when(item_viewer) {
                "REI" -> modRuntimeOnly(libs.fabric.rei.get1201()) { exclude(group = "net.fabricmc") }
                "EMI" -> modRuntimeOnly(libs.fabric.emi.get1201())
                "JEI" -> modRuntimeOnly(libs.fabric.jei.get1201())
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

    fabric("fabric:1.21.1") {
        dependsOn(common1211, fabricCommon)
        mixins.from(file("src/fabric/estrogen-fabric-1.21.1.mixins.json"))

        loaderVersion = libs.versions.fabric
        minecraftVersion = libs.versions.minecraft.get1211()

        mappings {
            official()
            parchment(libs.versions.parchment.get1211())
        }


        includedClient() // includedClient() is not a run
        runs {
            client {
            }
            server {
                runDir("runServer")
            }
            data()
        }

        data()

        dependencies {
            fabricApi(libs.versions.fapi.get1211())
            modApi.bundle(libs.bundles.fabric.cardinalComponents.get1211())
            modImplementation(libs.fabric.trinkets.get1211())
            modImplementation(libs.fabric.modmenu.get1211())
            //modImplementation(libs.fabric.cynosure)
            modImplementation(libs.fabric.kritter.get1211())
            modCompileOnlyApi(libs.fabric.flywheel.api.get1211())
            modImplementation(libs.fabric.flywheel.get1211())

            localRuntime("org.anarres:jcpp:1.4.14")
            localRuntime("io.github.douira:glsl-transformer:2.0.1")

            include(libs.fabric.flywheel.get1211()) { isTransitive = false }

            when(item_viewer) {
                "REI" -> modRuntimeOnly(libs.fabric.rei.get1211()) { exclude(group = "net.fabricmc") }
                "EMI" -> modRuntimeOnly(libs.fabric.emi.get1211())
                "JEI" -> modRuntimeOnly(libs.fabric.jei.get1211())
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

    forge("forge:1.20.1") {
        dependsOn(common1201, forgeLike)
        mixins.from(file("src/forge/estrogen-forge-1.20.1.mixins.json"))

        loaderVersion = libs.versions.forge.get()
        minecraftVersion = libs.versions.minecraft.get1201().get()

        datagenDirectory.set(file("build/generated/resources/forge"))

        mappings {
            official()
            parchment(libs.versions.parchment.get1201())
        }

        metadata {
            modLoader = "kotlinforforge"
            loaderVersion("4.0")
            blurLogo = false
            modProperty("catalogueItemIcon", "estrogen:estrogen_pill")
            modProperty("catalogueBackground", "estrogen_background.png")
            modProperty("cynosure:datapacks", listOf("vanillamode"))
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
            api(libs.forge.kotlin.get1201())
            modCompileOnlyApi(libs.forge.flywheel.api.get1201())
            modImplementation(libs.forge.flywheel.get1201())
            modCompileOnly(libs.forge.rei.get1201())
            implementation(libs.forge.mixinExtras)
            compileOnlyApi(libs.forge.jei.get1201())
            modCompileOnly(libs.forge.emi.get1201())
            modImplementation(skipIncludeTransformation(libs.forge.kritter.get1201()))
            modCompileOnly(libs.forge.oculus)

            legacyClasspath(libs.cosmetics)

            include(libs.forge.mixinExtras) { isTransitive = false }
            include(libs.forge.flywheel.get1201()) { isTransitive = false }

            include(libs.cosmetics)

            when(item_viewer) {
                "EMI" -> modRuntimeOnly(libs.forge.emi.get1201())
                "REI" -> modRuntimeOnly(libs.forge.rei.get1201())
                "JEI" -> modRuntimeOnly(libs.forge.jei.get1201())
                "disabled" -> {}
                else -> error("Invalid item viewer for Forge: $item_viewer")
            }

            if (devauth_enabled.toBoolean()) modRuntimeOnly(libs.forge.devauth)
        }
    }

    neoforge("neoforge:1.21.1") {
        dependsOn(common1211, forgeLike)
        mixins.from(file("src/forge/estrogen-neoforge-1.21.1.mixins.json"))

        loaderVersion = libs.versions.neoforge.get()
        minecraftVersion = libs.versions.minecraft.get1211().get()

        datagenDirectory.set(file("build/generated/resources/neoforge"))

        mappings {
            official()
            parchment(libs.versions.parchment.get1211())
        }

        metadata {
            modLoader = "kotlinforforge"
            loaderVersion("5.0")
            blurLogo = false
            modProperty("catalogueItemIcon", "estrogen:estrogen_pill")
            modProperty("catalogueBackground", "estrogen_background.png")
            modProperty("cynosure:datapacks", listOf("vanillamode"))
        }

        runs {
            client()
            server {
                runDir("runServer")
                jvmArgs("--nogui")
            }
            data() // NEEDED FOR GENERATED DATA TO ATTACH ON NEOFORGE! SCREAM AT ASHLEY FOR THIS
        }

        data()

        dependencies {
            api(libs.forge.kotlin.get1211())
            modCompileOnlyApi(libs.forge.flywheel.api.get1211())
            modImplementation(libs.forge.flywheel.get1211())
            //modImplementation(libs.forge.baubly.get1211() { exclude(group = "me.shedaniel") }
            modCompileOnly(libs.forge.rei.get1211())
            implementation(libs.forge.mixinExtras)
            compileOnlyApi(libs.forge.jei.get1211())
            modCompileOnly(libs.forge.emi.get1211())
            modImplementation(skipIncludeTransformation(libs.forge.kritter.get1211()))
            modCompileOnly(libs.forge.oculus)

            legacyClasspath(libs.cosmetics)

            include(libs.forge.mixinExtras) { isTransitive = false }
            include(libs.forge.flywheel.get1211()) { isTransitive = false }

            include(libs.cosmetics)

            when(item_viewer) {
                "EMI" -> modRuntimeOnly(libs.forge.emi.get1211())
                "REI" -> modRuntimeOnly(libs.forge.rei.get1211())
                "JEI" -> modRuntimeOnly(libs.forge.jei.get1211())
                "disabled" -> {}
                else -> error("Invalid item viewer for NeoForge: $item_viewer")
            }

            if (devauth_enabled.toBoolean()) modRuntimeOnly(libs.forge.devauth)
        }
    }
}

// Fix Forge attributes (remove when?)
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

/*
configurations.named("forge1201RuntimeClasspath") {
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
/* TODO: Commented these out for now, maybe still needed
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
/*
tasks.named("runForgeData") {
    enabled = false
}
 */

minecraftRuns.configureEach {
    jvmArgs("-Dlog4j.configurationFile=\"${project.layout.projectDirectory.file("gradle/log4j.config.xml").toPath().absolutePathString()}\"")
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
            "-fabric",
            arrayOf(McVersions.MC1201, McVersions.MC1211)
        ) { cloche.targets["fabric:$it"].finalJar.flatMap(Jar::getArchiveFile) },
        PublishMetadata(
            "Forge",
            arrayOf("forge"),
            arrayOf("cynosure", "curios"),
            "-forge",
            arrayOf(McVersions.MC1201)
        ) { cloche.targets["forge:$it"].finalJar.flatMap(Jar::getArchiveFile) },
        PublishMetadata(
            "NeoForge",
            arrayOf("neoforge"),
            arrayOf("cynosure", "curios"),
            "-neoforge",
            arrayOf(McVersions.MC1211)
        ) { cloche.targets["neoforge:$it"].finalJar.flatMap(Jar::getArchiveFile) }
    )
    type = ALPHA

    val optionsCurseforge = curseforgeOptions {
        accessToken = System.getenv("CURSEFORGE_TOKEN")
        projectId = "850410"
        javaVersions.add(JavaVersion.VERSION_17)
        clientRequired = true
        serverRequired = true
    }

    val optionsModrinth = modrinthOptions {
        accessToken = System.getenv("MODRINTH_TOKEN")
        projectId = "HhIJW8n1"
    }

    loaders.forEach { loader ->
        loader.apply {
            McVersions.entries.forEach { mcVersion ->
                changelog = file("CHANGELOG.md").readText().replace("@VERSION@", modVersion).replace("@MC_VERSION", mcVersion.version)
                if (mcVersion in this.supports) {
                    curseforge("curseforge$loaderName") {
                        minecraftVersions.add(mcVersion.version)
                        javaVersions.add(mcVersion.javaVersion)

                        from(optionsCurseforge)
                        modLoaders.addAll(*modloaders)
                        file = jar.invoke(mcVersion.version)
                        displayName = "$mod_name $modVersion ${mcVersion.version} $loaderName"
                        version = "$modVersion-${mcVersion.version}$suffix"
                        requires(*requires)
                    }

                    modrinth("modrinth$loaderName") {
                        minecraftVersions.add(mcVersion.version)
                        from(optionsModrinth)
                        modLoaders.addAll(*modloaders)
                        file = jar.invoke(mcVersion.version)
                        displayName = "$mod_name $modVersion ${mcVersion.version} $loaderName"
                        version = "$modVersion-${mcVersion.version}$suffix"
                        requires(*requires)
                    }
                }
            }
        }
    }
}

class PublishMetadata(
    val loaderName: String,
    val modloaders: Array<String>,
    val requires: Array<String>,
    val suffix: String,
    val supports: Array<McVersions>,
    val jar: (String) -> Provider<RegularFile>

    )

enum class McVersions(val version: String, val javaVersion: JavaVersion) {
    MC1201("1.20.1", JavaVersion.VERSION_17),
    MC1211("1.21.1", JavaVersion.VERSION_21)
}
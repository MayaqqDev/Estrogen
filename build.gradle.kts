import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.7-SNAPSHOT" apply false
    id("me.modmuss50.mod-publish-plugin") version "0.4.5"
    id("maven-publish")
    id("java")
}

// A task to build both, Forge and Fabric
tasks.register("buildBoth") {
    dependsOn(project(":fabric").tasks.named("buildBothFabric"))
    dependsOn(project(":forge").tasks.named("buildBothForge"))
}

architectury {
    minecraft = project.property("minecraft_version") as String
}

subprojects {
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "maven-publish")

    val modLoader = project.property("name") as String
    val modId = project.property("modid") as String
    val isCommon = modLoader == "common"
    val minecraft_version = project.property("minecraft_version") as String

    configure<LoomGradleExtensionAPI> {
        silentMojangMappingsLicense()
    }

    dependencies {
        "minecraft"("com.mojang:minecraft:${minecraft_version}")
        "mappings"(project.the<LoomGradleExtensionAPI>().layered() {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-${minecraft_version}:${project.property("parchmentmc_version")}@zip")
        })

        "modApi"("com.teamresourceful.resourcefullib:resourcefullib-${name}-${minecraft_version}:${project.property("resourcefullib_version")}")
        "modApi"("earth.terrarium.botarium:botarium-${name}-${minecraft_version}:${project.property("botarium_version")}")
        "include"("modApi"("uwu.serenity.critter:critter-${name}:${project.property("critter_version")}")!!)

        "include"("modImplementation"("maven.modrinth:jukeboxfix:${project.property("jukeboxfix_version")}-${minecraft_version}")!!)

        // JEI
        "modCompileOnly"("mezz.jei:jei-${minecraft_version}-common-api:${project.property("jei_version")}")

        "include"("implementation"("com.teamresourceful:resourceful-cosmetics-4j:${project.property("resourceful_cosmetics_version")}")!!)
    }

    if (!isCommon) {
        publishing {
            publications {
                create<MavenPublication>("mavenJava") {
                    artifactId = "Estrogen-${modLoader}"
                    from(components["java"])

                    pom {
                        name.set("Estrogen ${modLoader}")
                        url.set("https://github.com/MayaqqDev/Estrogen")

                        scm {
                            connection.set("git:https://github.com/MayaqqDev/Estrogen.git")
                            developerConnection.set("git:https://github.com/MayaqqDev/Estrogen.git")
                            url.set("https://github.com/MayaqqDev/Estrogen")
                        }

                        licenses {
                            license {
                                name.set("LGPL-3.0")
                                url.set("https://www.gnu.org/licenses/lgpl-3.0.en.html")
                            }
                        }
                    }
                }
            }
            repositories {
                maven {
                    name = "sapphoCompany"
                    url = uri("https://maven.is-immensely.gay/releases")
                    credentials {
                        username = project.findProperty("mavenUser") as String?
                        password = project.findProperty("mavenPassword") as String?
                    }
                    authentication {
                        create<BasicAuthentication>("basic")
                    }
                }
            }
        }
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "architectury-plugin")
    apply(plugin = "maven-publish")

    val archivesBaseName = project.property("archives_base_name") as String
    version = project.property("mod_version") as String
    group = project.property("maven_group") as String

    // repositories for all the build.gradle files, makes it a lot easier to manage
    // Format is: name: name of the maven, description: mods that use this maven, url: the url of the maven
    repositories {
        maven { name = "Terraformers Maven"; description = "EMI & Mod Menu"; url = uri("https://maven.terraformersmc.com/") }
        maven { name = "Shedaniel Maven"; description = "REI"; url = uri("https://maven.shedaniel.me") }
        maven { name = "Blamejared Maven"; description = "JEI"; url = uri("https://maven.blamejared.com/") }
        maven { name = "Tterrag Maven"; description = "Create, Forge Registrate & Forge Flywheel"; url = uri("https://maven.tterrag.com") }
        maven { name = "TheIllusivec4 Maven"; description = "Curios, CaelusAPI"; url = uri("https://maven.theillusivec4.top/") }
        maven { name = "Devos Maven"; description = "Create Fabric, Porting Lib, Forge Tags, Milk Lib & Fabric Registrate"; url = uri("https://mvn.devos.one/snapshots/") }
        maven { name = "Curseforge Maven"; description = "Forge Config API Port"; url = uri("https://cursemaven.com") }
        maven { name = "Cafeteria Maven"; description = "Fake Player API"; url = uri("https://maven.cafeteria.dev/releases") }
        maven { name = "JamiesWhiteShirt Maven"; description = "Reach Entity Attributes"; url = uri("https://maven.jamieswhiteshirt.com/libs-release") }
        maven { name = "Ladysnake Maven"; description = "Trinkets"; url = uri("https://maven.ladysnake.org/releases") }
        maven { name = "Unascribed Maven"; description = "Ears"; url = uri("https://repo.unascribed.com") }
        maven { name = "Modrinth Maven"; description = "Jukeboxfix, Ad Astra"; url = uri("https://api.modrinth.com/maven") }
        maven { name = "Resourceful Maven"; description = "Baubly, Botarium & ResourcefulLib"; url = uri("https://maven.teamresourceful.com/repository/maven-public/") }
        maven { name = "Figura Maven"; description = "Figura"; url = uri("https://maven.figuramc.org/releases") }
        maven { name = "Sappho Company"; description = "Critter"; url = uri("https://maven.is-immensely.gay/nightly") }
        maven { name = "DevAuth maven"; description = "DevAuth"; url = uri("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1") }
        maven { name = "Xander maven"; description = "YACL"; url = uri("https://maven.isxander.dev/releases") }
        maven { name = "ImpactDev Maven"; description = "Cobblemon"; url =  uri("https://maven.impactdev.net/repository/development/") }
        maven { name = "Jitpack maven"; description = "Mixin Extras & Fabric ASM"; url = uri("https://jitpack.io/") } //NOTE: LEAVE THIS AS LAST
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        withSourcesJar()
    }
}

/*
publishMods {

    val mod_version = project.property("mod_version") as String

    changelog = file("CHANGELOG.md").readText().replace("@VERSION@", mod_version)
    type = STABLE

    val optionsCurseforge = curseforgeOptions {
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        minecraftVersions.add("1.20.1")
        projectId = "850410"
        requires {
            slug = "botarium"
        }
        requires {
            slug = "resourceful-lib"
        }
        optional {
            slug = "ears"
        }
        embeds {
            slug = "jukeboxfix"
        }
    }

    val optionsModrinth = modrinthOptions {
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        projectId = "HhIJW8n1"
        minecraftVersions.add("1.20.1")
        requires {
            slug = "botarium"
        }
        requires {
            slug = "resourceful-lib"
        }
        optional {
            slug = "ears"
        }
        embeds {
            slug = "jukeboxfix"
        }
    }

    curseforge("curseforgeFabric") {
        from optionsCurseforge
        modLoaders.add("fabric")
        modLoaders.add("quilt")
        file = project(":fabric").tasks.named("remapJar").get().archiveFile
        displayName = "Create: Estrogen ${mod_version} Fabric"
        version = "${mod_version}-fabric"
        requires {
            slug = "create-fabric"
        }
        requires {
            slug = "trinkets"
        }
        optional {
            slug = "roughly-enough-items"
        }
        optional {
            slug = "emi"
        }
    }

    curseforge("curseforgeForge") {
        from optionsCurseforge
        modLoaders.add("forge")
        file = project(":forge").tasks.named("remapJar").get().archiveFile
        displayName = "Create: Estrogen ${mod_version} Forge"
        version = "${mod_version}-forge"
        requires {
            slug = "create"
        }
        requires {
            slug = "curios"
        }
        optional {
            slug = "jei"
        }
    }

    modrinth("modrinthFabric") {
        from optionsModrinth
        modLoaders.add("fabric")
        modLoaders.add("quilt")
        file = project(":fabric").tasks.named("remapJar").get().archiveFile
        displayName = "Create: Estrogen ${mod_version} Fabric"
        version = "${mod_version}-fabric"
        requires {
            slug = "create-fabric"
        }
        requires {
            slug = "trinkets"
        }
        optional {
            slug = "rei"
        }
        optional {
            slug = "emi"
        }
    }

    modrinth("modrinthForge") {
        from optionsModrinth
        modLoaders.add("forge")
        file = project(":forge").tasks.named("remapJar").get().archiveFile
        displayName = "Create: Estrogen ${mod_version} Forge"
        version = "${mod_version}-forge"
        requires {
            slug = "create"
        }
        requires {
            slug = "curios"
        }
        optional {
            slug = "jei"
        }
    }
} */

package dev.mayaqq.multijarfixer

import org.gradle.api.artifacts.transform.*
import org.gradle.api.file.FileSystemLocation
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.jar.JarInputStream
import java.util.jar.JarOutputStream
import java.util.jar.Manifest
import java.util.zip.ZipEntry


@CacheableTransform
abstract class FixMultiRelease : TransformAction<TransformParameters.None> {

    abstract val inputFile: Provider<FileSystemLocation>
        @InputArtifact
        @PathSensitive(PathSensitivity.NONE)
        get

    override
    fun transform(outputs: TransformOutputs) {
        val file = inputFile.get().asFile
        if (isMultiReleaseJar(file) != true) {
            outputs.file(file)
        } else {
            // copy jar but remove Multi-Release attribute
            val outputFile = outputs.file("${file.name.substring(0, file.name.lastIndexOf('.'))}-fixed.jar")

            JarInputStream(FileInputStream(file)).use { inputJar ->
                JarOutputStream(FileOutputStream(outputFile)).use { outputJar ->
                    var entry = inputJar.nextEntry
                    while (entry != null) {
                        if (entry.name.equals("META-INF/MANIFEST.MF", ignoreCase = true)) {
                            // Skip the manifest entry, we will handle it later
                            entry = inputJar.nextEntry
                            continue
                        }

                        outputJar.putNextEntry(entry)
                        inputJar.copyTo(outputJar)
                        entry = inputJar.nextEntry
                    }

                    val manifest = Manifest(inputJar.manifest)
                    manifest.mainAttributes.remove("Multi-Release")

                    outputJar.putNextEntry(ZipEntry("META-INF/MANIFEST.MF"))
                    manifest.write(outputJar)
                }
            }
        }
    }

    private fun isMultiReleaseJar(file: File): Boolean? = JarInputStream(FileInputStream(file)).use {
        if (it.manifest.mainAttributes.getValue("Multi-Release") == "true") {
            var entry = it.nextEntry
            while (entry != null) {
                if (entry.name.startsWith("META-INF/versions/")) {
                    return@use true
                }
                entry = it.nextEntry
            }
            return@use false
        }
        return@use null
    }
}
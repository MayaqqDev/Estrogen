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
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream


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

            ZipInputStream(FileInputStream(file)).use { inputJar ->
                ZipOutputStream(FileOutputStream(outputFile)).use { outputJar ->
                    var entry = inputJar.nextEntry
                    while (entry != null) {
                        if (entry.name.equals("META-INF/MANIFEST.MF", ignoreCase = true)) {
                            val data = String(inputJar.readBytes())
                                .lines()
                                .filterNot { it.startsWith("Multi-Release:") }
                                .joinToString("\n")

                            outputJar.putNextEntry(ZipEntry("META-INF/MANIFEST.MF"))
                            outputJar.write(data.toByteArray())
                            outputJar.closeEntry()
                            entry = inputJar.nextEntry
                        } else {
                            outputJar.putNextEntry(entry)
                            inputJar.copyTo(outputJar)
                            outputJar.closeEntry()
                            entry = inputJar.nextEntry
                        }
                    }
                }
            }
        }
    }

    private fun isMultiReleaseJar(file: File): Boolean? = JarInputStream(FileInputStream(file)).use {
        if (it.manifest?.mainAttributes?.getValue("Multi-Release") == "true") {
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
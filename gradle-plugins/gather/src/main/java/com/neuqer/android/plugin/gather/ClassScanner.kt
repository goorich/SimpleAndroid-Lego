package com.neuqer.android.plugin.gather

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.JarInput
import org.objectweb.asm.ClassReader
import java.io.FileInputStream
import java.io.InputStream
import java.util.zip.ZipFile

/**
 * 类扫描器
 *
 * @author techflowing
 * @since 2019-09-19 00:32
 */
class ClassScanner {

    /**
     * 扫描Dir和Jar
     */
    fun scan(dirInputList: MutableList<DirectoryInput>, jarInputList: MutableList<JarInput>): MutableList<String> {
        val classList: MutableList<String> = mutableListOf()

        dirInputList.forEach {
            val dir = it.file.absoluteFile
            dir.walk().maxDepth(Int.MAX_VALUE)
                    .filter { it.isFile }
                    .filter { it.extension == "class" }
                    .forEach { classFile ->
                        scanClass(FileInputStream(classFile), classFile.absolutePath, classList)
                    }

        }

        jarInputList.forEach {
            val jar = it.file.absoluteFile
            ZipFile(jar).use {
                it.entries().asSequence()
                        .filter { entry -> !entry.isDirectory }
                        .filter { entry -> entry.name.endsWith(".class") }
                        .forEach { zipEntry ->
                            scanClass(it.getInputStream(zipEntry), zipEntry.name, classList)
                        }
            }
        }
        classList.forEach {
            LogUtil.log("扫描到的类：$it")
        }
        return classList
    }


    private fun scanClass(inputStream: InputStream, classPath: String, result: MutableList<String>) {
        if (classPath.endsWith(".class")) {
            val classReader = ClassReader(inputStream)
            classReader.accept(ClassInfoVisitor { className ->
                if (className != null) {
                    result.add(className)
                }
            }, ClassReader.SKIP_CODE or ClassReader.SKIP_FRAMES)
        }
    }
}
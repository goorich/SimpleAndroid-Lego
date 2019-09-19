package com.neuqer.android.plugin.gather

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.google.common.collect.ImmutableSet
import javassist.*
import javassist.bytecode.ClassFile
import java.lang.IllegalStateException

/**
 * 搜集处理器
 *
 * @author techflowing
 * @since 2019-09-19 00:11
 */
class GatherProcessor(
        private val dirInputList: MutableList<DirectoryInput>,
        private val jarInputList: MutableList<JarInput>,
        private val transformOutputProvider: TransformOutputProvider) {

    /**ClassPool*/
    private val classPool = ClassPool(true)
    /**ModuleApplication Class 集合*/
    private var moduleAppClassList: MutableList<String> = mutableListOf()

    /**
     * 搜集入口
     */
    fun gather() {
        prepare()
        scanClass()
        verifyScannedClass()
        outputModuleListProviderClass()
        outputInput()
    }

    /**
     * 准备工作，添加ClassPool 搜索路径
     */
    private fun prepare() {
        dirInputList.forEach {
            classPool.insertClassPath(it.file.absolutePath)
        }
        jarInputList.forEach {
            classPool.insertClassPath(it.file.absolutePath)
        }
    }

    /**
     * 扫描Class
     */
    private fun scanClass() {
        moduleAppClassList = ClassScanner().scan(dirInputList, jarInputList)
    }

    /**
     * 校验扫描到 注解 ModuleApplication 的Class
     * 校验：是否继承自  AbsModuleApplication
     */
    private fun verifyScannedClass() {
        val absModuleApplicationClass = classPool.get(Constants.I_MODULE_APPLICATION)
        moduleAppClassList.forEach { className ->
            val ctClass = classPool.get(className)
            if (!ctClass.subtypeOf(absModuleApplicationClass)) {
                throw IllegalStateException("$className, @ModuleApplication 注解的类必须实现 IModuleApplication接口")
            }
        }
    }

    /**
     * 输出类
     */
    private fun outputModuleListProviderClass() {
        val moduleListProviderInterface = classPool.getCtClass(Constants.MODULE_LIST_PROVIDER)
        val listClass = classPool.getCtClass("java.util.List")
        val moduleListProviderClass = classPool.makeClass(Constants.MODULE_LIST_PROVIDER_IMPL)
        moduleListProviderClass.classFile.majorVersion = ClassFile.JAVA_7
        // 添加接口
        moduleListProviderClass.addInterface(moduleListProviderInterface)
        // 添加变量
        val listField = CtField(listClass, "list", moduleListProviderClass)
        listField.modifiers = Modifier.PRIVATE
        moduleListProviderClass.addField(listField)
        // 添加构造方法
        val construct = CtNewConstructor.defaultConstructor(moduleListProviderClass)
        construct.modifiers = Modifier.PUBLIC
        // 构造方法内容
        var constantBody = "{ list = new java.util.ArrayList();"
        moduleAppClassList.forEach {
            constantBody += " list.add(new $it());"
        }
        constantBody += "}"
        construct.setBody(constantBody)
        moduleListProviderClass.addConstructor(construct)
        // 添加 IModuleListProvider 的实现方法
        val methodBody = "return list;"
        val method = CtNewMethod.make(Modifier.PUBLIC, listClass, "getModuleList",
                null, null, methodBody, moduleListProviderClass)
        moduleListProviderClass.addMethod(method)

        // 输出
        val output = transformOutputProvider.getContentLocation("gather-generate",
                TransformManager.CONTENT_CLASS, ImmutableSet.of(QualifiedContent.Scope.PROJECT), Format.DIRECTORY)
        moduleListProviderClass.writeFile(output.path)
    }

    /**
     * 输出Dir 和 Jar
     */
    private fun outputInput() {
        dirInputList.forEach { input ->
            val output = transformOutputProvider.getContentLocation(input.name, input.contentTypes,
                    input.scopes, Format.DIRECTORY)
            FileUtils.copyDirectory(input.file, output)
        }

        jarInputList.forEach { input ->
            val output = transformOutputProvider.getContentLocation(input.name, input.contentTypes,
                    input.scopes, Format.JAR)
            FileUtils.copyFile(input.file, output)
        }
    }
}
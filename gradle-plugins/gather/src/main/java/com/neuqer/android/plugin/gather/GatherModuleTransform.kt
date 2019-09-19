package com.neuqer.android.plugin.gather

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.google.common.collect.ImmutableSet

/**
 * Transform
 *
 * @author techflowing
 * @since 2019-09-18 00:46
 */
class GatherModuleTransform : Transform() {

    override fun getName(): String {
        return "gather-transform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun transform(transformInvocation: TransformInvocation) {
        val startTime = System.currentTimeMillis()
        LogUtil.log("start")

        transformInvocation.outputProvider.deleteAll()

        val dirInputList: MutableList<DirectoryInput> = mutableListOf()
        val jarInputList: MutableList<JarInput> = mutableListOf()

        transformInvocation.inputs.forEach { input ->
            dirInputList.addAll(input.directoryInputs)
            jarInputList.addAll(input.jarInputs)
        }
        // 开始搜集
        GatherProcessor(dirInputList, jarInputList, transformInvocation.outputProvider).gather()

        LogUtil.log("end, cost time: " + (System.currentTimeMillis() - startTime))
    }
}
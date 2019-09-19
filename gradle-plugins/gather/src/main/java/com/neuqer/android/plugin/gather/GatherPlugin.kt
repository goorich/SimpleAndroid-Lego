package com.neuqer.android.plugin.gather

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * ModuleApplication 注解的类搜集插件
 *
 * @author techflowing
 * @since 2019-09-18 00:38
 */
class GatherPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        if (!project.plugins.hasPlugin(AppPlugin::class.java)) {
            throw  IllegalStateException("should apply plugin com.android.application")
        }
        project.extensions.getByType(AppExtension::class.java).registerTransform(GatherModuleTransform())
    }
}
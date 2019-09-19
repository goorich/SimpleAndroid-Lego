package com.neuqer.android.plugin.gather

import org.objectweb.asm.Opcodes

/**
 * 常量类
 *
 * @author techflowing
 * @since 2019-09-19 23:08
 */
class Constants {
    companion object {
        const val API = Opcodes.ASM5
        const val ANNOTATION_MODULE_APPLICATION_DESC = "Lcom/neuqer/android/annotation/ModuleApplication;"
        const val I_MODULE_APPLICATION = "com.neuqer.android.runtime.IModuleApplication"
        const val MODULE_LIST_PROVIDER = "com.neuqer.android.runtime.IModuleListProvider"
        const val MODULE_LIST_PROVIDER_IMPL = "com.neuqer.android.runtime.ModuleListProviderImpl"
    }
}
package com.neuqer.android.plugin.gather

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor

/**
 * ClassVisitor
 *
 * @author techflowing
 * @since 2019-09-19 23:07
 */
class ClassInfoVisitor(private val classVisitEnd: (String?) -> Unit) : ClassVisitor(Constants.API) {

    /**当前类名称*/
    private var className: String? = null
    /**当前类是否是 ModuleApplication 注解*/
    private var isModuleApplicationAnnotation: Boolean = false

    override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name?.replace("/", ".")
    }

    override fun visitAnnotation(desc: String?, visible: Boolean): AnnotationVisitor? {
        isModuleApplicationAnnotation = Constants.ANNOTATION_MODULE_APPLICATION_DESC == desc
        return super.visitAnnotation(desc, visible)
    }

    override fun visitEnd() {
        super.visitEnd()
        if (isModuleApplicationAnnotation) {
            classVisitEnd(className)
        }
    }
}
package com.neuqer.android.plugin.gather

/**
 * Log工具
 *
 * @author techflowing
 * @since 2019-09-18 23:42
 */
class LogUtil {

    companion object {
        private const val TAG = "Gradle-gather: "

        fun log(msg: String) {
            println(TAG + msg)
        }
    }


}
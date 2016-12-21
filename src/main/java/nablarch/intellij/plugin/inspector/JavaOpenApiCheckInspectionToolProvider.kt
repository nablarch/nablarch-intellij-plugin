package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*

/**
 * ブラックリストに定義されているJavaAPIを使用していないことをチェックする。
 */
class JavaOpenApiCheckInspectionToolProvider : InspectionToolProvider {
  override fun getInspectionClasses(): Array<Class<*>> {
    return arrayOf(JavaOpenApiCheckInspectionTool::class.java)
  }
}
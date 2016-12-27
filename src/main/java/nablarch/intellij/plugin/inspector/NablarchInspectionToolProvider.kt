package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*

/**
 * Nablarchフレームワークの非公開API、
 * およびブラックリストに定義されたJavaのAPIを使用していないことをチェックする。
 *
 * @author Naoki Yamamoto
 */
class NablarchInspectionToolProvider : InspectionToolProvider {

  override fun getInspectionClasses(): Array<Class<*>> {
    return arrayOf(PublishApiCheckInspectionTool::class.java, JavaOpenApiCheckInspectionTool::class.java)
  }
}


package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*

/**
 * Nablarchフレームワークの非公開API、
 * およびブラックリストに定義されたJavaAPIを使用していないことをチェックする。
 *
 * @author Naoki Yamamoto
 */
class NablarchInspectionToolProvider : InspectionToolProvider {

  override fun getInspectionClasses(): Array<Class<*>> {
    return arrayOf(PublishApiCheckInspectionTool::class.java, BlacklistJavaApiCheckInspectionTool::class.java)
  }
}


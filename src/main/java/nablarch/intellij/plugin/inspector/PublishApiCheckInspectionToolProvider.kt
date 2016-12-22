package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*

/**
 * Nablarchフレームワークの非公開APIを使用していないことをチェックする。
 * 
 * @author siosio
 */
class PublishApiCheckInspectionToolProvider : InspectionToolProvider {

  override fun getInspectionClasses(): Array<Class<*>> {
    return arrayOf(PublishApiCheckInspectionTool::class.java)
  }
}


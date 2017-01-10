package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*

/**
 * ブラックリストに定義されたクラスへの参照を検出するクラス。
 *
 * @author Naoki Yamamoto
 */
class BlacklistJavaApiReferenceInspector(
    private val expression: PsiReferenceExpression?,
    private val holder: ProblemsHolder,
    private val blacklist: Blacklist
) {

  fun inspect() {
    if (expression == null || !expression.isValid) {
      return
    }

    expression.resolve()?.let {
      if (it is PsiClass  && blacklist.isBlacklistJavaApi(it)) {
        addBlacklistProblem(holder, expression)
      }
    }
  }
}
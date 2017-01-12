package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*
import com.intellij.psi.util.*

/**
 * ブラックリストに定義されたクラスのインスタンス生成を検出するクラス。
 *
 * @author Naoki Yamamoto
 */
class BlacklistJavaApiLambdaInspector(
    private val expression: PsiLambdaExpression?,
    private val holder: ProblemsHolder,
    private val blacklist: Blacklist
) {

  fun inspect() {
    if (expression == null || !expression.isValid) {
      return
    }

    PsiTypesUtil.getPsiClass(expression.functionalInterfaceType)?.let {
      if (blacklist.isBlacklistJavaApi(it)) {
        addBlacklistProblem(holder, expression)
      }
    }
  }
}
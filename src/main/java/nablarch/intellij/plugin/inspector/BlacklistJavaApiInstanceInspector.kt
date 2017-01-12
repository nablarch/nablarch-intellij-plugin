package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*
import com.intellij.psi.util.*

/**
 * ブラックリストに定義されたクラスのインスタンス生成を検出するクラス。
 *
 * @author Naoki Yamamoto
 */
class BlacklistJavaApiInstanceInspector(
    private val expression: PsiNewExpression?,
    private val holder: ProblemsHolder,
    private val blacklist: Blacklist
) {

  fun inspect() {
    if (expression == null || !expression.isValid) {
      return
    }

    expression.classOrAnonymousClassReference?.resolve().let {
      if (it is PsiClass && blacklist.isBlacklistJavaApi(it)) {
        PsiTreeUtil.findChildOfAnyType(expression, PsiJavaCodeReferenceElement::class.java)?.let {
          addBlacklistProblem(holder, it)
        }
      }
    }
  }
}
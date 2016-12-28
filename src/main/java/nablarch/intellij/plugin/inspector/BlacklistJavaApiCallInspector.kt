package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*
import com.intellij.psi.util.*

/**
 * ブラックリストに定義されたメソッドやコンストラクタの呼び出し箇所を検出するクラス。
 *
 * @author Naoki Yamamoto
 */
class BlacklistJavaApiCallInspector(
    private val expression: PsiCallExpression?,
    private val holder: ProblemsHolder,
    private val blacklist: Blacklist
) {

  fun inspect() {
    if (expression == null || !expression.isValid) {
      return
    }

    val method = expression.resolveMethod()
    if (method != null) {

      if (isBlacklistJavaApi(method, blacklist)) {
        PsiTreeUtil.findChildOfAnyType(expression, PsiJavaCodeReferenceElement::class.java)?.let {
          addBlacklistProblem(holder, it)
        }
      }
    } else {
      if (expression is PsiNewExpression) {
        PsiTypesUtil.getPsiClass(expression.type)?.let {
          if (isBlacklistJavaApi(it, blacklist)) {
            PsiTreeUtil.findChildOfAnyType(expression, PsiJavaCodeReferenceElement::class.java)?.let {
              addBlacklistProblem(holder, it)
            }
          }
        }
      }
    }
  }
}
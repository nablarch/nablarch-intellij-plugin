package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*
import com.intellij.psi.util.*

/**
 * メソッド呼び出しとコンストラクタ呼び出しに関してNablarchの非公開API使用箇所を検出するクラス。
 * 
 * @author siosio
 */
class MethodCallInspector(
    private val expression: PsiCallExpression?,
    private val holder: ProblemsHolder,
    private val tags: List<String>
) {

  fun inspect() {
    if (expression == null || !expression.isValid) {
      return
    }

    val method = expression.resolveMethod()
    if (method != null) {
      if (!isNablarchClass(method.containingClass)) {
        return
      }
      if (!isPublishedMethod(method) && !isPublishedClass(method)) {
        addProblem(holder, expression, tags)
      }
    } else {
      if (expression is PsiNewExpression) {
        PsiTypesUtil.getPsiClass(expression.type)?.let { psiClass ->
          if (isNablarchClass(psiClass) && !isPublishedApi(psiClass, tags)) {
            PsiTreeUtil.findChildOfAnyType(expression, PsiJavaCodeReferenceElement::class.java)?.let {
              addProblem(holder, it, tags)
            }
          }
        }
      }
    }
  }

  private fun isPublishedMethod(method: PsiMethod): Boolean {
    val result = isPublishedApi(method, tags)
    return if (result) {
      true
    } else {
      method.findSuperMethods().firstOrNull {
        isPublishedApi(it, tags) || (it.containingClass?.let {
          isPublishedApi(it, tags)
        } ?: false)
      } != null
    }
  }

  private fun isPublishedClass(method: PsiMethod): Boolean {
    val psiClass = method.containingClass ?: return true
    return isPublishedApi(psiClass, tags)
  }
}

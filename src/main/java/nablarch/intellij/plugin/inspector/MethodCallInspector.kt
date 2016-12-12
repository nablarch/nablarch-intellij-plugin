package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*
import com.intellij.psi.util.*

class MethodCallInspector(
    private val expression: PsiCallExpression?,
    private val holder: ProblemsHolder
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
        PsiTreeUtil.findChildrenOfAnyType(expression, PsiIdentifier::class.java).firstOrNull {
          it.text == method.name
        }?.let {
          addProblem(holder, it)
        }
      }
    } else {
      if (expression is PsiNewExpression) {
        PsiTypesUtil.getPsiClass(expression.type)?.let { psiClass ->
          if (isNablarchClass(psiClass) && !isPublishedApi(psiClass)) {
            PsiTreeUtil.findChildOfAnyType(expression, PsiJavaCodeReferenceElement::class.java)?.let {
              addProblem(holder, it)
            }
          }
        }
      }
    }
  }

  private fun isPublishedMethod(method: PsiMethod): Boolean {
    val result = isPublishedApi(method)
    return if (result) {
      true
    } else {
      method.findSuperMethods().firstOrNull {
        isPublishedApi(it) || (it.containingClass?.let(::isPublishedApi) ?: false)
      } != null
    }
  }

  private fun isPublishedClass(method: PsiMethod): Boolean {
    val psiClass = method.containingClass ?: return true
    return isPublishedApi(psiClass)
  }
}
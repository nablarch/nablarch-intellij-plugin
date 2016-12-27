package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*
import com.intellij.psi.util.*

class JavaOpenApiCallInspector(
    private val expression: PsiCallExpression?,
    private val holder: ProblemsHolder
) {

  fun inspect() {
    if (expression == null || !expression.isValid) {
      return
    }

    val method = expression.resolveMethod()
    if (method != null) {

      if (!isJavaOpenApi(method)) {
        PsiTreeUtil.findChildOfAnyType(expression, PsiJavaCodeReferenceElement::class.java)?.let {
          addUnpermittedProblem(holder, it)
        }
      }
    } else {
      if (expression is PsiNewExpression) {
        PsiTypesUtil.getPsiClass(expression.type)?.let {
          if (!isJavaOpenApi(it)) {
            PsiTreeUtil.findChildOfAnyType(expression, PsiJavaCodeReferenceElement::class.java)?.let {
              addUnpermittedProblem(holder, it)
            }
          }
        }
      }
    }
  }
}
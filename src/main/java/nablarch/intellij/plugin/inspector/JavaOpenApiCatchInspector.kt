package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*
import com.intellij.psi.impl.source.*
import com.intellij.psi.util.*

class JavaOpenApiCatchInspector(
    private val catchType: PsiType?,
    private val holder: ProblemsHolder
) {

  fun inspect() {
    when (catchType) {
      is PsiDisjunctionType -> {
        catchType.disjunctions.forEach { type ->
          PsiTypesUtil.getPsiClass(type)?.let {
            if (!isJavaOpenApi(it)) {
              addUnpermittedProblem(holder, (type as PsiClassReferenceType).reference)
            }
          }
        }
      }
      else -> {
        PsiTypesUtil.getPsiClass(catchType)?.let {
          if (!isJavaOpenApi(it)) {
            addUnpermittedProblem(holder, (catchType as PsiClassReferenceType).reference)
          }
        }
      }
    }
  }
}
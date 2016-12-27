package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*
import com.intellij.psi.impl.source.*
import com.intellij.psi.util.*

class JavaOpenApiCatchInspector(
    private val catchType: PsiType?,
    private val holder: ProblemsHolder,
    private val blacklist: Set<String>
) {

  fun inspect() {
    if (catchType == null || !catchType.isValid) {
      return
    }
    
    val checker: (PsiClassReferenceType) -> Unit = { type ->
      PsiTypesUtil.getPsiClass(type)?.let {
        if (!isJavaOpenApi(it, blacklist)) {
          addUnpermittedProblem(holder, type.reference)
        }
      }
    }

    when (catchType) {
      is PsiDisjunctionType -> {
        catchType.disjunctions.forEach { type ->
          checker(type as PsiClassReferenceType)
        }
      }
      else -> {
        checker(catchType as PsiClassReferenceType)
      }
    }
  }
}
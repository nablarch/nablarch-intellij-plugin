package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*

class VariableDefinitionInspector(
    private val targetElement: PsiElement,
    private val psiClass: PsiClass,
    private val holder: ProblemsHolder
) {
  
  fun inspect() {
    if (!isNablarchClass(psiClass) || isPublishedApi(psiClass)) {
      return
    }
    
    psiClass.allMethods.forEach {
      if (isPublishedApi(it)) {
        return
      }
    }
    addProblem(holder,targetElement)
  }
}
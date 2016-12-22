package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*

/**
 * 変数定義部に関してNablarchの非公開APIを使用している箇所を検出するクラス。
 * 
 * @author siosio
 */
class VariableDefinitionInspector(
    private val targetElement: PsiElement,
    private val psiClass: PsiClass,
    private val holder: ProblemsHolder,
    private val tags: List<String>
) {
  
  fun inspect() {
    if (!isNablarchClass(psiClass) || isPublishedApi(psiClass, tags)) {
      return
    }
    
    psiClass.allMethods.forEach {
      if (isPublishedApi(it, tags)) {
        return
      }
    }
    addProblem(holder, targetElement, tags)
  }
}
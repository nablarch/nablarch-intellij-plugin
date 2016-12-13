package nablarch.intellij.plugin.inspector

import com.intellij.codeInsight.*
import com.intellij.codeInspection.*
import com.intellij.psi.*

val publishAnnotationName = "nablarch.core.util.annotation.Published"



fun isPublishedApi(element: PsiModifierListOwner):Boolean = AnnotationUtil.isAnnotated(element, publishAnnotationName, false)

fun isNablarchClass(psiClass: PsiClass?): Boolean = psiClass?.qualifiedName?.startsWith("nablarch.") ?: false

fun addProblem(holder: ProblemsHolder, element: PsiElement) {
  holder.registerProblem(element, "非公開APIです。", ProblemHighlightType.ERROR)
}


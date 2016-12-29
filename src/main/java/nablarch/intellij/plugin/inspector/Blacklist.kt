package nablarch.intellij.plugin.inspector

import com.intellij.psi.*
import com.intellij.psi.util.*

/**
 * ブラックリスト情報を保持するBeanクラス。
 *
 * @author Naoki Yamamoto
 */
data class Blacklist(val packages: List<String>, val classes: List<String>, val methods: List<String>) {

  fun isBlacklistJavaApi(psiMethod: PsiMethod?): Boolean {
    if (psiMethod == null) {
      return false
    }

    val name = PsiUtil.getMemberQualifiedName(psiMethod) ?: return false
    val sb = StringBuilder()
    sb.append(name).append('(')
    val paramTypes = psiMethod.getSignature(PsiSubstitutor.EMPTY).parameterTypes.map { it.canonicalText }
    paramTypes.joinTo(sb)
    sb.append(')')
    val fqcn = sb.toString()

    return packages.any { fqcn.startsWith(it) } ||
        classes.any { fqcn.startsWith(it)} ||
        methods.contains(fqcn)
  }

  fun isBlacklistJavaApi(psiClass: PsiClass?): Boolean {
    if (psiClass == null) {
      return false
    }

    val fqcn = PsiUtil.getMemberQualifiedName(psiClass) ?: return false
    return packages.any { fqcn.startsWith(it) } ||
        classes.any { fqcn.startsWith(it)}
  }

}
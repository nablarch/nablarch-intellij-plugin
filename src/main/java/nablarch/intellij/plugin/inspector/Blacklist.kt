package nablarch.intellij.plugin.inspector

import com.intellij.psi.*

/**
 * ブラックリスト情報を保持するBeanクラス。
 *
 * @author Naoki Yamamoto
 */
data class Blacklist(
    val packages: List<String> = listOf(
        "java.lang.reflect",
        "java.security"
    ),
    val classes: List<String> = listOf(
        "java.lang.Exception",
        "java.lang.RuntimeException",
        "java.lang.NullPointerException",
        "java.lang.ProcessBuilder",
        "java.lang.Thread",
        "java.util.Vector",
        "java.util.Hashtable"
    )
) {

  fun isBlacklistJavaApi(psiClass: PsiClass): Boolean {
    val fqcn = psiClass.qualifiedName ?: return false
    return packages.any { fqcn.startsWith(it) } ||
        classes.any { fqcn == it }
  }

}
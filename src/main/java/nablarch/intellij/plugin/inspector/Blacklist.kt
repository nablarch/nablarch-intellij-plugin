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
        "java.security",
        "java.security.acl",
        "java.security.cert",
        "java.security.interfaces",
        "java.security.spec"
    ),
    val classes: List<String> = listOf(
        "java.lang.Error",
        "java.lang.InheritableThreadLocal",
        "java.lang.Process",
        "java.lang.ProcessBuilder",
        "java.lang.ProcessBuilder.Redirect",
        "java.lang.Runnable",
        "java.lang.Runtime",
        "java.lang.RuntimePermission",
        "java.lang.SecurityManager",
        "java.lang.StringBuffer",
        "java.lang.System",
        "java.lang.Thread",
        "java.lang.ThreadGroup",
        "java.lang.ThreadLocal",
        "java.util.Hashtable",
        "java.util.Stack",
        "java.util.StringTokenizer",
        "java.util.Vector"
    )
) {

  fun isBlacklistJavaApi(psiClass: PsiClass): Boolean {
    val fqcn = psiClass.qualifiedName ?: return false
    val packageName = (psiClass.containingFile as PsiJavaFile).packageName

    return packages.any { packageName == it } ||
        classes.any { fqcn == it }
  }

}
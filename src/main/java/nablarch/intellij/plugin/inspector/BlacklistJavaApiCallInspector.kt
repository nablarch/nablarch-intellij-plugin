package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*
import com.intellij.psi.util.*

/**
 * ブラックリストに定義されたクラスの呼び出し箇所を検出するクラス。
 *
 * @author Naoki Yamamoto
 */
class BlacklistJavaApiCallInspector(
    private val element: PsiTypeElement?,
    private val holder: ProblemsHolder,
    private val blacklist: Blacklist
) {

  fun inspect() {
    if (element == null || !element.isValid) {
      return
    }

    PsiTypesUtil.getPsiClass(element.type)?.let {
      if (blacklist.isBlacklistJavaApi(it)) {
        PsiTreeUtil.findChildOfAnyType(element, PsiJavaCodeReferenceElement::class.java)?.let {
          addBlacklistProblem(holder, it)
        }
      }
    }

  }
}
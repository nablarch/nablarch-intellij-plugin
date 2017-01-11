package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*
import com.intellij.psi.impl.source.*
import com.intellij.psi.util.*

/**
 * ブラックリストに定義された例外クラスの送出箇所を検出するクラス。
 *
 * @author Naoki Yamamoto
 */
class BlacklistJavaApiThrowInspector(
    private val exception: PsiExpression?,
    private val holder: ProblemsHolder,
    private val blacklist: Blacklist
) {

  fun inspect() {
    if (exception == null || !exception.isValid) {
      return
    }

    PsiTypesUtil.getPsiClass(exception.type)?.let {
      if (blacklist.isBlacklistJavaApi(it)) {
        PsiTreeUtil.findChildOfAnyType(exception, PsiJavaCodeReferenceElement::class.java)?.let {
          addBlacklistProblem(holder, it)
        }
      }
    }
  }
}
package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.psi.*
import com.intellij.psi.impl.source.*
import com.intellij.psi.util.*

class PublishApiCheckInspectionTool : BaseJavaLocalInspectionTool() {

  override fun getGroupDisplayName(): String = "nablarch"

  override fun getDisplayName(): String = "use unpublished api."

  override fun getShortName(): String {
    return "unpublishedApi"
  }

  override fun isEnabledByDefault(): Boolean = true

  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : JavaElementVisitor() {

      /**
       * メソッド呼び出しのチェック
       */
      override fun visitMethodCallExpression(expression: PsiMethodCallExpression?) {
        super.visitMethodCallExpression(expression)
        MethodCallInspector(expression, holder).inspect()
      }

      /**
       * コンストラクタ呼び出しのチェック
       */
      override fun visitNewExpression(expression: PsiNewExpression?) {
        super.visitNewExpression(expression)
        MethodCallInspector(expression, holder).inspect()
      }

      /**
       * catchでの例外捕捉のチェック
       */
      override fun visitCatchSection(section: PsiCatchSection?) {
        super.visitCatchSection(section)
        if (section == null || !section.isValid) {
          return
        }

        val checker: (PsiClassReferenceType) -> Unit = { type ->
          PsiTypesUtil.getPsiClass(type)?.let {
            VariableDefinitionInspector(type.reference, it, holder).inspect()
          }
        }

        val catchType = section.catchType
        when (catchType) {
          // multi catch
          is PsiDisjunctionType -> {
            catchType.disjunctions.forEach { type ->
              checker(type as PsiClassReferenceType)
            }
          }
          // single catch
          else -> {
            checker(catchType as PsiClassReferenceType)
          }
        }

      }
    }
  }
}
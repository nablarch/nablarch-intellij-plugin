package nablarch.intellij.plugin.inspector

import com.intellij.codeHighlighting.*
import com.intellij.codeInspection.*
import com.intellij.codeInspection.ui.*
import com.intellij.psi.*
import com.intellij.psi.impl.source.*
import com.intellij.psi.util.*
import com.siyeh.ig.ui.*
import org.jdom.*
import javax.swing.*

/**
 * Nablarchの非公開APIの使用箇所を検出するインスペクタ実装。
 * 
 * @author siosio
 */
open class PublishApiCheckInspectionTool : BaseJavaLocalInspectionTool() {

  val tags: MutableList<String> = mutableListOf()

  var tagsString: String = ""

  override fun getGroupDisplayName(): String = "nablarch"

  override fun getDisplayName(): String = "use unpublished api."

  override fun getShortName(): String = "unpublishedApi"

  override fun getDefaultLevel(): HighlightDisplayLevel = HighlightDisplayLevel.ERROR

  override fun createOptionsPanel(): JComponent? {
    val listTable = ListTable(ListWrappingTableModel(listOf(tags), "呼び出しを許可するタグのリスト"))
    return UiUtils.createAddRemovePanel(listTable)
  }

  override fun writeSettings(node: Element) {
    tagsString = tags.joinToString(",")
    super.writeSettings(node)
  }

  override fun readSettings(node: Element) {
    super.readSettings(node)
    val split = tagsString.split(",")
    tags.clear()
    tags.addAll(split.filter(String::isNotBlank))
  }

  override fun isEnabledByDefault(): Boolean = true

  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : JavaElementVisitor() {

      /**
       * メソッド呼び出しのチェック
       */
      override fun visitMethodCallExpression(expression: PsiMethodCallExpression?) {
        super.visitMethodCallExpression(expression)
        MethodCallInspector(expression, holder, tags).inspect()
      }

      /**
       * コンストラクタ呼び出しのチェック
       */
      override fun visitNewExpression(expression: PsiNewExpression?) {
        super.visitNewExpression(expression)
        MethodCallInspector(expression, holder, tags).inspect()
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
            VariableDefinitionInspector(type.reference, it, holder, tags).inspect()
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
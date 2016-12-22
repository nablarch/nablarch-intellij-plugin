package nablarch.intellij.plugin.inspector

import com.intellij.codeHighlighting.*
import com.intellij.codeInspection.*
import com.intellij.openapi.fileChooser.*
import com.intellij.openapi.project.*
import com.intellij.openapi.ui.*
import com.intellij.psi.*
import org.jdom.*
import java.awt.*
import javax.swing.*


open class JavaOpenApiCheckInspectionTool : BaseJavaLocalInspectionTool() {

  val textField = JTextField(20)

  val errorLabel = JLabel()

  var blacklistFile: String = ""

  override fun getGroupDisplayName(): String = "nablarch"

  override fun getDisplayName(): String = "use unpermitted java api."

  override fun getShortName(): String = "unpermittedJavaApi"

  override fun getDefaultLevel(): HighlightDisplayLevel = HighlightDisplayLevel.ERROR

  override fun createOptionsPanel(): JComponent? {

    val parent = JPanel(VerticalFlowLayout())

    val inputPanel = JPanel(FlowLayout(FlowLayout.LEFT))
    val button = JButton("ファイルを選択")
    button.addActionListener {
      val descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor("config")
      val file = FileChooser.chooseFile(descriptor, ProjectUtil.guessCurrentProject(null), null)
      if (file != null) {
        textField.text = file.path
      }
    }
    inputPanel.add(JLabel("使用不許可API定義ファイル"))
    inputPanel.add(textField)
    inputPanel.add(button)
    parent.add(inputPanel)

    val errorPanel = JPanel(FlowLayout(FlowLayout.LEFT))
    errorPanel.add(errorLabel)
    parent.add(errorPanel)

    return parent
  }

  override fun writeSettings(node: Element) {
    try {
      refreshBlacklist(textField.text)
      errorLabel.text = ""
      blacklistFile = textField.text
    } catch (e: Exception) {
      errorLabel.text = "指定されたファイルの読み込みに失敗しました。"
    }

    super.writeSettings(node)
  }

  override fun readSettings(node: Element) {
    super.readSettings(node)
    textField.text = blacklistFile
  }

  override fun isEnabledByDefault(): Boolean = true

  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : JavaElementVisitor() {

      /**
       * メソッド呼び出しのチェック
       */
      override fun visitMethodCallExpression(expression: PsiMethodCallExpression?) {
        super.visitMethodCallExpression(expression)
        JavaOpenApiCallInspector(expression, holder).inspect()
      }

      /**
       * コンストラクタ呼び出しのチェック
       */
      override fun visitNewExpression(expression: PsiNewExpression?) {
        super.visitNewExpression(expression)
        JavaOpenApiCallInspector(expression, holder).inspect()
      }

      /**
       * catchでの例外捕捉のチェック
       */
      override fun visitCatchSection(section: PsiCatchSection?) {
        super.visitCatchSection(section)
        if (section == null || !section.isValid) {
          return
        }
        JavaOpenApiCatchInspector(section.catchType, holder).inspect()

      }
    }
  }
}
package nablarch.intellij.plugin.inspector

import com.intellij.codeHighlighting.*
import com.intellij.codeInspection.*
import com.intellij.diagnostic.*
import com.intellij.openapi.extensions.*
import com.intellij.openapi.fileChooser.*
import com.intellij.openapi.project.*
import com.intellij.openapi.ui.*
import com.intellij.openapi.util.text.*
import com.intellij.psi.*
import org.jdom.*
import java.io.*
import java.util.*
import javax.swing.*

/**
 * ブラックリストに定義されたJavaAPIの使用箇所を検出するインスペクタ。
 *
 * @author Naoki Yamamoto
 */
open class BlacklistJavaApiCheckInspectionTool : BaseJavaLocalInspectionTool() {

  val textField = JTextField(10)

  var blacklistFile: String = ""

  override fun getGroupDisplayName(): String = "nablarch"

  override fun getDisplayName(): String = "use blacklist java api."

  override fun getShortName(): String = "blacklistJavaApi"

  override fun getDefaultLevel(): HighlightDisplayLevel = HighlightDisplayLevel.ERROR

  override fun createOptionsPanel(): JComponent? {

    val parent = JPanel(VerticalFlowLayout())

    val inputPanel = JPanel()
    inputPanel.layout = BoxLayout(inputPanel, BoxLayout.X_AXIS)
    val button = TextFieldWithBrowseButton(textField)
    button.addActionListener {
      val descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor("config")
      val file = FileChooser.chooseFile(descriptor, ProjectUtil.guessCurrentProject(null), null)
      if (file != null) {
        button.text = file.path
      }
    }
    inputPanel.add(button)

    parent.add(JLabel("使用不許可API定義ファイル:"))
    parent.add(inputPanel)

    return parent
  }

  override fun writeSettings(node: Element) {
    blacklistFile = textField.text
    super.writeSettings(node)
  }

  override fun readSettings(node: Element) {
    super.readSettings(node)
    textField.text = blacklistFile
  }

  override fun isEnabledByDefault(): Boolean = true

  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {

    val blacklist = createBlacklist()

    return object : JavaElementVisitor() {

      /**
       * クラスの使用箇所のチェック
       */
      override fun visitTypeElement(type: PsiTypeElement?) {
        super.visitTypeElement(type)
        BlacklistJavaApiCallInspector(type, holder, blacklist).inspect()
      }

      /**
       * クラスへの参照箇所をチェック
       */
      override fun visitReferenceExpression(expression: PsiReferenceExpression?) {
        super.visitReferenceExpression(expression)
        BlacklistJavaApiReferenceInspector(expression, holder, blacklist).inspect()
      }

      /**
       * catchでの例外捕捉のチェック
       */
      override fun visitCatchSection(section: PsiCatchSection?) {
        super.visitCatchSection(section)
        if (section == null || !section.isValid) {
          return
        }
        BlacklistJavaApiCatchInspector(section.catchType, holder, blacklist).inspect()

      }
    }
  }

  private fun createBlacklist(): Blacklist {
    if (blacklistFile.isBlank()) {
      return Blacklist()
    } else {
      val file = File(blacklistFile)
      if (file.exists()) {
        val packages = ArrayList<String>()
        val classes = ArrayList<String>()
        file.forEachLine {
          if (it.endsWith(".*")) {
            packages.add(StringUtil.trimEnd(it, ".*"))
          } else if (it.isNotBlank()){
            classes.add(it)
          }
        }
        return Blacklist(packages, classes)
      } else {
        throw PluginException(
            "指定されたブラックリスト定義ファイルが見つかりません。 [File: ${file.absolutePath}]",
            PluginId.getId("com.nablarch.tool.plugin"))
      }
    }
  }
}
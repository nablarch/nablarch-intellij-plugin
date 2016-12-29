package nablarch.intellij.plugin.inspector

import com.intellij.codeHighlighting.*
import com.intellij.codeInspection.*
import com.intellij.diagnostic.*
import com.intellij.openapi.extensions.*
import com.intellij.openapi.fileChooser.*
import com.intellij.openapi.project.*
import com.intellij.openapi.ui.*
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

  val defaultBlacklistPackages = listOf("java.awt")

  val defaultBlacklistClasses = listOf("java.lang.Exception", "java.lang.RuntimeException", "java.lang.NullPointerException", "java.applet.Applet")

  val defaultBlacklistMethods = listOf("java.net.HttpCookie.getName()", "java.net.PasswordAuthentication.PasswordAuthentication(java.lang.String, char[])")

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
       * メソッド呼び出しのチェック
       */
      override fun visitMethodCallExpression(expression: PsiMethodCallExpression?) {
        super.visitMethodCallExpression(expression)
        BlacklistJavaApiCallInspector(expression, holder, blacklist).inspect()
      }

      /**
       * コンストラクタ呼び出しのチェック
       */
      override fun visitNewExpression(expression: PsiNewExpression?) {
        super.visitNewExpression(expression)
        BlacklistJavaApiCallInspector(expression, holder, blacklist).inspect()
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
      return Blacklist(defaultBlacklistPackages, defaultBlacklistClasses, defaultBlacklistMethods)
    } else {
      val file = File(blacklistFile).absoluteFile
      if (file.exists()) {
        val packages = ArrayList<String>()
        val classes = ArrayList<String>()
        val methods = ArrayList<String>()
        file.forEachLine {
          if (it.endsWith(".*")) {
            packages.add(it.trimEnd { it == '*' })
          } else if (it.endsWith(')')) {
            methods.add(it)
          } else if(it.isNotBlank()) {
            classes.add(it)
          }
        }
        return Blacklist(packages, classes, methods)
      } else {
        throw PluginException(
            "指定されたブラックリスト定義ファイルが見つかりません。 [File: ${file.absolutePath}]",
            PluginId.getId("com.nablarch.tool.plugin"))
      }
    }
  }
}
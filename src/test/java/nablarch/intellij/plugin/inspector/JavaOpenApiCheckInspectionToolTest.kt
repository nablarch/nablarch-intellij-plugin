package nablarch.intellij.plugin.inspector

import com.intellij.openapi.roots.*
import com.intellij.pom.java.*
import com.intellij.testFramework.*
import com.intellij.testFramework.fixtures.*
import com.intellij.util.*
import junit.framework.*
import org.jdom.*
import java.applet.*
import java.net.*


/**
 * [JavaOpenApiCheckInspectionTool]のテスト。
 */
open class JavaOpenApiCheckInspectionToolTest : LightCodeInsightFixtureTestCase() {

  override fun getTestDataPath(): String = "testData/nablarch/intellij/plugin/inspector"

  override fun setUp() {
    super.setUp()
    myFixture.enableInspections(JavaOpenApiCheckInspectionTool())
    PsiTestUtil.addLibrary(myModule, PathUtil.getJarPathForClass(Applet::class.java))
    PsiTestUtil.addLibrary(myModule, PathUtil.getJarPathForClass(HttpCookie::class.java))
    LanguageLevelProjectExtension.getInstance(project).languageLevel = LanguageLevel.JDK_1_8
    refreshBlacklist("")
  }

  // -------------------------------------------------------------------------------- NGパターン

  fun `test_ブラックリストに定義されたクラスのコンストラクタを呼び出している場合はNGとなること`() {
    myFixture.testHighlighting("ブラックリストに定義されたクラスのコンストラクタ.java")
  }

  fun `test_ブラックリストに定義されたクラスのメソッドを呼び出している場合はNGとなること`() {
    myFixture.testHighlighting("ブラックリストに定義されたクラスのメソッド.java")
  }

  fun `test_ブラックリストに定義された例外クラスを捕捉している場合はNGとなること`() {
    myFixture.testHighlighting("ブラックリストに定義された例外クラスの捕捉.java")
  }

  fun `test_ブラックリストに定義されたパッケージ内のクラスを使用している場合はNGとなること`() {
    myFixture.testHighlighting("ブラックリストに定義されたパッケージ内のクラス.java")
  }

  fun `test_独自定義のブラックリストに定義されたクラスのコンストラクタを呼び出している場合はNGとなること`() {
    refreshBlacklist(testDataPath + "/blacklist.config")
    myFixture.testHighlighting("独自定義のブラックリストに定義されたクラスのコンストラクタ.java")
  }

  fun `test_独自定義のブラックリストに定義されたクラスのメソッドを呼び出している場合はNGとなること`() {
    refreshBlacklist(testDataPath + "/blacklist.config")
    myFixture.testHighlighting("独自定義のブラックリストに定義されたクラスのメソッド.java")
  }

  fun `test_独自定義のブラックリストに定義された例外クラスを捕捉している場合はNGとなること`() {
    refreshBlacklist(testDataPath + "/blacklist.config")
    myFixture.testHighlighting("独自定義のブラックリストに定義された例外クラスの捕捉.java")
  }

  fun `test_独自定義のブラックリストファイルが存在しない場合にエラーとなること`() {
    val sut = JavaOpenApiCheckInspectionTool()
    sut.textField.text = "notFound.config"

    sut.writeSettings(Element("test"))

    TestCase.assertEquals("ファイルの読み込みに失敗しました。", sut.errorLabel.text)
  }

  // -------------------------------------------------------------------------------- OKパターン

  fun `test_ブラックリストで未定義のクラスのコンストラクタを呼び出している場合はOKとなること`() {
    myFixture.testHighlighting("ブラックリストで未定義のクラスのコンストラクタ.java")
  }

  fun `test_ブラックリストで未定義のクラスのメソッドを呼び出している場合はOKとなること`() {
    myFixture.testHighlighting("ブラックリストで未定義のクラスのメソッド.java")
  }

  fun `test_ブラックリストで未定義の例外クラスを捕捉している場合はOKとなること`() {
    myFixture.testHighlighting("ブラックリストで未定義の例外クラスの捕捉.java")
  }

  fun `test_独自定義のブラックリストで未定義のクラスのコンストラクタを呼び出している場合はOKとなること`() {
    refreshBlacklist(testDataPath + "/blacklist.config")
    myFixture.testHighlighting("独自定義のブラックリストで未定義のクラスのコンストラクタ.java")
  }

  fun `test_独自定義のブラックリストで未定義のクラスのメソッドを呼び出している場合はOKとなること`() {
    refreshBlacklist(testDataPath + "/blacklist.config")
    myFixture.testHighlighting("独自定義のブラックリストで未定義のクラスのメソッド.java")
  }

  fun `test_独自定義のブラックリストで未定義の例外クラスを捕捉している場合はOKとなること`() {
    refreshBlacklist(testDataPath + "/blacklist.config")
    myFixture.testHighlighting("独自定義のブラックリストで未定義の例外クラスの捕捉.java")
  }

  fun `test_独自定義のブラックリストファイルを正常に読み込めること`() {
    val sut = JavaOpenApiCheckInspectionTool()
    sut.textField.text = testDataPath + "/blacklist.config"
    sut.errorLabel.text = "ファイルの読み込みに失敗しました。"

    sut.writeSettings(Element("test"))

    TestCase.assertEquals("", sut.errorLabel.text)
    TestCase.assertEquals(testDataPath + "/blacklist.config", sut.blacklistFile)
  }
}
package nablarch.intellij.plugin.inspector

import com.intellij.codeInspection.*
import com.intellij.diagnostic.*
import com.intellij.mock.*
import com.intellij.openapi.roots.*
import com.intellij.pom.java.*
import com.intellij.psi.*
import com.intellij.testFramework.*
import com.intellij.testFramework.fixtures.*
import com.intellij.util.*
import java.applet.*
import java.net.*


/**
 * [BlacklistJavaApiCheckInspectionTool]のテスト。
 */
open class BlacklistJavaApiCheckInspectionToolTest : LightCodeInsightFixtureTestCase() {

  override fun getTestDataPath(): String = "testData/nablarch/intellij/plugin/inspector"

  val sut = BlacklistJavaApiCheckInspectionTool()

  override fun setUp() {
    super.setUp()
    PsiTestUtil.addLibrary(myModule, PathUtil.getJarPathForClass(Applet::class.java))
    PsiTestUtil.addLibrary(myModule, PathUtil.getJarPathForClass(HttpCookie::class.java))
    LanguageLevelProjectExtension.getInstance(project).languageLevel = LanguageLevel.JDK_1_8

    sut.blacklistFile = ""
    myFixture.enableInspections(sut)
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
    sut.blacklistFile = testDataPath + "/blacklist.config"
    myFixture.testHighlighting("独自定義のブラックリストに定義されたクラスのコンストラクタ.java")
  }

  fun `test_独自定義のブラックリストに定義されたクラスのメソッドを呼び出している場合はNGとなること`() {
    sut.blacklistFile = testDataPath + "/blacklist.config"
    myFixture.testHighlighting("独自定義のブラックリストに定義されたクラスのメソッド.java")
  }

  fun `test_独自定義のブラックリストに定義された例外クラスを捕捉している場合はNGとなること`() {
    sut.blacklistFile = testDataPath + "/blacklist.config"
    myFixture.testHighlighting("独自定義のブラックリストに定義された例外クラスの捕捉.java")
  }

  fun `test_独自定義のブラックリストファイルが存在しない場合にエラーとなること`() {
    sut.blacklistFile = "notFound.config"

    try {
      sut.buildVisitor(ProblemsHolder(InspectionManager.getInstance(project), MockPsiFile(PsiManager.getInstance(project)), true), true)
      fail("ファイルが見つからないため、例外が発生する")
    } catch (e: Exception) {
      assertTrue(e is PluginException)
      assertTrue(e.message!!.startsWith("指定されたブラックリスト定義ファイルが見つかりません。"))
      assertTrue(e.message!!.contains("notFound.config"))
      assertTrue(e.message!!.contains("com.nablarch.tool.plugin"))
    }
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
    sut.blacklistFile = testDataPath + "/blacklist.config"
    myFixture.testHighlighting("独自定義のブラックリストで未定義のクラスのコンストラクタ.java")
  }

  fun `test_独自定義のブラックリストで未定義のクラスのメソッドを呼び出している場合はOKとなること`() {
    sut.blacklistFile = testDataPath + "/blacklist.config"
    myFixture.testHighlighting("独自定義のブラックリストで未定義のクラスのメソッド.java")
  }

  fun `test_独自定義のブラックリストで未定義の例外クラスを捕捉している場合はOKとなること`() {
    sut.blacklistFile = testDataPath + "/blacklist.config"
    myFixture.testHighlighting("独自定義のブラックリストで未定義の例外クラスの捕捉.java")
  }
}
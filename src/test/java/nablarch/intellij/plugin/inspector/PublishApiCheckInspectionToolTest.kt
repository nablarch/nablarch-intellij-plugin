package nablarch.intellij.plugin.inspector

import com.intellij.openapi.roots.*
import com.intellij.pom.java.*
import com.intellij.testFramework.*
import com.intellij.testFramework.fixtures.*
import com.intellij.util.*
import nablarch.core.message.*
import nablarch.fw.*
import nablarch.fw.results.*
import nablarch.fw.web.*
import org.apache.commons.httpclient.*
import java.math.*


/**
 * [PublishApiCheckInspectionTool]のテスト。
 */
open class PublishApiCheckInspectionToolTest : LightCodeInsightFixtureTestCase() {

  override fun getTestDataPath(): String = "testData/nablarch/intellij/plugin/inspector"
  

  override fun setUp() {
    super.setUp()
    val publishApiCheckInspectionTool = PublishApiCheckInspectionTool()
    publishApiCheckInspectionTool.tags.add("architect")
    myFixture.enableInspections(publishApiCheckInspectionTool)
    PsiTestUtil.addLibrary(myModule, PathUtil.getJarPathForClass(HttpRequest::class.java))
    PsiTestUtil.addLibrary(myModule, PathUtil.getJarPathForClass(ExecutionContext::class.java))
    PsiTestUtil.addLibrary(myModule, PathUtil.getJarPathForClass(BigDecimal::class.java))
    PsiTestUtil.addLibrary(myModule, PathUtil.getJarPathForClass(HttpClient::class.java))
    PsiTestUtil.addLibrary(myModule, PathUtil.getJarPathForClass(MessageUtil::class.java))
    PsiTestUtil.addLibrary(myModule, PathUtil.getJarPathForClass(TransactionAbnormalEnd::class.java))
    LanguageLevelProjectExtension.getInstance(project).languageLevel = LanguageLevel.JDK_1_8
  }
  
  // -------------------------------------------------------------------------------- lNGパターン
  
  fun `test_非公開なメソッドを呼び出している場合はNGとなること`() {
    myFixture.testHighlighting("非公開メソッドの呼び出し.java")
  }
  
  fun `test_非公開なコンストラクタを呼び出している場合はNGとなること`() {
    myFixture.testHighlighting("非公開コンストラクタの呼び出し.java")
  }
  
  fun `test_Staticな非公開メソッドを呼び出している場合はNGとなること`() {
    myFixture.testHighlighting("非公開なStaticメソッドの呼び出し.java")
  }
  
  fun `test_非公開な例外の捕捉はNGとなること`() {
    myFixture.addClass("""
    package nablarch.exception;
    public class UnpublishedException extends RuntimeException {
    }
    """)
    
    myFixture.testHighlighting("非公開な例外クラスの利用.java")
  }
  
  fun `test_非公開クラスの未定義デフォルトコンストラクタはNGとなること`() {
    myFixture.addClass("""
      package nablarch.test;
      public class Hoge {
      }
    """)

    myFixture.testHighlighting("非公開クラスのデフォルトコンストラクタ.java")
  }
  
  // -------------------------------------------------------------------------------- OKパターン
  
  fun `test_Nablarch以外のAPIの利用はOKとなること`() {
    myFixture.testHighlighting("Nablarch以外のクラスの利用.java")
  }

  fun `test_公開クラスのコンストラクタ呼び出しはOKとなること`() {
    myFixture.testHighlighting("公開クラスのコンストラクタ呼び出し.java")
  }

  fun `test_公開クラスのメソッド呼び出しはOKとなること`() {
    myFixture.testHighlighting("公開クラスのメソッド呼び出し.java")
  }
  
  fun `test_Staticな公開メソッドの呼び出しはOKとなること`() {
    myFixture.testHighlighting("Staticな公開メソッドの呼び出し.java")
  }
  
  fun `test_ネストした公開クラスの利用はOKとなること`() {
    myFixture.testHighlighting("ネストした公開クラスの利用.java")
  }

  fun `test_公開クラスの捕捉はOKとなること`() {
    myFixture.addClass("""
      package nablarch.test;
      import nablarch.core.util.annotation.Published;
      public class HogeException extends RuntimeException {
          @Published
          public HogeException() {
          }
      }
    """)
    myFixture.testHighlighting("公開クラスの捕捉.java")
  }
  
  fun `test_公開クラスの未定義デフォルトコンストラクタはOKとなること`() {
    myFixture.addClass("""
      package nablarch.test;
      import nablarch.core.util.annotation.Published;
      @Published(tag = null)
      public class Hoge {
      }
    """)
    
    myFixture.testHighlighting("公開クラスのデフォルトコンストラクタ.java")
  }
  
  fun `test_許可タグしていありでアノテーションのタグに列挙されていない場合はNGとなること`() {
    myFixture.addClass("""
      package nablarch.test;
      import nablarch.core.util.annotation.Published;
      public class Hoge {
          @Published(tag = {"OK", "", null})
          public Hoge() {
          }
      }
    """)
    myFixture.testHighlighting("カテゴリNG.java")
  }
  
  fun `test_許可タグを指定していなくてアノテーションにタグありの場合はNGとなること`() {
    val publishApiCheckInspectionTool = PublishApiCheckInspectionTool()
    publishApiCheckInspectionTool.tags.clear()
    myFixture.enableInspections(publishApiCheckInspectionTool)
    myFixture.addClass("""
      package nablarch.test;
      import nablarch.core.util.annotation.Published;
      public class Hoge {
          @Published(tag = {"OK", "", null})
          public Hoge() {
          }
      }
    """)
    myFixture.testHighlighting("カテゴリNG_2.java")
  }
  
  fun `test_許可タグを指定していてそのタグが１つでも指定されていればOKとなること`() {
    val publishApiCheckInspectionTool = PublishApiCheckInspectionTool()
    publishApiCheckInspectionTool.tags.clear()
    publishApiCheckInspectionTool.tags += listOf<String>("tag1", "tag2")
    
    myFixture.enableInspections(publishApiCheckInspectionTool)
    myFixture.addClass("""
      package nablarch.test;
      import nablarch.core.util.annotation.Published;
      public class Hoge {
          @Published(tag = {"tag2", "tag3", "tag4", null})
          public Hoge() {
          }
      }
    """)
    myFixture.testHighlighting("カテゴリOK.java")
  }
}
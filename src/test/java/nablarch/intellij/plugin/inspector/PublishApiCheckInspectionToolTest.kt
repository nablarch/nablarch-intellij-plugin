package nablarch.intellij.plugin.inspector

import com.intellij.pom.java.*
import com.intellij.testFramework.*
import com.intellij.testFramework.builders.*
import com.intellij.testFramework.fixtures.*
import com.intellij.util.*
import nablarch.core.message.*
import nablarch.fw.*
import nablarch.fw.results.*
import nablarch.fw.web.*
import org.apache.commons.httpclient.*
import java.io.*


/**
 * [PublishApiCheckInspectionTool]のテスト。
 */
@TestDataPath("\$CONTENT_ROOT/testData/nablarch/intellij/plugin/inspector/publishapi")
open class PublishApiCheckInspectionToolTest : JavaCodeInsightFixtureTestCase() {

  override fun getTestDataPath(): String = "testData/nablarch/intellij/plugin/inspector/publishapi"

  override fun setUp() {
    super.setUp()
    val publishApiCheckInspectionTool = PublishApiCheckInspectionTool()
    publishApiCheckInspectionTool.tags.add("architect")
    myFixture.enableInspections(publishApiCheckInspectionTool)
  }

  override fun tuneFixture(moduleBuilder: JavaModuleFixtureBuilder<*>) {
    val path = PathUtil.getJarPathForClass(HttpRequest::class.java)
    moduleBuilder.addContentRoot(File(path).parentFile.parentFile.parentFile.parent)
    moduleBuilder.setLanguageLevel(LanguageLevel.JDK_1_8)
    moduleBuilder.addLibrary("jre", PathUtil.getJarPathForClass(java.lang.Object::class.java))
    moduleBuilder.addLibrary("nablarch-cw-web", PathUtil.getJarPathForClass(HttpRequest::class.java))
    moduleBuilder.addLibrary("nablarch-core", PathUtil.getJarPathForClass(ExecutionContext::class.java))
    moduleBuilder.addLibrary("nablarch-core-message", PathUtil.getJarPathForClass(MessageUtil::class.java))
    moduleBuilder.addLibrary("nablarch-fw", PathUtil.getJarPathForClass(TransactionAbnormalEnd::class.java))
    moduleBuilder.addLibrary("http-client", PathUtil.getJarPathForClass(HttpClient::class.java))
  }

  // -------------------------------------------------------------------------------- NGパターン
  
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

  fun `test_非公開クラスを継承した場合NGとなること`() {
    myFixture.testHighlighting("非公開クラスの継承.java")
  }

  fun `test_非公開インタフェースを実装した場合NGとなること`() {
    myFixture.testHighlighting("非公開インタフェースの実装.java")
  }

  fun `test_非公開コンストラクタのsuper呼び出しはNGとなること`() {
    myFixture.addClass("""
      package nablarch.test;
      import nablarch.core.util.annotation.Published;
      public class Hoge {

          public Hoge(String str) {
          }

          @Published
          public void test() {};
      }
    """)
    myFixture.testHighlighting("非公開コンストラクタのsuper呼び出し.java")
  }

  fun `test_公開メソッドを持つクラスは継承できること`() {
    myFixture.testHighlighting("公開メソッドを持つクラスの継承.java")
  }
}

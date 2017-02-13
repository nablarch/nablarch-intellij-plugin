package nablarch.intellij.plugin.inspector;

import nablarch.fw.web.HttpCookie;

public class 公開クラスのメソッド呼び出し {

    public void method() {
        final HttpCookie cookie = new HttpCookie();
        cookie.put("a", "b");
    }
}

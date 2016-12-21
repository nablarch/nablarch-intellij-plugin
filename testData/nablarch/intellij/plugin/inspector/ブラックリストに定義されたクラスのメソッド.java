package nablarch.intellij.plugin.inspector;

import java.net.HttpCookie;

public class ブラックリストに定義されたクラスのメソッド {

    public String method(HttpCookie cookie) {
        return <error descr="使用不許可APIです。">cookie.getName</error>();
    }
}
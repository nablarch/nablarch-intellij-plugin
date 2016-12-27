package nablarch.intellij.plugin.inspector;

import java.applet.Applet;
import java.net.HttpCookie;

public class ブラックリストに定義されたクラスのメソッド {

    public String method(Applet applet) {
        <error descr="使用不許可APIです。">applet.destroy</error>();

        HttpCookie cookie = new HttpCookie("cookie", "value");
        return <error descr="使用不許可APIです。">cookie.getName</error>();
    }
}
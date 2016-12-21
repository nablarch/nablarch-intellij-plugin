package nablarch.intellij.plugin.inspector;

import java.applet.Applet;

public class ブラックリストに定義されたクラスのコンストラクタ {

    public void method() {
        Applet app = new <error descr="使用不許可APIです。">Applet</error>();
    }
}
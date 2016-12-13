package nablarch.intellij.plugin.inspector;

import nablarch.test.Hoge;

public class 非公開クラスのデフォルトコンストラクタ {
    
    public void method() {
        new <error descr="非公開APIです。">Hoge</error>();
    }
}

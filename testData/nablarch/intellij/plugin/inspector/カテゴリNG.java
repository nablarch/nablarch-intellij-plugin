package nablarch.intellij.plugin.inspector;

import nablarch.test.*;

public class カテゴリNG {

    public void method() {
        Hoge hoge = new <error descr="非公開APIです。(許可タグリスト:architect)">Hoge</error>();
    }

}

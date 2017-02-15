package nablarch.intellij.plugin.inspector;

import nablarch.test.*;

public class カテゴリNG {

    public void method() {
        Hoge hoge = <error descr="非公開APIです。(許可タグリスト:architect)">new Hoge()</error>;
    }

}

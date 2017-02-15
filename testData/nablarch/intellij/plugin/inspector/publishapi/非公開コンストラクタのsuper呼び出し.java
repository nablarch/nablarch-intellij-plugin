package nablarch.intellij.plugin.inspector;

import nablarch.test.*;

public class 非公開コンストラクタのsuper呼び出し extends Hoge {

    public 非公開コンストラクタのsuper呼び出し() {
        <error descr="非公開APIです。(許可タグリスト:architect)">super("hoge")</error>;
    }
}

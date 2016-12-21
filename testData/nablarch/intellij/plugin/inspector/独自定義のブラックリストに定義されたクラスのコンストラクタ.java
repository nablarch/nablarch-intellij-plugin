package nablarch.intellij.plugin.inspector;

import java.util.List;
import java.util.ArrayList;

public class 独自定義のブラックリストに定義されたクラスのコンストラクタ {

    public void method() {
        List<String> list = new <error descr="使用不許可APIです。">ArrayList<String></error>();
    }
}
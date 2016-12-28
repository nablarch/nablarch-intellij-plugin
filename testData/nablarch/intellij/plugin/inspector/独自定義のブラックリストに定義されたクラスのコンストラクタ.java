package nablarch.intellij.plugin.inspector;

import java.beans.BeanDescriptor;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class 独自定義のブラックリストに定義されたクラスのコンストラクタ {

    public void method() {
        // メソッド指定
        List<String> list = new <error descr="使用不許可APIです。">ArrayList<String></error>();

        // クラス指定
        BigDecimal decimal = new <error descr="使用不許可APIです。">BigDecimal</error>("123");

        // パッケージ指定
        BeanDescriptor descriptor = new <error descr="使用不許可APIです。">BeanDescriptor</error>(String.class);
    }
}
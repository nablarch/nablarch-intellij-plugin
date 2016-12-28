package nablarch.intellij.plugin.inspector;

import java.beans.BeanDescriptor;
import java.math.BigDecimal;
import java.util.HashMap;

public class 独自定義のブラックリストに定義されたクラスのメソッド {

    public void method(BigDecimal decimal, BeanDescriptor descriptor) {
        // メソッド指定
        HashMap<String, String> map = new HashMap<>();
        <error descr="使用不許可APIです。">map.put</error>("key", "value");

        // クラス指定
        <error descr="使用不許可APIです。">decimal.abs</error>();

        // パッケージ指定
        <error descr="使用不許可APIです。">descriptor.getBeanClass</error>();
    }
}
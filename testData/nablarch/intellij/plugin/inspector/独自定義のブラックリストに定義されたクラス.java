package nablarch.intellij.plugin.inspector;

import java.beans.BeanDescriptor;
import java.math.BigDecimal;

public class 独自定義のブラックリストに定義されたクラス {

    public void method(<warning descr="使用不許可APIです。">BigDecimal</warning> decimal) {
        <warning descr="使用不許可APIです。">BeanDescriptor</warning> descriptor = new <warning descr="使用不許可APIです。">BeanDescriptor</warning>(String.class);
    }
}
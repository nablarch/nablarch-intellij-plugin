package nablarch.intellij.plugin.inspector;

import java.beans.BeanDescriptor;
import java.math.BigDecimal;

public class 独自定義のブラックリストに定義されたクラス {

    public void method(<error descr="使用不許可APIです。">BigDecimal</error> decimal) {
        <error descr="使用不許可APIです。">BeanDescriptor</error> descriptor = new BeanDescriptor(String.class);
    }
}
package nablarch.intellij.plugin.inspector;

import java.math.BigDecimal;

public class 独自定義のブラックリストに定義されたクラスのメソッド {

    public BigDecimal method(BigDecimal decimal) {
        return <error descr="使用不許可APIです。">decimal.abs</error>();
    }
}
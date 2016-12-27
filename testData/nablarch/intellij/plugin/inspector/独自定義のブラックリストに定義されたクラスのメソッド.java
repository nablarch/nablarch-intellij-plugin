package nablarch.intellij.plugin.inspector;

import java.math.BigDecimal;
import java.util.HashMap;

public class 独自定義のブラックリストに定義されたクラスのメソッド {

    public BigDecimal method(BigDecimal decimal) {
        HashMap<String, String> map = new HashMap<>();
        <error descr="使用不許可APIです。">map.put</error>("key", "value");

        return <error descr="使用不許可APIです。">decimal.abs</error>();
    }
}
package nablarch.intellij.plugin.inspector;

import java.sql.SQLException;

public class 独自定義のブラックリストに定義された例外クラスの捕捉 {

    public void method() {

        try {
            throw new <error descr="使用不許可APIです。">SQLException</error>();
        } catch (<error descr="使用不許可APIです。">SQLException</error> e) {
        }
    }
}
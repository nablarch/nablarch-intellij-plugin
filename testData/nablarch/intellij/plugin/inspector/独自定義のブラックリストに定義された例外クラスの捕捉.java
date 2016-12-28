package nablarch.intellij.plugin.inspector;

import java.beans.IntrospectionException;
import java.sql.SQLException;

public class 独自定義のブラックリストに定義された例外クラスの捕捉 {

    public void method() {

        // クラス指定
        try {
            throw new <error descr="使用不許可APIです。">SQLException</error>();
        } catch (<error descr="使用不許可APIです。">SQLException</error> e) {
        }

        // パッケージ指定
        try {
            throw new <error descr="使用不許可APIです。">IntrospectionException</error>("message");
        } catch (<error descr="使用不許可APIです。">IntrospectionException</error> e) {
        }
    }
}
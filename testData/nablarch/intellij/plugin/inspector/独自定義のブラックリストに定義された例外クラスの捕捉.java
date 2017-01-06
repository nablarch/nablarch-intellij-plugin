package nablarch.intellij.plugin.inspector;

import java.beans.IntrospectionException;
import java.sql.SQLException;

public class 独自定義のブラックリストに定義された例外クラスの捕捉 {

    public void method() {
        try {
            throw new SQLException();
        } catch (<error descr="使用不許可APIです。">SQLException</error> e) {
        }

        try {
            throw new IntrospectionException("message");
        } catch (<error descr="使用不許可APIです。">IntrospectionException</error> e) {
        }
    }
}
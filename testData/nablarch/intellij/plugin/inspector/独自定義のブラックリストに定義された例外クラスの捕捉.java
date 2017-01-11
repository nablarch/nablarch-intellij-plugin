package nablarch.intellij.plugin.inspector;

import java.beans.IntrospectionException;
import java.sql.SQLException;

public class 独自定義のブラックリストに定義された例外クラスの捕捉 {

    public void method() {
        try {
            throw new <warning descr="使用不許可APIです。">SQLException</warning>();
        } catch (<warning descr="使用不許可APIです。">SQLException</warning> e) {
        }

        try {
            throw new <warning descr="使用不許可APIです。">IntrospectionException</warning>("message");
        } catch (<warning descr="使用不許可APIです。">IntrospectionException</warning> e) {
        }
    }
}
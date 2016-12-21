package nablarch.intellij.plugin.inspector;

public class ブラックリストに定義された例外クラスの捕捉 {

    public void method() {

        try {
            throw new <error descr="使用不許可APIです。">Exception</error>();
        } catch (<error descr="使用不許可APIです。">Exception</error> e) {
        }

        try {
            throw new <error descr="使用不許可APIです。">RuntimeException</error>();
        } catch (IllegalArgumentException | <error descr="使用不許可APIです。">NullPointerException</error> e) {
        }
    }
}
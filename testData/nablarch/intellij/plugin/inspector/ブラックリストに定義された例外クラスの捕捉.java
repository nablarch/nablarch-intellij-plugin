package nablarch.intellij.plugin.inspector;

public class ブラックリストに定義された例外クラスの捕捉 {

    public void method() {

        try {
            throw new Exception();
        } catch (<error descr="使用不許可APIです。">Exception</error> e) {
        }

        try {
            throw new Exception();
        } catch (<error descr="使用不許可APIです。">Exception</error> | <error descr="使用不許可APIです。">Error</error> e) {
        }
    }
}
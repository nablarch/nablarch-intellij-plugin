package nablarch.intellij.plugin.inspector;

public class ブラックリストに定義された例外クラスの捕捉 {

    public void method() {

        try {
            throw new Exception();
        } catch (<warning descr="使用不許可APIです。">Exception</warning> e) {
        }

        try {
            throw new Exception();
        } catch (<warning descr="使用不許可APIです。">Exception</warning> | <warning descr="使用不許可APIです。">Error</warning> e) {
        }
    }
}
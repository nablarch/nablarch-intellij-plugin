package nablarch.intellij.plugin.inspector;

public class ブラックリストに定義された例外クラスの捕捉 {

    public void method() {

        try {
            throw new <warning descr="使用不許可APIです。">Error</warning>();
        } catch (<warning descr="使用不許可APIです。">Error</warning> e) {
        }

        try {
            throw new <warning descr="使用不許可APIです。">Error</warning>();
        } catch (Exception | <warning descr="使用不許可APIです。">Error</warning> e) {
        }
    }
}
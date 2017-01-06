package nablarch.intellij.plugin.inspector;

public class ブラックリストに定義されたクラス {

    public void method() {
        <error descr="使用不許可APIです。">ProcessBuilder</error> processBuilder = new ProcessBuilder("test");
    }
}
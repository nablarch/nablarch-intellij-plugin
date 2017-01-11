package nablarch.intellij.plugin.inspector;

public class ブラックリストに定義されたクラス {

    public void method() {
        <warning descr="使用不許可APIです。">ProcessBuilder</warning> processBuilder = new ProcessBuilder("test");

        <warning descr="使用不許可APIです。">System</warning>.exit(0);
    }
}
package nablarch.intellij.plugin.inspector;

public class ブラックリストに定義されたクラス {

    public void method() {

        // クラスの変数定義
        <warning descr="使用不許可APIです。">ProcessBuilder</warning> processBuilder = null;

        // クラスのstaticメソッド
        <warning descr="使用不許可APIです。">System</warning>.exit(0);

        // コンストラクタ
        new <warning descr="使用不許可APIです。">Thread</warning>();

        // 匿名クラス
        new <warning descr="使用不許可APIです。">Runnable</warning>() {
            @Override
            public void run() {}
        };

        // ラムダ式
        final <warning descr="使用不許可APIです。">Runnable</warning> obj = <warning descr="使用不許可APIです。">() -> {}</warning>;
    }
}
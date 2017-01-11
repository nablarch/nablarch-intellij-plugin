package nablarch.intellij.plugin.inspector;

import java.io.IOException;

public class 独自定義のブラックリストで未定義の例外クラスの捕捉 {

    public void method() {

        try {
            throw new IOException();
        } catch (IOException e) {
        }

        try {
        } catch (NullPointerException | UnsupportedOperationException e) {
        }
    }
}
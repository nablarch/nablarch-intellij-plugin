package nablarch.intellij.plugin.inspector;

import java.io.IOException;

public class ブラックリストで未定義の例外クラスの捕捉 {

    public void method() {

        try {
            throw new IOException();
        } catch (IOException e) {
        }
        
        try {
            throw new IllegalStateException("error");
        } catch (RuntimeException e) {
        }

        try {
        } catch (IllegalArgumentException | IllegalStateException e) {
        }
    }
}
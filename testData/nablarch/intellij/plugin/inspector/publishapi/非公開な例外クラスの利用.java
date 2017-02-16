package nablarch.intellij.plugin.inspector;

import nablarch.exception.UnpublishedException;

/**
 *
 */
public class 非公開な例外クラスの利用 {

    public void method() {
        try {
            throw new <error descr="非公開APIです。(許可タグリスト:architect)">UnpublishedException</error>();
        } catch (<error descr="非公開APIです。(許可タグリスト:architect)">UnpublishedException</error> e){
        }

        try {
            throw new RuntimeException();
        } catch (<error descr="非公開APIです。(許可タグリスト:architect)">UnpublishedException</error> | IllegalStateException e) {
        }
    }
}

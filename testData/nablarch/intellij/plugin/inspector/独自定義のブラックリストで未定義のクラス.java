package nablarch.intellij.plugin.inspector;

import java.beans.beancontext.BeanContextSupport;

public class 独自定義のブラックリストで未定義のクラス {

    public void method() {
        ProcessBuilder processBuilder = new ProcessBuilder();

        // サブパッケージは対象とならないこと
        BeanContextSupport beanContextSupport = new BeanContextSupport();
    }
}
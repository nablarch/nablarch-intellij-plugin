package nablarch.intellij.plugin.inspector;

import java.awt.event.ActionEvent;

public class ブラックリストに定義されたパッケージ内のクラス {

    public String method(ActionEvent event) {
        return <error descr="使用不許可APIです。">event.getActionCommand</error>();
    }
}
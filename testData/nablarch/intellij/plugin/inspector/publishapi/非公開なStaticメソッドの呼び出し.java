package nablarch.intellij.plugin.inspector;

import nablarch.core.util.StringUtil;
import static nablarch.core.util.StringUtil.toArray;

import java.util.*;

public class 非公開なStaticメソッドの呼び出し {

    public void method() {
        StringUtil.<error descr="非公開APIです。(許可タグリスト:architect)">lowerAndTrimUnderScore</error>("");

        <error descr="非公開APIです。(許可タグリスト:architect)">toArray</error>(new ArrayList<>());
    }
}

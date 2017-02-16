package nablarch.intellij.plugin.inspector;

import nablarch.common.web.validator.ValidationStrategy;
import nablarch.fw.web.HttpRequest;
import nablarch.common.web.interceptor.InjectForm;
import nablarch.fw.web.servlet.ServletExecutionContext;
import java.io.*;

public class 非公開インタフェースの実装 implements  <error descr="非公開APIです。(許可タグリスト:architect)">ValidationStrategy</error> {

    @Override
    public Serializable validate(HttpRequest request, InjectForm annotation, boolean canValidate, ServletExecutionContext context) {
        return null;
    }
}

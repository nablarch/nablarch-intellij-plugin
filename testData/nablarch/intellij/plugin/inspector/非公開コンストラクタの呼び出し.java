package nablarch.intellij.plugin.inspector;

import nablarch.common.util.ParamsConvertor;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

public class 非公開コンストラクタの呼び出し {

    public HttpResponse method(HttpRequest request, ExecutionContext context) {
        ParamsConvertor convertor = new <error descr="非公開APIです。">ParamsConvertor</error>('1', '2', '3');
        return new HttpResponse("WEB-INF/view/login.html");
    }
}

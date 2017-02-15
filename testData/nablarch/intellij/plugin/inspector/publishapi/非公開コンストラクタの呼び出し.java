package nablarch.intellij.plugin.inspector;

import nablarch.common.util.ParamsConvertor;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

public class 非公開コンストラクタの呼び出し {

    public HttpResponse method(HttpRequest request, ExecutionContext context) {
        ParamsConvertor convertor = <error descr="非公開APIです。(許可タグリスト:architect)">new ParamsConvertor('1', '2', '3')</error>;
        return new HttpResponse("WEB-INF/view/login.html");
    }
}

package nablarch.intellij.plugin.inspector;

import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

public class 非公開メソッドの呼び出し {

    public HttpResponse method(HttpRequest request, ExecutionContext context) {
        context.<error descr="非公開APIです。(許可タグリスト:architect)">getHandlerQueue</error>();
        
        if (context.<error descr="非公開APIです。(許可タグリスト:architect)">getHandlerQueue</error>().isEmpty()) {
            throw new RuntimeException("error");
        }
        return new HttpResponse("WEB-INF/view/login.html");
    }
}

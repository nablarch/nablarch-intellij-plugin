package nablarch.intellij.plugin.inspector;

import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

public class 非公開メソッドの呼び出し {

    public HttpResponse method(HttpRequest request, ExecutionContext context) {
        <error descr="非公開APIです。(許可タグリスト:architect)">context.getHandlerQueue()</error>;
        
        if (<error descr="非公開APIです。(許可タグリスト:architect)">context.getHandlerQueue()</error>.isEmpty()) {
            throw new RuntimeException("error");
        }

        <error descr="非公開APIです。(許可タグリスト:architect)">context.setRequestScopedVar("hoge", "fuga")
               .getHandlerQueue()</error>;
        return new HttpResponse("WEB-INF/view/login.html");
    }
}

package nablarch.intellij.plugin.inspector;

import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpCookie;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

public class 公開クラスのコンストラクタ呼び出し {

    public HttpResponse ok(HttpRequest request, ExecutionContext context) {
        final HttpCookie cookie = new HttpCookie();
        return new HttpResponse("WEB-INF/view/login.html");
    }
}

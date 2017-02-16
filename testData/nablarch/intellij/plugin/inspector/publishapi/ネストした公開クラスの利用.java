package nablarch.intellij.plugin.inspector;

import java.lang.reflect.Method;

import nablarch.fw.Result;
import nablarch.fw.web.HttpResponse;

/**
 *
 */
public class ネストした公開クラスの利用 {

    public HttpResponse method() {
        return new HttpResponse(new Result.Success().getStatusCode());
    }
}

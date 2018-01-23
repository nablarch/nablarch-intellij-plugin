package nablarch.intellij.plugin.inspector;

import nablarch.fw.Result;
import nablarch.fw.web.HttpResponse;
import nablarch.fw.Interceptor.Impl;
import nablarch.fw.ExecutionContext;

/**
 *
 */
public class ネストした公開クラスの利用 {

    public HttpResponse method() {
        return new HttpResponse(new Result.Success().getStatusCode());
    }
    
    public static class ネスト extends Impl<Object, Object, Override> {
        
        @Override
        public Object handle(Object input, ExecutionContext context) {
            Object handler = getOriginalHandler();
            return null;
        }
    }
}

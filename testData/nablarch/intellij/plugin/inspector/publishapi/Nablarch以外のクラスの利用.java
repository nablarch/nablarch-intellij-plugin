package nablarch.intellij.plugin.inspector;

import java.lang.String;
import java.math.BigDecimal;

import org.apache.commons.httpclient.HttpClient;

public class Nablarch以外のクラスの利用 {

    public String method(String str, BigDecimal decimal) {
        final HttpClient client = new HttpClient();
        final BigDecimal result = decimal.add(decimal);
        return str.substring(0, 5);
    }
}

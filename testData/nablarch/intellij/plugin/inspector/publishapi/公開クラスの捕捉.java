package nablarch.intellij.plugin.inspector;

import nablarch.core.message.ApplicationException;
import nablarch.core.message.MessageLevel;
import nablarch.core.message.MessageUtil;
import nablarch.fw.results.TransactionAbnormalEnd;
import nablarch.test.*;

/**
 *
 */
public class 公開クラスの捕捉 {

    public void method() {
        try {
            throw new ApplicationException(MessageUtil.createMessage(MessageLevel.ERROR, ""));
        } catch (ApplicationException e) {
            
        }
    }
    
    public void method2() {
        try {
            throw new TransactionAbnormalEnd(0, "code");
        } catch (TransactionAbnormalEnd e) {
        }
    }
    
    public void method3() {
        try {
        } catch (HogeException e) {
        }
    }
}

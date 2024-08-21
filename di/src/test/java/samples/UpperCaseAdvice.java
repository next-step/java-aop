package samples;

import com.interface21.beans.factory.proxy.Advice;
import com.interface21.beans.factory.proxy.JoinPoint;

public class UpperCaseAdvice implements Advice {
    @Override
    public Object invoke(JoinPoint joinPoint) throws Throwable {
        return ((String) joinPoint.proceed()).toUpperCase();
    }
}

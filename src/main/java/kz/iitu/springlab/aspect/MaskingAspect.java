package kz.iitu.springlab.aspect;

import kz.iitu.springlab.audit.Sensitive;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Arrays;

@Aspect
@Component
@Order(4)
public class MaskingAspect {

    private static final Logger log = LoggerFactory.getLogger(MaskingAspect.class);

    @Before("kz.iitu.springlab.aspect.Pointcuts.serviceOperation()")
    public void before(JoinPoint jp) {
        log.info("[MASK] {} args={}", jp.getSignature().toShortString(), maskedArgs(jp));
    }

    /** Arguments of the call, with parameters marked @Sensitive replaced by *** */
    public static String maskedArgs(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Annotation[][] annotations = signature.getMethod().getParameterAnnotations();
        Object[] args = jp.getArgs();
        String[] result = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            boolean sensitive = Arrays.stream(annotations[i])
                    .anyMatch(a -> a instanceof Sensitive);
            result[i] = sensitive ? "***" : String.valueOf(args[i]);
        }
        return Arrays.toString(result);
    }
}

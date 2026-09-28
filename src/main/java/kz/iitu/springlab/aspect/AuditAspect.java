package kz.iitu.springlab.aspect;

import kz.iitu.springlab.audit.Audited;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;

@Aspect
@Component
@Order(1)
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    // "audited" in the expression must match the parameter name below
    @Around("@annotation(audited)")
    public Object audit(ProceedingJoinPoint pjp, Audited audited) throws Throwable {
        String action = audited.action();
        String args = audited.logArguments() ? " args=" + Arrays.toString(pjp.getArgs()) : "";
        log.info("[AUDIT] start {} at {}{}", action, Instant.now(), args);
        try {
            Object result = pjp.proceed();
            log.info("[AUDIT] {} success at {}", action, Instant.now());
            return result;
        } catch (Throwable ex) {
            log.warn("[AUDIT] {} failure at {}: {}", action, Instant.now(), ex.getMessage());
            throw ex;               // do not swallow the exception
        }
    }
}

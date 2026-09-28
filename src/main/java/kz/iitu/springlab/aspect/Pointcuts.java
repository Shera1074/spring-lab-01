package kz.iitu.springlab.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Pointcuts {

    // any method of any class in kz.iitu.springlab.service and its sub-packages
    @Pointcut("within(kz.iitu.springlab.service..*)")
    public void serviceLayer() { }

    // any public method, any return type, any arguments
    @Pointcut("execution(public * *(..))")
    public void publicMethod() { }

    @Pointcut("serviceLayer() && publicMethod()")
    public void serviceOperation() { }
}

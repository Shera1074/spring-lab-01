package kz.iitu.springlab.aspect.variants;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimited {
    int maxRequests() default 3;
    long timeWindowMs() default 10000;
}
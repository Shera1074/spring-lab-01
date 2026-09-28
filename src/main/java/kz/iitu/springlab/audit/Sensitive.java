package kz.iitu.springlab.audit;

import java.lang.annotation.*;

/** Marks a method parameter whose value must never appear in the log. */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sensitive {
}

package info.luckydog.craft.annotation;

import info.luckydog.craft.constant.PermissionEnum;

import java.lang.annotation.*;

/**
 * Permission
 *
 * @author eric
 * @since 2019/7/4
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Permission {
    PermissionEnum[] funcNecessary() default {};
}

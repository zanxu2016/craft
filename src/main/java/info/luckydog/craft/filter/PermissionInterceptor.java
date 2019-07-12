package info.luckydog.craft.filter;

import info.luckydog.craft.annotation.Permission;
import info.luckydog.craft.constant.PermissionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * PermissionFilter
 *
 * @author eric
 * @since 2019/7/4
 */
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("权限校验开始...");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Class objClass = handlerMethod.getBeanType();

        boolean hasPermission = false;

        Annotation classPermission = objClass.getAnnotation(Permission.class);
        PermissionEnum[] permissions;
        if (classPermission != null) {
            Permission classPer = (Permission) classPermission;
            permissions = classPer.funcNecessary();
            hasPermission = hasPermission(permissions);

        }

        Method method = handlerMethod.getMethod();
        PermissionEnum[] methodPermissions;
        Permission methodPermission = method.getAnnotation(Permission.class);
        if (methodPermission != null) {
            methodPermissions = methodPermission.funcNecessary();
            hasPermission = hasPermission(methodPermissions);
        }

        //鉴权通过
        if (!hasPermission) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            log.info("无权限操作！");
        }

        return hasPermission;
    }

    private boolean hasPermission(PermissionEnum[] permissions) {
        boolean hasPermission = false;
        if (permissions == null || permissions.length <= 0) {
            return false;
        }
        for (PermissionEnum permission : permissions) {
            if (permission == PermissionEnum.ADMIN) {
                hasPermission = true;
                break;
            }
        }
        return hasPermission;
    }
}

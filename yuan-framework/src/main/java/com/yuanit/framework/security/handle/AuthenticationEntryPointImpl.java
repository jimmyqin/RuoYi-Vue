package com.yuanit.framework.security.handle;

import com.yuanit.common.constant.HttpStatus;
import com.yuanit.common.core.domain.R;
import com.yuanit.common.utils.JsonUtils;
import com.yuanit.common.utils.ServletUtils;
import com.yuanit.common.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 认证失败处理类 返回未授权
 *
 * @author
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JsonUtils.writeValueAsString(R.fail(code, msg)));
    }
}

package com.yuanit.framework.security.handle;

import com.yuanit.common.constant.Constants;
import com.yuanit.common.core.domain.AjaxResult;
import com.yuanit.common.core.domain.R;
import com.yuanit.common.core.domain.model.LoginUser;
import com.yuanit.common.utils.JsonUtils;
import com.yuanit.common.utils.MessageUtils;
import com.yuanit.common.utils.ServletUtils;
import com.yuanit.common.utils.StringUtils;
import com.yuanit.framework.manager.AsyncManager;
import com.yuanit.framework.manager.factory.AsyncFactory;
import com.yuanit.framework.web.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

/**
 * 自定义退出处理类 返回成功
 *
 * @author 
 */
@RequiredArgsConstructor
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private final TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (Objects.nonNull(loginUser)) {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, MessageUtils.message("user.logout.success")));
        }
        ServletUtils.renderString(response, JsonUtils.writeValueAsString(R.ok(MessageUtils.message("user.logout.success"))));
    }
}

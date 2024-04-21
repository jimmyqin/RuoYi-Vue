package com.yuanit.web.controller.system;

import com.yuanit.common.core.controller.BaseController;
import com.yuanit.common.core.domain.R;
import com.yuanit.common.core.domain.model.RegisterBody;
import com.yuanit.common.utils.StringUtils;
import com.yuanit.framework.web.service.SysRegisterService;
import com.yuanit.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 * 
 * @author ruoyi
 */
@RequiredArgsConstructor
@RestController
public class SysRegisterController extends BaseController {

    private final SysRegisterService registerService;
    private final ISysConfigService configService;

    @PostMapping("register")
    public R register(@RequestBody RegisterBody user) {
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return R.fail("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? R.ok() : R.fail(msg);
    }
}

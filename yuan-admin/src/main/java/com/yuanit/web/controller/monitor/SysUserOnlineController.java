package com.yuanit.web.controller.monitor;

import com.yuanit.common.annotation.Log;
import com.yuanit.common.constant.CacheConstants;
import com.yuanit.common.core.controller.BaseController;
import com.yuanit.common.core.domain.R;
import com.yuanit.common.core.domain.model.LoginUser;
import com.yuanit.common.core.page.TableDataInfo;
import com.yuanit.common.core.redis.RedisCache;
import com.yuanit.common.enums.BusinessType;
import com.yuanit.common.utils.StringUtils;
import com.yuanit.system.domain.SysUserOnline;
import com.yuanit.system.service.ISysUserOnlineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 *
 * @author
 */
@Tag(name = "在线用户监控")
@RequiredArgsConstructor
@RestController
@RequestMapping("monitor/online")
public class SysUserOnlineController extends BaseController {

    private final ISysUserOnlineService userOnlineService;
    private final RedisCache redisCache;

    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @GetMapping("list")
    public TableDataInfo list(@RequestParam("ipaddr") String ipaddr, @RequestParam("userName")String userName) {
        Collection<String> keys = redisCache.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<>();
        for (String key : keys) {
            LoginUser user = redisCache.getCacheObject(key);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
            } else if (StringUtils.isNotEmpty(ipaddr)) {
                userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
            } else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
                userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
            } else {
                userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return getDataTable(userOnlineList);
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("{tokenId}")
    public R forceLogout(@PathVariable("tokenId") String tokenId) {
        redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + tokenId);
        return R.ok();
    }
}

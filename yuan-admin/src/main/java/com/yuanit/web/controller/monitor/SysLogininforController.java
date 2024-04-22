package com.yuanit.web.controller.monitor;

import com.yuanit.common.annotation.Log;
import com.yuanit.common.core.controller.BaseController;
import com.yuanit.common.core.domain.R;
import com.yuanit.common.core.page.TableDataInfo;
import com.yuanit.common.enums.BusinessType;
import com.yuanit.common.utils.poi.ExcelUtil;
import com.yuanit.framework.web.service.SysPasswordService;
import com.yuanit.system.domain.SysLogininfor;
import com.yuanit.system.service.ISysLogininforService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统访问记录
 *
 * @author
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("monitor/logininfor")
public class SysLogininforController extends BaseController {

    private final ISysLogininforService logininforService;
    private final SysPasswordService passwordService;

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:list')")
    @GetMapping("list")
    public TableDataInfo list(SysLogininfor logininfor) {
        startPage();
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        return getDataTable(list);
    }

    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:logininfor:export')")
    @PostMapping("export")
    public void export(HttpServletResponse response, SysLogininfor logininfor) {
        List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
        ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
        util.exportExcel(response, list, "登录日志");
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("{infoIds}")
    public R remove(@PathVariable("infoIds") Long[] infoIds) {
        return R.result(logininforService.deleteLogininforByIds(infoIds));
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:remove')")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("clean")
    public R clean() {
        logininforService.cleanLogininfor();
        return R.ok();
    }

    @PreAuthorize("@ss.hasPermi('monitor:logininfor:unlock')")
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @GetMapping("unlock/{userName}")
    public R unlock(@PathVariable("userName") String userName) {
        passwordService.clearLoginRecordCache(userName);
        return R.ok();
    }
}

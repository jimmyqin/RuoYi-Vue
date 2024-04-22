package com.yuanit.web.controller.monitor;

import com.yuanit.common.annotation.Log;
import com.yuanit.common.core.controller.BaseController;
import com.yuanit.common.core.domain.R;
import com.yuanit.common.core.page.TableDataInfo;
import com.yuanit.common.enums.BusinessType;
import com.yuanit.common.utils.poi.ExcelUtil;
import com.yuanit.system.domain.SysOperLog;
import com.yuanit.system.service.ISysOperLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志记录
 *
 * @author
 */
@Tag(name = "操作日志记录")
@RequiredArgsConstructor
@RestController
@RequestMapping("monitor/operlog")
public class SysOperlogController extends BaseController {

    private final ISysOperLogService operLogService;

    @PreAuthorize("@ss.hasPermi('monitor:operlog:list')")
    @GetMapping("list")
    public TableDataInfo list(SysOperLog operLog) {
        startPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:export')")
    @PostMapping("export")
    public void export(HttpServletResponse response, SysOperLog operLog) {
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        util.exportExcel(response, list, "操作日志");
    }

    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("{operIds}")
    public R remove(@PathVariable("operIds") Long[] operIds) {
        return R.result(operLogService.deleteOperLogByIds(operIds));
    }

    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @PreAuthorize("@ss.hasPermi('monitor:operlog:remove')")
    @DeleteMapping("clean")
    public R clean() {
        operLogService.cleanOperLog();
        return R.ok();
    }
}

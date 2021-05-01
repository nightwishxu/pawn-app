package com.pawn.glave.app.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.certificate.dao.FinancialRecordDao;
import com.pawn.glave.app.modules.certificate.entity.FinancialRecord;
import com.pawn.glave.app.modules.certificate.service.FinancialRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 金融记录controller
 */
@Slf4j
@RestController
@RequestMapping("/sys/financialRecord")
public class FinancialRecordController {

    @Resource
    private FinancialRecordDao financialRecordDao;

    @Resource
    private FinancialRecordService financialRecordService;

    @GetMapping("/lists")
    public R lists(@RequestParam Map<String, Object> params) {
        PageUtils pageUtils = financialRecordService.queryPage(params);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/save")
    public R save(@RequestBody FinancialRecord financialRecord) {
        log.info("financialRecord:{}", financialRecord.toString());
        if (financialRecord.getId() == null) {
            financialRecordService.save(financialRecord);
        } else {
            LambdaUpdateWrapper<FinancialRecord> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(FinancialRecord::getId, financialRecord.getId())
                    .set(FinancialRecord::getDate, financialRecord.getDate())
                    .set(FinancialRecord::getMortgageFinancial, financialRecord.getMortgageFinancial())
                    .set(FinancialRecord::getMechanism, financialRecord.getMechanism())
                    .set(FinancialRecord::getRemark, financialRecord.getRemark());
            financialRecordService.update(updateWrapper);
        }
        return R.ok();
    }

    @GetMapping("/delete/{id}")
    public R delete(@PathVariable long id) {
        financialRecordDao.deleteById(id);
        return R.ok();
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable long id) {
        LambdaQueryWrapper<FinancialRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FinancialRecord::getId, id);
        FinancialRecord financialRecord = financialRecordDao.selectOne(queryWrapper);
        return R.ok().put("data", financialRecord);
    }
}

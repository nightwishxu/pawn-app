package com.pawn.glave.app.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.pawn.glave.app.common.exception.RRException;
import com.pawn.glave.app.common.utils.PageUtils;
import com.pawn.glave.app.common.utils.R;
import com.pawn.glave.app.modules.certificate.dao.CirculationRecordDao;
import com.pawn.glave.app.modules.certificate.entity.CirculationRecord;
import com.pawn.glave.app.modules.certificate.service.CirculationRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/sys/certificateRecord")
public class CertificateRecordController {

    @Resource
    private CirculationRecordDao circulationRecordDao;

    @Resource
    private CirculationRecordService circulationRecordService;

    @GetMapping("/lists")
    public R lists(@RequestParam Map<String, Object> params) {
        PageUtils pageUtils = circulationRecordService.queryPage(params);
        return R.ok().put("page", pageUtils);
    }

    @PostMapping("/save")
    public R save(@RequestBody CirculationRecord circulationRecord) {
        log.info("circulationRecord:{}", circulationRecord.toString());
        if (circulationRecord.getCertificateId() == null) throw new RRException("参数异常");
        if (circulationRecord.getId() == null) {
            circulationRecordService.save(circulationRecord);
        } else {
            LambdaUpdateWrapper<CirculationRecord> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(CirculationRecord::getId, circulationRecord.getId())
                    .set(CirculationRecord::getDate, circulationRecord.getDate())
                    .set(CirculationRecord::getMode, circulationRecord.getMode())
                    .set(CirculationRecord::getPrice, circulationRecord.getPrice())
                    .set(CirculationRecord::getRemark, circulationRecord.getRemark())
                    .set(CirculationRecord::getTarget, circulationRecord.getTarget());
            circulationRecordService.update(updateWrapper);
        }
        return R.ok();
    }

    @GetMapping("/delete/{id}")
    public R delete(@PathVariable long id) {
        circulationRecordDao.deleteById(id);
        return R.ok();
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable long id) {
        LambdaQueryWrapper<CirculationRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CirculationRecord::getId, id);
        CirculationRecord circulationRecord = circulationRecordDao.selectOne(queryWrapper);
        return R.ok().put("data", circulationRecord);
    }
}

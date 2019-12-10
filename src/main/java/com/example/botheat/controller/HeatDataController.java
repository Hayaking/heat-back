package com.example.botheat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.botheat.bean.MessageFactory;
import com.example.botheat.entity.HeatData;
import com.example.botheat.service.HeatDataService;
import com.example.botheat.util.DateUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author haya
 */
@Log4j2
@RestController
public class HeatDataController {

    @Autowired
    private HeatDataService heatDataService;

    @GetMapping(value = "/data/{custName}/{pageNo}/{pageSize}/{startDate}/{endDate}")
    public Object findAll(@PathVariable String custName, @PathVariable Integer pageNo, @PathVariable Integer pageSize, @PathVariable long startDate, @PathVariable long endDate) {
        IPage<HeatData> res = heatDataService.getPageByDateRange( new Page<>( pageNo, pageSize ), custName, startDate, endDate );
        return MessageFactory.instance( res );
    }

    @GetMapping(value = "/data/{custName}/{startDate}/{endDate}")
    public Object findAll(@PathVariable String custName, @PathVariable long startDate, @PathVariable long endDate) {
        List<HeatData> res = heatDataService.getListByDateRange( custName, startDate, endDate );
        return MessageFactory.instance( res );
    }

    @GetMapping(value = "/data/report/day")
    public Object dayReport() {
        Date endDate = DateUtil.getDayHour( new Date(), 8 );
        Date startDate = DateUtil.getPriorDay( endDate );
        log.warn( new SimpleDateFormat().format( endDate ) );
        log.warn( new SimpleDateFormat().format( startDate ) );
        List<HeatData> list = heatDataService.findReport( startDate, endDate );
        List<HeatData> oldList = heatDataService.findReport( DateUtil.getPriorDay( startDate ), DateUtil.getPriorDay( endDate ) );
        return MessageFactory.instance( new List[]{list, oldList} );
    }

    @GetMapping(value = "/data/report/month")
    public Object monthReport() {
        Date endDate = DateUtil.getDayHour( new Date(), 8 );
        Date startDate = DateUtil.getPriorMonth( endDate );
        List<HeatData> list = heatDataService.findReport( startDate, endDate );
        List<HeatData> oldList = heatDataService.findReport( DateUtil.getPriorMonth( startDate ), DateUtil.getPriorMonth( endDate ) );
        return MessageFactory.instance( new List[]{list, oldList} );
    }
}

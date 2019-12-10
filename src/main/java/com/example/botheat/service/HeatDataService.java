package com.example.botheat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.botheat.entity.HeatData;
import com.example.botheat.util.PageModel2;

import java.util.Date;
import java.util.List;

/**
 * @author haya
 */
public interface HeatDataService extends IService<HeatData> {
    boolean add(HeatData heatData);

//    IPage<HeatData> getPageByDateRange(Page<HeatData> page,Long startDate, Long endDate);

    IPage<HeatData> getPageByDateRange(Page<HeatData> page, String custName, long startDate, long endDate);

    void addHeatData(HeatData heatData);

    void modifyHeatData(HeatData heatData);

    void delHeatData(int[] heatDataIds);

    PageModel2<HeatData> findAllHeatData(String custName, Date startdDate, Date endDate, int pageNo, int pageSize);

    HeatData findHeatDataById(int id);

    List<HeatData> findLatestHeatData();

    List<HeatData> findReport(Date startDate, Date endDate);

    List<HeatData> getListByDateRange(String custName, long startDate, long endDate);
}

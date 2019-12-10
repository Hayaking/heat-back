package com.example.botheat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.botheat.entity.HeatData;
import com.example.botheat.mapper.HeatDataMapper;
import com.example.botheat.service.HeatDataService;
import com.example.botheat.util.PageModel2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author haya
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HeatDataServiceImpl extends ServiceImpl<HeatDataMapper,HeatData> implements HeatDataService {
	@Resource
	private HeatDataMapper heatDataMapper;

    @Override
    public boolean add(HeatData heatData) {
		return heatDataMapper.upsert( heatData ) > 0;
    }

	@Override
	public IPage<HeatData> getPageByDateRange(Page<HeatData> page, String custName, long startDate, long endDate) {
		QueryWrapper<HeatData> wrapper = new QueryWrapper<>();
		wrapper.eq( "custName", custName );
		Integer total = heatDataMapper.selectCount( wrapper.between( "createDate", startDate, endDate ) );
		page.setTotal( total );
		if (total != 0) {
			List<HeatData> list = heatDataMapper.getPage( custName,(page.getCurrent()-1)*page.getSize(), page.getSize(), startDate, endDate );
			page.setRecords( list );
		}
		return page;
	}

	@Override
	public void addHeatData(HeatData heatData) {
//		return h;
	}

	@Override
	public void modifyHeatData(HeatData heatData) {

	}

	@Override
	public void delHeatData(int[] heatDataIds) {

	}

	@Override
	public PageModel2<HeatData> findAllHeatData(String custName, Date startdDate, Date endDate, int pageNo, int pageSize) {
		return null;
	}

	@Override
	public HeatData findHeatDataById(int id) {
		return null;
	}

	@Override
	public List<HeatData> findLatestHeatData() {
		return null;
	}

	@Override
	public List<HeatData> findReport(Date startDate, Date endDate) {
		return heatDataMapper.getByDateRange( startDate.getTime(), endDate.getTime() );
	}

	@Override
	public List<HeatData> getListByDateRange(String custName, long startDate, long endDate) {
		QueryWrapper<HeatData> wrapper = new QueryWrapper<>();
		wrapper.eq( "custName", custName );
		return heatDataMapper.selectList( wrapper.between( "createDate", startDate, endDate ) );
	}
//	@Resource
//	private HeatDataDao heatDataDao;
//
//	@Override
//	public void addHeatData(HeatData heatData) {
//		try {
//			heatDataDao.addHeatData(heatData);
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new AppException("添加失败!");
//		}
//	}
//
//	@Override
//	public PageModel2<HeatData> findAllHeatData(String custName, Date startDate, Date endDate,
//												int pageNo, int pageSize) {
//		try {
//			return heatDataDao.findAllHeatData(custName, startDate, endDate, pageNo, pageSize);
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new AppException("分页查询失败!");
//		}
//	}
//
//	@Override
//	public List<HeatData> findLatestHeatData() {
//		try {
//			return heatDataDao.findLatestHeatData();
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new AppException("查询失败!");
//		}
//	}
//
//	@Override
//	public void modifyHeatData(HeatData heatData) {
//		try {
//			heatDataDao.modifyHeatData(heatData);
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new AppException("修改失败!");
//		}
//	}
//
//	@Override
//	public void delHeatData(int[] heatDataIds) {
//		try {
//			heatDataDao.delHeatData(heatDataIds);
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new AppException("删除失败!");
//		}
//	}
//
//	@Override
//	public HeatData findHeatDataById(int id) {
//		try {
//			return heatDataDao.findHeatDataById(id);
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new AppException("查询失败，代码=【" + id + "】!");
//		}
//	}
//
//	@Override
//	public List<HeatData> findReport(Date startDate, Date endDate){
//		try {
//			return heatDataDao.findReport(startDate, endDate);
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new AppException("查询失败!");
//		}
//
//	}
//	public void setHeatDataDao(HeatDataDao heatDataDao) {
//		this.heatDataDao = heatDataDao;
//	}
}

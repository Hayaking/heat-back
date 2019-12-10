package com.example.botheat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.botheat.entity.HeatData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author haya
 */
@Mapper
public interface HeatDataMapper extends BaseMapper<HeatData> {
    @Insert(value = "UPSERT INTO heatData  VALUES( '${heatData.id}',${heatData.addr},'${heatData.custName}',${heatData.temperature},${heatData.pressure},${heatData.flow},${heatData.totalFlow},${heatData.monthFlow},${heatData.dayFlow},${heatData.acquireTime},${heatData.createDate})")
    int upsert(@Param("heatData") HeatData heatData);


    @Select(value = "SELECT * FROM ( SELECT * FROM heatData ORDER BY createDate DESC ) WHERE custName = #{custName} AND createDate BETWEEN ${startDate} AND ${endDate} LIMIT ${pageSize} OFFSET ${pageNo}")
    List<HeatData> getPage(@Param( "custName" ) String custName, @Param( "pageNo" ) long pageNo,@Param( "pageSize")  long pageSize,@Param( "startDate" )  long startDate,@Param( "endDate" ) long endDate);

    @Select( value = "select * from heatData  where id in (select max(id) from HeatData where acquireTime between ${startDate} and ${endDate} group by addr) order by addr" )
    List<HeatData> getByDateRange(long startDate, long endDate);

//    @Select( value = "select * from HeatData where acquireTime between ${startDate} and ${endDate}" )
//    List<HeatData> getByDateRange(long startDate, long endDate);
}

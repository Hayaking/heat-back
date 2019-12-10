package com.example.botheat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.botheat.entity.Customer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author haya
 */
@Mapper
public interface CustomMapper extends BaseMapper<Customer> {
    @Insert(value = "UPSERT INTO customer (id,gprsId,addr,custName) VALUES( '${customer.id}','${customer.gprsId}',${customer.addr},#{customer.custName})")
    int upsert(@Param("customer") Customer customer);

    @Select( value = "select * from customer LIMIT ${pageSize} OFFSET ${pageNo}")
    List<Customer> getPage(@Param( "pageNo" ) long pageNo,@Param( "pageSize" ) long pageSize);
}

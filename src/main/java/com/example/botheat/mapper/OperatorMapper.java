package com.example.botheat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.botheat.entity.Operator;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author haya
 */
@Mapper
public interface OperatorMapper extends BaseMapper<Operator> {

    @Insert(value = "UPSERT INTO operator VALUES( '${operator.id}','${operator.userName}','${operator.password}' )")
    int upsert(@Param( "operator" ) Operator operator);
}

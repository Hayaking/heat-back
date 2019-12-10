package com.example.botheat;

import cn.hutool.core.util.HashUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.botheat.entity.Customer;
import com.example.botheat.entity.HeatData;
import com.example.botheat.entity.Operator;
import com.example.botheat.mapper.CustomMapper;
import com.example.botheat.mapper.HeatDataMapper;
import com.example.botheat.mapper.OperatorMapper;
import com.example.botheat.service.HeatDataService;
import com.example.botheat.service.OperatorService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
class BotHeatApplicationTests {

    @Resource
    private OperatorMapper operatorMapper;
    @Autowired
    private OperatorService operatorService;
    @Resource
    private CustomMapper customMapper;
    @Autowired
    private HeatDataService heatDataService;
    @Resource
    private HeatDataMapper heatDataMapper;

    @Test
    void contextLoads() {
        List<Operator> insert = operatorMapper.selectList( new QueryWrapper<>() );
        log.warn( insert );
    }

    @Test
    void add() {
        Operator op = new Operator() {{
            setUserName( "ggboy" );
            setPassword( "1234" );
            setId( getUserName() + "_" + getPassword() );
        }};
        int flag = operatorMapper.upsert( op );
    }

    @Test
    void addCustomerMap() {
        String gprsStr = "13612340006,13612340007,13612340008,13612340009,13612340010,13612340011,13612340012,13612340013,13612340014,13612340015,13612340016,13612340017,13612340018,13612340019,13612340020,13612340021,13612340022,13612340023,13612340024,13612340025,13612340026,13903714072";
        final int[] i = {1};
        for (String phone : gprsStr.split( "," )) {
            customMapper.upsert( new Customer() {{
                setId( String.valueOf( HashUtil.javaDefaultHash( phone ) ) );
                setGprsId( phone );
                setAddr( 123 );
                setCustName( "customer" + (i[0]++) );
            }} );
        }
    }

    @Test
    void getHeatData() {
        List<HeatData> list = heatDataService.list();
        log.info( list );
    }

    @Test
    void insertHeatData() {
        for (int i = 0; i < 1000; i++) {
            HeatData heatData = new HeatData() {{
                setFlow( (float) (Math.random() * 20) );
                setTemperature( (float) (Math.random() * 300) );
                setPressure( (float) (Math.random() * 1.6) );
                setDayFlow( (float) (Math.random() * 20) );
                setMonthFlow( (float) (Math.random() * 20) );
                setTotalFlow( (float) (Math.random() * 20) );
                setAddr( 123 );
                setCreateDate( new Date().getTime() );
                setAcquireTime( new Date().getTime() );
            }};
            heatData.setCustName( "customer" + ((i  % 10)+1) );
            heatData.setId( String.valueOf( HashUtil.javaDefaultHash( heatData.toString() ) ) );
            heatDataService.add( heatData );
        }
    }

//    @Test
//    void getByDate() {
//        List<HeatData> list = heatDataMapper.getPage( 1, 100, 0L, 9999999999999L );
//        log.info( list );
//    }
}

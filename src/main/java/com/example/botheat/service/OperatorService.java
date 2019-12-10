package com.example.botheat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.botheat.entity.Operator;
import com.example.botheat.util.PageModel;

import java.util.UUID;

/**
 * @author haya
 */
public interface OperatorService extends IService<Operator> {

    UUID login(Operator operator);

    void addOperator(Operator operator);

    void modifyOperator(Operator operator);

    void delOperator(int[] operatorIds);

    PageModel findAllOperator(String queryString, int pageNo, int pageSize);

    Operator findOperatorById(int id);
}

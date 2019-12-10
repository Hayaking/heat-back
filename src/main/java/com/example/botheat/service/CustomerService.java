package com.example.botheat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.botheat.entity.Customer;
import com.example.botheat.util.PageModel;

import java.util.List;
import java.util.Map;

/**
 * @author haya
 */
public interface CustomerService extends IService<Customer> {

    boolean upsert(Customer customer);

    void addCustomer(Customer customer);

    void modifyCustomer(Customer customer);

    void delCustomer(int[] customerIds);

    IPage<Customer> getByPage(Page<Customer> page);

    PageModel findAllCustomer(String queryString, int pageNo, int pageSize);

    Map<String, Customer> findCustomerMap();

    Customer findCustomerById(int id);

    List<String> findCustomerName();

    IPage<Customer> getPage(Page<Customer> page);
}

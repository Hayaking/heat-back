package com.example.botheat.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.botheat.bean.MessageFactory;
import com.example.botheat.entity.Customer;
import com.example.botheat.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author haya
 */
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PutMapping(value = "/customer")
    public Object addOrUpdate(@RequestBody Customer customer) {
        customer.insertOrUpdate();
        return "main";
    }

    @DeleteMapping(value = "/customer/{id}")
    public Object delete(@PathVariable Integer id) {
        boolean res = customerService.removeById( id );
        return res;
    }

    @GetMapping(value = "/customer/{pageNum}/{pageSize}")
    public Object page(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        IPage<Customer> res = customerService.getPage( new Page<>( pageNum, pageSize ) );
        return MessageFactory.instance( res );
    }

    @GetMapping(value = "/customer/list")
    public Object list() {
        List<Customer> res = customerService.list();
        return MessageFactory.instance( res );
    }

}

package com.example.botheat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.botheat.entity.Customer;
import com.example.botheat.mapper.CustomMapper;
import com.example.botheat.service.CustomerService;
import com.example.botheat.util.PageModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author haya
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl extends ServiceImpl<CustomMapper, Customer> implements CustomerService {
    @Resource
    private CustomMapper customMapper;

    @Override
    public boolean upsert(Customer customer) {
        return customMapper.upsert( customer ) > 0;
    }

    @Override
    public void addCustomer(Customer customer) {

    }

    @Override
    public void modifyCustomer(Customer customer) {

    }

    @Override
    public void delCustomer(int[] customerIds) {

    }

    @Override
    public IPage<Customer> getByPage(Page<Customer> page) {
        return customMapper.selectPage( page, new QueryWrapper<>() );
    }

    @Override
    public PageModel findAllCustomer(String queryString, int pageNo, int pageSize) {
        return null;
    }
//    @Override
//    public PageModel<Customer> findAllCustomer(String queryString, int pageNo, int pageSize) {
//        try {
//            return customerDao.findAllCustomer( queryString, pageNo, pageSize );
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new AppException("分页查询失败!");
//        }
//    }

    //    @Override
//    public Map<String, Customer> findCustomerMap() {
//        Map<String, Customer> map = new HashMap<>();
//        PageModel<Customer> pageModel = findAllCustomer( null, 1, Integer.MAX_VALUE );
//        if (pageModel == null) {
//            return new HashMap<>();
//        }
//        List<Customer> list = pageModel
//								.getList();
//        if (list == null) {
//            return map;
//        }
//        for (Customer c : list) {
//            map.put( c.getGprsId(), c );
//        }
//        return map;
//
//    }

    @Override
    public Map<String, Customer> findCustomerMap() {
        Map<String, Customer> map = new HashMap<>();
        customMapper.selectList( new QueryWrapper<>() ).forEach( item->{
            map.put( item.getGprsId(), item );
        } );
        return map;
    }

    @Override
    public Customer findCustomerById(int id) {
        return null;
    }

    @Override
    public List<String> findCustomerName() {
        return null;
    }

    @Override
    public IPage<Customer> getPage(Page<Customer> page) {
        Integer total = customMapper.selectCount( new QueryWrapper<>() );
        List<Customer> list = customMapper.getPage( (page.getCurrent() - 1) * page.getSize(), page.getSize() );
        page.setTotal( total );
        page.setRecords( list );
        return page;
    }

//    @Resource
//    private CustomerDao customerDao;
//
//    @Override
//    public void addCustomer(Customer customer) {
//        try {
//            customerDao.addCustomer( customer );
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new AppException("添加失败!");
//        }
//    }
//

//
//    @Override
//    public void modifyCustomer(Customer customer) {
//        try {
//            customerDao.modifyCustomer( customer );
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new AppException("修改失败!");
//        }
//    }
//
//    @Override
//    public void delCustomer(int[] customerIds) {
//        try {
//            customerDao.delCustomer( customerIds );
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new AppException("删除失败!");
//        }
//    }
//
//    @Override
//    public Customer findCustomerById(int id) {
//        try {
//            return customerDao.findCustomerById( id );
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new AppException("查询失败，代码=【" + id + "】!");
//        }
//    }
//

//
//    @Override
//    public List<String> findCustomerName() {
//        List<String> nameList = new ArrayList<>();
//        List<Customer> list = findAllCustomer( null, 1, Integer.MAX_VALUE ).getList();
//        for (Customer c : list) {
//            nameList.add( c.getCustName() );
//        }
//        return nameList;
//
//    }
//
//    public void setCustomerDao(CustomerDao customerDao) {
//        this.customerDao = customerDao;
//    }
}

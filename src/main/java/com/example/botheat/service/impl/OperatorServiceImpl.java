package com.example.botheat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.botheat.entity.Operator;
import com.example.botheat.mapper.OperatorMapper;
import com.example.botheat.service.OperatorService;
import com.example.botheat.service.UUIDService;
import com.example.botheat.util.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;


/**
 * @author haya
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OperatorServiceImpl extends ServiceImpl<OperatorMapper,Operator> implements OperatorService {
	@Resource
	private OperatorMapper operatorMapper;

	@Autowired
	private UUIDService uuidService;

	@Override
	public UUID login(Operator operator) {
		QueryWrapper<Operator> wrapper = new QueryWrapper<>();
		wrapper.eq( "username", operator.getUserName() ).eq( "password", operator.getPassword() );
		if (null != operatorMapper.selectOne( wrapper )) {
			return uuidService.add( operator );
		}
		return null;
	}

	@Override
	public void addOperator(Operator operator) {

	}

	@Override
	public void modifyOperator(Operator operator) {

	}

	@Override
	public void delOperator(int[] operatorIds) {

	}

	@Override
	public PageModel findAllOperator(String queryString, int pageNo, int pageSize) {
		return null;
	}

	@Override
	public Operator findOperatorById(int id) {
		return null;
	}

//	@Autowired
//	private OperatorDao operatorDao;
//
//	@Override
//	public void addOperator(Operator operator) {
//		try {
//			operatorDao.addOperator(operator);
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new AppException("���ʧ��!");
//		}
//	}
//
//	@Override
//	@SuppressWarnings("unchecked")
//	public PageModel findAllOperator(String queryString,
//									 int pageNo, int pageSize) {
//		try {
//			return operatorDao.findAllOperator(queryString, pageNo, pageSize);
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new AppException("��ҳ��ѯʧ��!");
//		}
//	}
//
//	@Override
//	public void modifyOperator(Operator operator) {
//		try {
//			operatorDao.modifyOperator(operator);
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new AppException("�޸�ʧ��!");
//		}
//	}
//
//	@Override
//	public void delOperator(int[] operatorIds) {
//		try {
//			operatorDao.delOperator(operatorIds);
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new AppException("ɾ��ʧ��!");
//		}
//	}
//
//	@Override
//	public Operator findOperatorById(int id) {
//		try {
//			return operatorDao.findOperatorById(id);
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new AppException("��ѯʧ�ܣ�����=��" + id + "��!");
//		}
//	}
//
//	public void setOperatorDao(OperatorDao operatorDao) {
//		this.operatorDao = operatorDao;
//	}
}

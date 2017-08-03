/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.test.entity.TestCustomer;
import com.thinkgem.jeesite.modules.test.dao.TestCustomerDao;

/**
 * 单表生成Service
 * @author ThinkGem
 * @version 2017-07-27
 */
@Service
@Transactional(readOnly = true)
public class TestCustomerService extends CrudService<TestCustomerDao, TestCustomer> {

	public TestCustomer get(String id) {
		return super.get(id);
	}
	
	public List<TestCustomer> findList(TestCustomer testCustomer) {
		return super.findList(testCustomer);
	}
	
	public Page<TestCustomer> findPage(Page<TestCustomer> page, TestCustomer testCustomer) {
		return super.findPage(page, testCustomer);
	}
	
	@Transactional(readOnly = false)
	public void save(TestCustomer testCustomer) {
		super.save(testCustomer);
	}
	
	@Transactional(readOnly = false)
	public void delete(TestCustomer testCustomer) {
		super.delete(testCustomer);
	}
	
}
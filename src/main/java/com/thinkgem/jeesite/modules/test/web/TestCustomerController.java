/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.test.entity.TestCustomer;
import com.thinkgem.jeesite.modules.test.service.TestCustomerService;

/**
 * 单表生成Controller
 * @author ThinkGem
 * @version 2017-07-27
 */
@Controller
@RequestMapping(value = "${adminPath}/test/testCustomer")
public class TestCustomerController extends BaseController {

	@Autowired
	private TestCustomerService testCustomerService;
	
	@ModelAttribute
	public TestCustomer get(@RequestParam(required=false) String id) {
		TestCustomer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testCustomerService.get(id);
		}
		if (entity == null){
			entity = new TestCustomer();
		}
		return entity;
	}
	
	@RequiresPermissions("test:testCustomer:view")
	@RequestMapping(value = {"list", ""})
	public String list(TestCustomer testCustomer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestCustomer> page = testCustomerService.findPage(new Page<TestCustomer>(request, response), testCustomer); 
		model.addAttribute("page", page);
		return "modules/test/testCustomerList";
	}

	@RequiresPermissions("test:testCustomer:view")
	@RequestMapping(value = "form")
	public String form(TestCustomer testCustomer, Model model) {
		model.addAttribute("testCustomer", testCustomer);
		return "modules/test/testCustomerForm";
	}

	@RequiresPermissions("test:testCustomer:edit")
	@RequestMapping(value = "save")
	public String save(TestCustomer testCustomer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, testCustomer)){
			return form(testCustomer, model);
		}
		testCustomerService.save(testCustomer);
		addMessage(redirectAttributes, "保存单表成功");
		return "redirect:"+Global.getAdminPath()+"/test/testCustomer/?repage";
	}
	
	@RequiresPermissions("test:testCustomer:edit")
	@RequestMapping(value = "delete")
	public String delete(TestCustomer testCustomer, RedirectAttributes redirectAttributes) {
		testCustomerService.delete(testCustomer);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/test/testCustomer/?repage";
	}

}
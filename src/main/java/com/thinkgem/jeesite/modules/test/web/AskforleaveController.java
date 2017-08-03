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
import com.thinkgem.jeesite.modules.act.entity.Act;
import com.thinkgem.jeesite.modules.test.entity.Askforleave;
import com.thinkgem.jeesite.modules.test.service.AskforleaveService;

/**
 * 单表生成Controller
 * @author ThinkGem
 * @version 2017-08-02
 */
@Controller
@RequestMapping(value = "${adminPath}/test/askforleave")
public class AskforleaveController extends BaseController {

	@Autowired
	private AskforleaveService askforleaveService;
	
	@ModelAttribute
	public Askforleave get(@RequestParam(required=false) String id) {
		Askforleave entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = askforleaveService.get(id);
		}
		if (entity == null){
			entity = new Askforleave();
		}
//		if(entity.getAct() == null) {
//			Act act = new Act();
//			entity.setAct(act);
//		}
		return entity;
	}
	
	@RequiresPermissions("test:askforleave:view")
	@RequestMapping(value = {"list", ""})
	public String list(Askforleave askforleave, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Askforleave> page = askforleaveService.findPage(new Page<Askforleave>(request, response), askforleave); 
		model.addAttribute("page", page);
		return "modules/test/askforleaveList";
	}

	@RequiresPermissions("test:askforleave:view")
	@RequestMapping(value = "form")
	public String form(Askforleave askforleave, Model model) {
		String view = "askforleaveForm";
		
		if (StringUtils.isNotBlank(askforleave.getId())){
			// 环节编号
			String taskDefKey = askforleave.getAct().getTaskDefKey();
			System.out.println("askforleave.getAct().isFinishTask():" + askforleave.getAct().isFinishTask());
			System.out.println("taskDefKey:" + taskDefKey);
			if(askforleave.getAct().isFinishTask() || taskDefKey != null) {
				view = "askforleaveflow";
			}
		}
		model.addAttribute("askforleave", askforleave);
		return "modules/test/" + view;
	}

	@RequiresPermissions("test:askforleave:edit")
	@RequestMapping(value = "save")
	public String save(Askforleave askforleave, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, askforleave)){
			return form(askforleave, model);
		}
		askforleaveService.save(askforleave);
		//addMessage(redirectAttributes, "保存单表成功");
		addMessage(redirectAttributes, "提交审批'" + askforleave.getApplicant() + "'成功");
		//return "redirect:"+Global.getAdminPath()+"/test/askforleave/?repage";
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("test:askforleave:edit")
	@RequestMapping(value = "saveFlow")
	public String saveFlow(Askforleave askforleave, Model model) {
		if (StringUtils.isBlank(askforleave.getAct().getFlag())
				|| StringUtils.isBlank(askforleave.getAct().getComment())){
			addMessage(model, "请填写审核意见。");
			return form(askforleave, model);
		}
		//这一步可以定义一个新的方法 ，将之后的一些数据重新添加到数据库中
		askforleaveService.save(askforleave);
		//还是需要调用save类似的方法，在方法中调用执行节点的方法
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	
	
	@RequiresPermissions("test:askforleave:edit")
	@RequestMapping(value = "delete")
	public String delete(Askforleave askforleave, RedirectAttributes redirectAttributes) {
		askforleaveService.delete(askforleave);
		addMessage(redirectAttributes, "删除单表成功");
		return "redirect:"+Global.getAdminPath()+"/test/askforleave/?repage";
	}

}
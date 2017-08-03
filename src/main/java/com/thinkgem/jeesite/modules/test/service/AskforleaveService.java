/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.h2.util.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.test.entity.Askforleave;
import com.thinkgem.jeesite.modules.test.dao.AskforleaveDao;

/**
 * 单表生成Service
 * @author ThinkGem
 * @version 2017-08-02
 */
@Service
@Transactional(readOnly = true)
public class AskforleaveService extends CrudService<AskforleaveDao, Askforleave> {
	
	@Autowired
	private ActTaskService actTaskService;

	public Askforleave get(String id) {
		return super.get(id);
	}
	
	public List<Askforleave> findList(Askforleave askforleave) {
		return super.findList(askforleave);
	}
	
	public Page<Askforleave> findPage(Page<Askforleave> page, Askforleave askforleave) {
		return super.findPage(page, askforleave);
	}
	
	@Transactional(readOnly = false)
	public void save(Askforleave askforleave) {
		//super.save(askforleave);
		// 申请发起
		if (StringUtils.isBlank(askforleave.getId())){
			askforleave.preInsert();
			dao.insert(askforleave);
			Map<String, Object> map = Maps.newHashMap();
			map.put("ismanager", "yes".equals(askforleave.getIsmanager())? "1" : "0");
			// 启动流程
			String  proInsId = actTaskService.startProcess(ActUtils.PD_ASKFORLEAVE[0], ActUtils.PD_ASKFORLEAVE[1], askforleave.getId(), "请假流程：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			actTaskService.taskForward(proInsId, map);
			// 完成流程任务
//			Map<String, Object> vars = Maps.newHashMap();
//			vars.put("ismanager", "yes".equals(askforleave.getIsmanager())? "1" : "0");
//			actTaskService.complete(askforleave.getAct().getTaskId(), askforleave.getAct().getProcInsId(), askforleave.getAct().getComment(), "请假流程：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), vars);
		}else {
			// 重新编辑申请		
			askforleave.preUpdate();
			askforleave.setComment(askforleave.getAct().getComment());
			dao.updateComment(askforleave);
			askforleave.getAct().setComment(("yes".equals(askforleave.getAct().getFlag())?"[重申] ":"[销毁] ")+askforleave.getAct().getComment());
			
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("ismanager", "yes".equals(askforleave.getIsmanager())? "1" : "0");
			actTaskService.complete(askforleave.getAct().getTaskId(), askforleave.getAct().getProcInsId(), askforleave.getAct().getComment(), "请假流程：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), vars);
			}
		}
	
	@Transactional(readOnly = false)
	public void delete(Askforleave askforleave) {
		super.delete(askforleave);
	}
	
}
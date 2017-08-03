/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.TestAudit;
import com.thinkgem.jeesite.modules.test.entity.Askforleave;

/**
 * 单表生成DAO接口
 * @author ThinkGem
 * @version 2017-08-02
 */
@MyBatisDao
public interface AskforleaveDao extends CrudDao<Askforleave> {
	public Askforleave getByProcInsId(String procInsId);
	
	public int updateComment(Askforleave askforleave);
	
}
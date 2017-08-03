/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.ActEntity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author ThinkGem
 * @version 2017-08-02
 */
public class Askforleave extends ActEntity<Askforleave> {
	
	private static final long serialVersionUID = 1L;
	private String applicant;		// 请假人
	private String leavetype;		// 请假类型
	private String reason;		// 请假原因
	private Date begintime;		// 开始时间
	private Date endtime;		// 结束时间
	private String ismanager;		// 是否是经理
	private String comment;
	
	public Askforleave() {
		super();
	}

	public Askforleave(String id){
		super(id);
	}

	@Length(min=0, max=50, message="请假人长度必须介于 0 和 50 之间")
	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	
	@Length(min=0, max=100, message="请假类型长度必须介于 0 和 100 之间")
	public String getLeavetype() {
		return leavetype;
	}

	public void setLeavetype(String leavetype) {
		this.leavetype = leavetype;
	}
	
	@Length(min=0, max=100, message="请假原因长度必须介于 0 和 100 之间")
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	@Length(min=0, max=100, message="是否是经理长度必须介于 0 和 100 之间")
	public String getIsmanager() {
		return ismanager;
	}

	public void setIsmanager(String ismanager) {
		this.ismanager = ismanager;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
}
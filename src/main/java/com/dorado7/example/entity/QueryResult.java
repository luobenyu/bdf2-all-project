/**
 * QueryResult.java
 * com.dorado7.example.entity
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-12-24 		小山
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
 */

package com.dorado7.example.entity;

import java.util.List;

import com.bstek.dorado.annotation.PropertyDef;
import com.bstek.ureport.el.parser.ElParser.primary_return;

/**
 * ClassName:QueryResult Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author 小山
 * @version
 * @since Ver 1.1
 * @Date 2015-12-24 上午9:54:33
 * 
 * @see
 */
public class QueryResult {

	private String id;
	@PropertyDef(label = "法人/自然人名称")
	private String name;
	@PropertyDef(label = "记录类型")
	private String type;
	@PropertyDef(label = "主体类型")
	private String objectType;
	@PropertyDef(label = "优良记录数")
	private String goodCount;
	@PropertyDef(label = "不良记录数")
	private String badCount;
	@PropertyDef(label = "失信记录数")
	private String dishonestyCount;
	
	@PropertyDef(label = "信息来源")
	private String source;
	@PropertyDef(label = "更新时间")
	private String time;
	
	private String encryStr;
	private String content;
	private List<LostCredit> details;
	private String str;
	
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<LostCredit> getDetails() {
		return details;
	}

	public void setDetails(List<LostCredit> details) {
		this.details = details;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(String goodCount) {
		this.goodCount = goodCount;
	}

	public String getBadCount() {
		return badCount;
	}

	public void setBadCount(String badCount) {
		this.badCount = badCount;
	}

	public String getDishonestyCount() {
		return dishonestyCount;
	}

	public void setDishonestyCount(String dishonestyCount) {
		this.dishonestyCount = dishonestyCount;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEncryStr() {
		return encryStr;
	}

	public void setEncryStr(String encryStr) {
		this.encryStr = encryStr;
	}

}

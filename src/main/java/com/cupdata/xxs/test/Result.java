/**
 * Result.java
 * com.cupdata.xxs.test
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-12-21 		小山
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.cupdata.xxs.test;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * ClassName:Result
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   小山
 * @version  
 * @since    Ver 1.1
 * @Date	 2015-12-21		下午7:37:49
 *
 * @see 	 
 */
public class Result {
	
	private String id;
	private String name;
	private String type;
	private String objectType;
	private String goodCount;
	private String badCount;
	private String dishonestyCount;
	
	private String source;
	private String time;
	private String encryStr;
	
	private List<Detail> details;
	
	public List<Detail> getDetails() {
		return details;
	}
	public void setDetails(List<Detail> details) {
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


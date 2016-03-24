/**
 * Detail.java
 * com.cupdata.xxs.test
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-12-22 		小山
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.cupdata.xxs.test;
/**
 * ClassName:Detail
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   小山
 * @version  
 * @since    Ver 1.1
 * @Date	 2015-12-22		上午9:34:37
 *
 * @see 	 
 */
public class Detail {
	
	/**信息来源*/
	private String source;
	/**发布时间*/
	private String releaseDate;
	/**企业/自然人名称*/
	private String name;
	/**组织机构代码/身份证号*/
	private String certno;
	/**案号*/
	private String caseno;
	/**立案时间*/
	private String casedate;
	/**性别*/
	private String gender;
	/**执行法院*/
	private String executiveCourt;
	/**省份*/
	private String province;
	/**执行依据文号*/
	private String referNo;
	/**作出执行依据单位*/
	private String referCompany;
	/**法律生效文书确定的义务*/
	private String obligation;
	/**被执行人的履行情况*/
	private String executiveState;
	/**失信被执行人具体情形*/
	private String situation;
	
	public String getSource() {
		return source;
	}
	@DetailName("信息来源")
	public void setSource(String source) {
		this.source = source;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	@DetailName("发布时间")
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getName() {
		return name;
	}
	@DetailName("企业/自然人名称")
	public void setName(String name) {
		this.name = name;
	}
	public String getCertno() {
		return certno;
	}
	@DetailName("组织机构代码/身份证号")
	public void setCertno(String certno) {
		this.certno = certno;
	}
	public String getCaseno() {
		return caseno;
	}
	@DetailName("案号")
	public void setCaseno(String caseno) {
		this.caseno = caseno;
	}
	public String getCasedate() {
		return casedate;
	}
	@DetailName("立案时间")
	public void setCasedate(String casedate) {
		this.casedate = casedate;
	}
	public String getGender() {
		return gender;
	}
	@DetailName("性别")
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getExecutiveCourt() {
		return executiveCourt;
	}
	@DetailName("执行法院")
	public void setExecutiveCourt(String executiveCourt) {
		this.executiveCourt = executiveCourt;
	}
	public String getProvince() {
		return province;
	}
	@DetailName("省份")
	public void setProvince(String province) {
		this.province = province;
	}
	public String getReferNo() {
		return referNo;
	}
	@DetailName("执行依据文号")
	public void setReferNo(String referNo) {
		this.referNo = referNo;
	}
	public String getReferCompany() {
		return referCompany;
	}
	@DetailName("执行依据文号")
	public void setReferCompany(String referCompany) {
		this.referCompany = referCompany;
	}
	public String getObligation() {
		return obligation;
	}
	@DetailName("法律生效文书确定的义务")
	public void setObligation(String obligation) {
		this.obligation = obligation;
	}
	public String getExecutiveState() {
		return executiveState;
	}
	@DetailName("被执行人的履行情况")
	public void setExecutiveState(String executiveState) {
		this.executiveState = executiveState;
	}
	public String getSituation() {
		return situation;
	}
	@DetailName("失信被执行人具体情形")
	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	
	
}


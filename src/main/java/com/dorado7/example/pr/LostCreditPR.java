/**
 * LostCreditPR.java
 * com.dorado7.example.pr
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-12-25 		小山
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.dorado7.example.pr;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.dorado7.example.entity.QueryResult;
import com.dorado7.example.service.QueryResultService;

/**
 * ClassName:LostCreditPR
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   小山
 * @version  
 * @since    Ver 1.1
 * @Date	 2015-12-25		上午9:56:34
 *
 * @see 	 
 */
@Component(LostCreditPR.BEAN_ID)
public class LostCreditPR {
	public static final String BEAN_ID = "lostCreditPR";
	
	@Autowired
	private QueryResultService queryResultService;
	
	@DataProvider
	public List<QueryResult> findAll(Map<String,String> parameter) throws Exception{
		String name = null;
		if(parameter.get("name")!=null&&!"".equals(parameter.get("name")))
			name = parameter.get("name");
		return queryResultService.findALl(name);
	}
	
}


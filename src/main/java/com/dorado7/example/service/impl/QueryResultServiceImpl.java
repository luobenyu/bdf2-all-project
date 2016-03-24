/**
 * QueryResultServiceImpl.java
 * com.dorado7.example.service.impl
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-12-25 		小山
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.dorado7.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dorado7.example.dao.QueryResultDao;
import com.dorado7.example.entity.QueryResult;
import com.dorado7.example.service.QueryResultService;

/**
 * ClassName:QueryResultServiceImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   小山
 * @version  
 * @since    Ver 1.1
 * @Date	 2015-12-25		上午9:42:05
 *
 * @see 	 
 */
@Service(QueryResultServiceImpl.BEAN_ID)
public class QueryResultServiceImpl implements QueryResultService {

	public static final String BEAN_ID = "QueryResultService";
	
	@Autowired
	private QueryResultDao queryResultDao;
	
	@Override
	public List<QueryResult> findALl(String str) throws Exception {
		return queryResultDao.findALl(str);
	}

}


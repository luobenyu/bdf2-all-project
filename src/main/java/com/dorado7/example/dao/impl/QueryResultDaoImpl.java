/**
 * QueryResultDaoImpl.java
 * com.dorado7.example.dao.impl
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-12-25 		小山
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.dorado7.example.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dorado7.example.dao.QueryResultDao;
import com.dorado7.example.entity.QueryResult;
import com.dorado7.example.utils.HttpUtils;

/**
 * ClassName:QueryResultDaoImpl
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   小山
 * @version  
 * @since    Ver 1.1
 * @Date	 2015-12-25		上午9:35:30
 *
 * @see 	 
 */
@Repository(QueryResultDaoImpl.BEAN_ID)
public class QueryResultDaoImpl implements QueryResultDao {
	
	public static final String BEAN_ID = "QueryResultDao";
	
	@Override
	public List<QueryResult> findALl(String str) throws Exception{
		return HttpUtils.search(str);
	}

}


/**
 * QueryResultDao.java
 * com.dorado7.example.dao
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-12-24 		小山
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.dorado7.example.dao.copy;

import java.util.List;

import com.dorado7.example.entity.QueryResult;


/**
 * ClassName:QueryResultDao
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   小山
 * @version  
 * @since    Ver 1.1
 * @Date	 2015-12-24		上午10:15:23
 *
 * @see 	 
 */
public interface QueryResultDao{
	List<QueryResult> findALl(String str) throws Exception;
}


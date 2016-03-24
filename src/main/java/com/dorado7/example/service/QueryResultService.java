/**
 * QueryResultService.java
 * com.dorado7.example.service
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-12-25 		小山
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.dorado7.example.service;

import java.util.List;

import com.dorado7.example.entity.QueryResult;

/**
 * ClassName:QueryResultService
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   小山
 * @version  
 * @since    Ver 1.1
 * @Date	 2015-12-25		上午9:40:50
 *
 * @see 	 
 */
public interface QueryResultService {
	List<QueryResult> findALl(String str) throws Exception;
}


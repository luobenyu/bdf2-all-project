/**
 * DetailName.java
 * com.dorado7.example.annotation
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-12-24 		小山
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.dorado7.example.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ClassName:DetailName
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   小山
 * @version  
 * @since    Ver 1.1
 * @Date	 2015-12-24		上午9:57:47
 *
 * @see 	 
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DetailName {
	String value();
}


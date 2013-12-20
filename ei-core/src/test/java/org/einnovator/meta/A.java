/**
 * 
 */
package org.einnovator.meta;

import javax.management.MXBean;

import org.einnovator.convert.NumberFormat;


@MXBean class A {
	String name;

	@NumberFormat
	double balance;
	
	public A() {}
}
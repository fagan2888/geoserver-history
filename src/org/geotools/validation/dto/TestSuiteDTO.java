/*
 * Created on Jan 14, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.geotools.validation.dto;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * TestSuiteDTO purpose.
 * <p>
 * Description of TestSuiteDTO ...
 * </p>
 * 
 * <p>
 * Capabilities:
 * </p>
 * <ul>
 * <li>
 * Feature: description
 * </li>
 * </ul>
 * <p>
 * Example Use:
 * </p>
 * <pre><code>
 * TestSuiteDTO x = new TestSuiteDTO(...);
 * </code></pre>
 * 
 * @author dzwiers, Refractions Research, Inc.
 * @author $Author: dmzwiers $ (last modification)
 * @version $Id: TestSuiteDTO.java,v 1.3 2004/01/19 23:54:56 dmzwiers Exp $
 */
public class TestSuiteDTO {
	/** the test suite name */
	private String name;
	
	/** the test suite description */
	private String description;
	
	/** the list of tests */
	private List tests;
	
	/**
	 * TestSuiteDTO constructor.
	 * <p>
	 * Does nothing
	 * </p>
	 */
	public TestSuiteDTO(){}
	
	/**
	 * TestSuiteDTO constructor.
	 * <p>
	 * Creates a copy of the TestSuiteDTO passed in.
	 * </p>
	 * @param ts The Test Suite to copy
	 */
	public TestSuiteDTO(TestSuiteDTO ts){
		name = ts.getName();
		description = ts.getDescription();
		tests = new LinkedList();
		Iterator i = ts.getTests().iterator();
		while(i.hasNext()){
			TestDTO t = (TestDTO)i.next();
			tests.add(new TestDTO(t));
		}
	}
	
	/**
	 * Implementation of clone.
	 * 
	 * @see java.lang.Object#clone()
	 * 
	 * @return An instance of TestSuiteDTO.
	 */
	public Object clone(){
		return new TestSuiteDTO(this);
	}
	
	public int hashCode(){
		int r = 1;
		if(tests !=null)
			r *= tests.hashCode();
		if(name != null)
			r *= name.hashCode();
		if(description != null)
			r *= description.hashCode();
		return r;
	}
	
	/**
	 * Implementation of equals.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * @param obj An object to compare for equality.
	 * @return true when the objects have the same data in the same order.
	 */
	public boolean equals(Object obj){
		if(obj == null || !(obj instanceof TestSuiteDTO))
			return false;
		boolean r = true;
		TestSuiteDTO ts = (TestSuiteDTO)obj;
		
		if(name!=null)
		r = r && (name.equals(ts.getName()));
		if(description!=null)
		r = r && (description.equals(ts.getDescription()));
		
		if(tests == null){
			if(ts.getTests()!=null)
				return false;
		}else{
			if(ts.getTests()!=null){
				r = r && tests.equals(ts.getTests());
			}else{
				return false;
			}
		}
		
		return r;
	}
	/**
	 * Access description property.
	 * 
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set description to description.
	 *
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Access name property.
	 * 
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set name to name.
	 *
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Access tests property.
	 * 
	 * @return Returns the tests.
	 */
	public List getTests() {
		return tests;
	}

	/**
	 * Set tests to tests.
	 *
	 * @param tests The tests to set.
	 */
	public void setTests(List tests) {
		this.tests = tests;
	}

}

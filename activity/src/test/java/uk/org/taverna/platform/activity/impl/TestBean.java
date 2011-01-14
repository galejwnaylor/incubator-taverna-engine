/*******************************************************************************
 * Copyright (C) 2010 The University of Manchester   
 * 
 *  Modifications to the initial code base are copyright of their
 *  respective authors, or their employers as appropriate.
 * 
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *    
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *    
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 ******************************************************************************/
package uk.org.taverna.platform.activity.impl;

import java.util.HashSet;
import java.util.List;

import net.sf.taverna.t2.workflowmodel.processor.activity.config.ConfigurationBean;
import net.sf.taverna.t2.workflowmodel.processor.activity.config.ConfigurationProperty;

/**
 * 
 * @author David Withers
 */
@ConfigurationBean(uri = ActivityServiceImplTest.annotatedBeanURI + "/configuration")
public class TestBean {

	public String stringType, optionalStringType;
	public int integerType;
	public long longType;
	public float floatType;
	public double doubleType;
	public boolean booleanType;
	public TestEnum enumType;
	public TestBean2 beanType, beanType2;
	public String[] arrayType;
	public List<String> listType;
	public HashSet<String> setType;
	
	@ConfigurationProperty(name = "stringType")
	public void setStringType(String parameter) {
		stringType = parameter;
	}

	@ConfigurationProperty(name = "optionalStringType", required = false)
	public void setOptionalStringType(String parameter) {
		optionalStringType = parameter;
	}

	@ConfigurationProperty(name = "integerType")
	public void setIntegerType(int parameter) {
		integerType = parameter;
	}

	@ConfigurationProperty(name = "longType")
	public void setLongType(long parameter) {
		longType = parameter;
	}

	@ConfigurationProperty(name = "floatType")
	public void setFloatType(float parameter) {
		floatType = parameter;
	}

	@ConfigurationProperty(name = "doubleType")
	public void setDoubleType(double parameter) {
		doubleType = parameter;
	}

	@ConfigurationProperty(name = "booleanType")
	public void setBooleanType(boolean parameter) {
		booleanType = parameter;
	}

	@ConfigurationProperty(name = "enumType")
	public void setEnumType(TestEnum parameter) {
		enumType = parameter;
	}

	@ConfigurationProperty(name = "beanType")
	public void setBeanType(TestBean2 parameter) {
		beanType = parameter;
	}

	@ConfigurationProperty(name = "beanType2")
	public void setBeanType2(TestBean2 parameter) {
		beanType2 = parameter;
	}

	@ConfigurationProperty(name = "arrayType")
	public void setArrayType(String[] parameter) {
		arrayType = parameter;
	}

	@ConfigurationProperty(name = "listType")
	public void setListType(List<String> parameter) {
		listType = parameter;
	}

	@ConfigurationProperty(name = "setType")
	public void setSetType(HashSet<String> parameter) {
		setType = parameter;
	}

}

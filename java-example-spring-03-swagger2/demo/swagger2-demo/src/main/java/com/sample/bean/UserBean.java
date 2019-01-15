/**
 * 
 */
package com.sample.bean;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author llei
 *
 */
public class UserBean {
	
	@ApiModelProperty(value = "用户的姓名")
	public String name;
	
	@ApiModelProperty(value = "用户的年龄")
	public Integer age;

	@ApiModelProperty(value = "用户的性别")
	public boolean sex;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	
	@Override
	public String toString() {
		return "UserBean [name=" + name + ", age=" + age + ", sex=" + sex + "]";
	}

}

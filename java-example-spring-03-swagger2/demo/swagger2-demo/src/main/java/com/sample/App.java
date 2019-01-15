/**
 * 
 */
package com.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.bean.UserBean;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author llei
 *
 */
@SpringBootApplication
@RestController
@EnableSwagger2
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SpringApplication.run(App.class, args);

	}
	
	@GetMapping("/hello")
	@ApiOperation(value = "返回问候语")
	public String greetings(@ApiParam(value = "问候的用户名字") String name) {
		
		return "Hello Spring Boot! " + name;
		
	}
	
	@PostMapping("/add")
	public UserBean addUser(UserBean userBean) {
		
		return userBean;
		
	}

}

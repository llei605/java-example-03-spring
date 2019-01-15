# 如何使用Swagger2

1. 用下面步骤新建一个Maven父工程；

   ![1542113310777](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542113310777.png)

   ![1542113345122](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542113345122.png)

   ![1542113401842](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542113401842.png)

   ![1542113522155](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542113522155.png)

2. 添加下面依赖；

   ``` xml
   <dependencyManagement>
       <dependencies>
           <dependency>
               <groupId>io.spring.platform</groupId>
               <artifactId>platform-bom</artifactId>
               <version>Brussels-SR13</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
       </dependencies>
   </dependencyManagement>
   
   <build>
       <plugins>
           <plugin>
               <groupId>org.apache.maven.plugins</groupId>
               <artifactId>maven-compiler-plugin</artifactId>
               <version>2.3.2</version>
               <configuration>
                   <source>1.8</source>
                   <target>1.8</target>
                   <encoding>UTF-8</encoding>
               </configuration>
           </plugin>
       </plugins>
   </build>
   ```

3. 用下面步骤创建子工程；

   ![1542113852402](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542113852402.png)

   ![1542114012116](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542114012116.png)

   ![1542114134863](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542114134863.png)

   ![1542114156834](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542114156834.png)

4. 在子工程中添加下面依赖；

   ``` xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   
   <!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
   <dependency>
       <groupId>io.springfox</groupId>
       <artifactId>springfox-swagger2</artifactId>
       <version>2.7.0</version>
   </dependency>
   
   <!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
   <dependency>
       <groupId>io.springfox</groupId>
       <artifactId>springfox-swagger-ui</artifactId>
       <version>2.7.0</version>
   </dependency>
   ```

5. 创建com.sample.bean.UserBean类；

   ![1542116497338](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542116497338.png)

6. 创建com.sample.App类；

   ![1542114645753](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542114645753.png)

   6. 创建SpringBoot启动程序；

      ![1542116628810](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542116628810.png)

   7. 创建src/main/resources/application.yml；

      ![1542115457922](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542115457922.png)

   8. 运行SpringBootApplication，在浏览器访问下面地址，会显示如下内容；

      ``` http
      http://localhost:8088/hello
      ```



      ![1542115563864](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542115563864.png)

   9. 在SpringBoot启动类中添加@EnableSwagger2注解；

      ![1542115753537](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542115753537.png)

   10. 重启SpringBoot程序，在浏览器访问下面地址，可以看到Swagger页面；

       ``` http
       http://localhost:8088/swagger-ui.html
       ```

       ![1542116695465](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542116695465.png)

   ## 扩展功能

   ### 1. 用中文显示方法名

   1. 在方法上添加@ApiOperation注解；

      ![1542117019996](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542117019996.png)

   2. 结果对比：

      ![1542117123705](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542117123705.png)

   ### 2. 用中文显示参数

   #### 情况1：封装在对象里面的参数 

   1. 在属性上添加@ApiModelProperty注解；

   ![1542118822965](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542118822965.png)

   2. 效果对比：

      ![1542119215783](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542119215783.png)

   #### 情况2：方法中的参数

   1. 在参数前面添加@ApiParam注解

   ![1542119088384](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542119088384.png)

   2. 效果对比：

      ![1542119303483](C:\Users\llei\AppData\Roaming\Typora\typora-user-images\1542119303483.png)

## java-example-spring-security

## 一、简介

### 1.spring security简介

### 2. jwt简介

**JWT = Json Web Token** 

#### 2.1 传统的session认证

**定义：**

​	用户登录后，会在服务器端储存一份凭证，并同时让用户将这个凭证保存在cookie中。用户在之后的访问中，连同凭证一起将请求发送给服务器，这样服务器就知道访问用户的身份信息了。

**弊端：**

1. 每认证一个用户，就会在服务器端储存一份凭证，往往这个凭证是储存在内存中，**明显增大了开销**；
2. 在分布式应用中，一台服务器中的凭证无法功效到另一台服务器上，**不利于扩展**；
3. 一旦cookie被截获，就很容易受到跨站请求伪造的攻击。

#### 2.2 基于token的权限验证流程

1. user向server发送账号和密码；
2. server验证账号和密码；
3. server通过验证后返回一个token；
4. user存储token，每次请求时连同token发送给server；
5. server验证token，并返回请求的数据。

**注意：**

> **第一：** 每次请求时都必须将token发送给server
>
> **第二：** token应该保存在请求头中
>
> **第三：** 服务器要支持CORS（跨域资源共享）策咯 【Access-Control-Allow-Origin：*】

#### 2.3 JWT的构成

​	JWT是下面这样的，分三部分构成。 

```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVc2VySWQiOjEyMywiVXNlck5hbWUiOiJhZG1pbiJ9.Qjw1epD5P6p4Yy2yju3-fkq28PddznqRj3ESfALQy_U
```

**2.3.1 header部分** 		承载2份信息：1） 声明这时jwt；2）声明加密算法。像这样：

```json
{
     'typ':'JWT',
     'alg':'HS256'  
}
```

然后将它进行base64加密，**构成第一部分**

```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
```

**2.3.2 payload部分**		承载3份信息：1）标准中注册的声明； 2）公共的声明； 3） 私有的声明。像这样：

``` json
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true
}
```

**1） 标准中注册的声明**

| key  | value                                                        |
| ---- | ------------------------------------------------------------ |
| iss  | jwt的签发者                                                  |
| sub  | jwt所面向的用户                                              |
| aud  | 接收jwt的一方                                                |
| exp  | jwt的过期时间                                                |
| nbf  | 定义在什么时间之前，jwt是不能用的                            |
| iat  | jwt的签发时间                                                |
| jti  | jwt的唯一身份识别，主要用来作为一次性的token，以避免重放攻击 |

**2） 公共的声明**

公共的声明可以添加任何的信息，一般添加用户的相关信息或其它业务需要的必要信息，但不建议添加敏感信息，因为该部分在客户端可解密；

**3） 私有的声明**

私有的声明是提供者和消费者功能定义的声明，一般不建议存放敏感信息，因为base64是对称解密的，意味着该部分信息可以归类为名文信息。

然后将它进行base64加密，**构成第二部分**

```
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9
```

**2.3.3 signature部分** 	承载3份信息： 1） 加密后的header部分； 2） 加密后的payload部分； 3） secred。

这部分信息由“加密后的header部分” + “.” + “加密后的payload”，然后通过header中声明的加密方式与secred进行组合加密，**构成第三部分**

```
var encodedString = base64UrlEncode(header) + '.' + base64UrlEncode(payload);
var signature = HMACSHA256(encodedString, 'secret'); 

// 得到这个：
TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
```

**将这三部分用“.”连接成一个完整的字符串，构成了最终的jwt：**

```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVc2VySWQiOjEyMywiVXNlck5hbWUiOiJhZG1pbiJ9.Qjw1epD5P6p4Yy2yju3-fkq28PddznqRj3ESfALQy_U
```

### 3. 基本环境搭建

##### 3.1 数据库

##### 3.2 运行环境

## 二、基本用法

### 1. 构建基本Spring Boot应用程序

#### 1.1 创建Maven项目，并引入相关依赖

``` xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.example</groupId>
  <artifactId>spring-security</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>spring-security</name>
  <url>http://maven.apache.org</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>  

  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>io.spring.platform</groupId>
        <artifactId>platform-bom</artifactId>
        <version>Brussels-SR15</version>
        <type>pom</type>
        <scope>import</scope>
      </dependency>
    </dependencies>
  </dependencyManagement>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>

    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <version>1.5.18.RELEASE</version>
      </plugin>
    </plugins>
  </build>
</project>

```

#### 1.2 创建简单Spring Boot应用程序，并构建3个路由

``` java
（代码略）
```

![1547546814753](assets/1547546814753.png)

### 2. 创建com.example.cofig.MySecurityConfig.java

如果需要Config生效，需要添加@EnableWebSecurity注解，并继承WebSecurityConfigurerAdapter类

![1547546998934](assets/1547546998934.png)

#### 1.1 构建未登陆用户的验证

##### 1.1.1 重写configure(HttpSecurity http)

​	由于WebSecurityConfigurerAdapter中定义了用户验证的方法，因此我们需要重写这个方法，以满足自身业务需求。

```java
/*
* WebSecurityConfigurerAdapter中的代码片段
*/
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .formLogin().and()
        .httpBasic();
}
```

​	在这个方法中，需要先设置不需要权限的访问路径。

![1547548662345](assets/1547548662345.png)

​	接下来我们添加一个filter，用来设置具体的权限验证规则。

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers("/", "/login").permitAll()  // 表示这两个路径不需要权限
        .anyRequest().authenticated()
        .and().addFilter(new MyLoginFilter(authenticationManager()));
}
```

**注意：**

* MyLoginFilter(AuthenticationManager authenticationManager)是自定义的filter；
* authenticationManager()被定义在WebSecurityConfigurerAdapter中，它会返回AuthenticationManager；

##### 1.1.2 构建MyLoginFilter(AuthenticationManager authenticationManager)

1.1.2.1 继承**spring security**的**UsernamePasswordAuthenticationFilter**；

![1547549611157](assets/1547549611157.png)

1.1.2.2 在MyLoginFilter中，**AuthenticationManager authenticationManager** 是私有属性，并且有一个带参的构造函数；

![1547549729171](assets/1547549729171.png)

1.1.2.3 接下来需要实现两个功能：

* 获取并验证用户输入的密码；（attemptAuthentication）
* 当验证通过后，做什么事情。（successfulAuthentication）

1.1.2.3.1 **获取并验证用户输入的密码**

![1547604784783](assets/1547604784783.png)

这里还需要根据Spring Security的架构，实现两个接口：

* UserDetailsService，用来定义从数据库中查找用户信息，并按Security的要求返回用户信息；

* GrantedAuthority，将用户权限转化为Security要求的格式。

  ``` java
  package com.example.service.impl;
  
  import com.example.dao.mapper.MyUserMapper;
  import com.example.exception.MyAuthException;
  import com.example.po.UserPO;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.security.core.userdetails.User;
  import org.springframework.security.core.userdetails.UserDetails;
  import org.springframework.security.core.userdetails.UserDetailsService;
  import org.springframework.security.core.userdetails.UsernameNotFoundException;
  import org.springframework.stereotype.Service;
  
  import java.util.Collections;
  
  /**
   * 作用：在数据库中查找用户，并按照Security的要求返回用户信息
   */
  @Service
  public class UserDetailsServiceImpl implements UserDetailsService {
  
      @Autowired
      private MyUserMapper myUserMapper;
  
      @Override
      public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
  
          // 在数据库查询用户
          UserPO userPO = myUserMapper.findUserByName(s);
          if (userPO == null) {
              new MyAuthException("User not find.");
          }
          User user = new User(userPO.getName(), userPO.getPassword(), Collections.emptyList());
          return user;
      }
  }
  
  ```

  ```java
  package com.example.service.impl;
  
  import org.springframework.security.core.GrantedAuthority;
  
  /**
   * 作用：将数据库中的Authority转成Security规定的Authority ??
   */
  public class GrantedAuthorityImpl implements GrantedAuthority {
  
      private String authority;
  
      public GrantedAuthorityImpl(String authority) {
          this.authority = authority;
      }
  
      public void setAuthority(String authority) {
          this.authority = authority;
      }
  
      @Override
      public String getAuthority() {
          return authority;
      }
  }
  ```

#### 2.2 构建用户授权

在**MyLoginFilter**中重写successfulAuthentication方法,用来定义用户登陆后要做什么事情。

```java
/**
* 作用：当用户登录成功后，Filter会调用此方法，因此可以在这里生成并返回token
*/
@Override
protected void successfulAuthentication (HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

    long EXPIREATION_TIME = 5 * 60 * 60 * 1000; // 过期时间：5分钟
    String SECRET = "P@ssw0rd";                 // JWT密码
    String TOKEN_PREFIX = "Bearer ";             // Token前缀
    String HEADER_STRING = "Authorization123";     // 存放Token的Header Key

    /**
         * JWT 的生成token的方法
         */
    // 生成JWT
    String retToken = Jwts.builder()
        .claim("authorities", "ROLE_ADMIN,AUTH_WRITE")
        .setSubject(authResult.getName())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIREATION_TIME))
        .signWith(SignatureAlgorithm.HS256, SECRET) // 加密方式
        .compact();

    response.addHeader(HEADER_STRING, TOKEN_PREFIX + retToken);
}
```

#### 2.3 构建已登陆用户的验证

**step 1.** 回到**MySecurityConfig**中，在configure(HttpSecurity http)方法中再增加一个filter
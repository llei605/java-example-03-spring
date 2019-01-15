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

#### 1.2 创建简单Spring Boot应用程序

``` java
（代码略）
```

### 2. 创建com.example.cofig.MySecurityConfig.java



#### 1.1 构建用户yanzh
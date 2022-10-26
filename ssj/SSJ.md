# [SSJ](https://www.bilibili.com/video/BV18W411g7on)
- Spring + SpringMVC + Spring-Data-JPA
---
## Spring
1. [web.xml](web/WEB-INF/web.xml) 配置 <context-param/> 和 ContextLoaderListener
```xml
<beans>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:applicationContext.xml</param-value>
    </context-param>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
</beans>
```
2. [applicationContext.xml](src/main/resources/applicationContext.xml)
---
## SpringMVC
1. [web.xml](web/WEB-INF/web.xml) 配置 DispatcherServlet
```xml
<web-app>
    <servlet>
        <servlet-name>spring-mvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-servlet.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>spring-mvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```
2. [spring-servlet.xml](src/main/resources/spring-servlet.xml)
```xml
<beans>
    <mvc:annotation-driven/>
    <context:component-scan base-package="com.ljh"/>
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
    <mvc:resources mapping="/**" location="classpath:static/" cache-period="900"/>
</beans>
```
---
## Servlet
- [web.xml](web/WEB-INF/web.xml) 配置 Filter
   1. CharacterEncodingFilter
   2. HiddenHttpMethodFilter：使 form 表单支持 PUT/DELETE 请求
   3. OpenEntityManagerInViewFilter：允许 Session 与一次完整的请求过程对应的线程相绑定
---
## Spring-Data-JPA
- @see spring-data-jpa 的 [applicationContext.xml](../spring-data-jpa/src/main/resources/applicationContext.xml)
---
## [JSTL](https://www.runoob.com/jsp/jsp-jstl.html)
1. [pom.xml](pom.xml) 引包
```xml
<dependency>
    <groupId>org.glassfish.web</groupId>
    <artifactId>jakarta.servlet.jsp.jstl</artifactId>
</dependency>
```
2. [spring-servlet.xml](src/main/resources/spring-servlet.xml) 配置试图解析器
```xml
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
</bean>
```
3. [*.jsp](web/index.jsp) 使用 [JSTL 标签](https://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/)
    - c 标签
    ```html
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    <!-- test       必要，条件 -->
    <c:if test="${x > 1}">
        …
    </c:if>

    <c:choose>
        <!-- 可以出现多次 -->
        <c:when test="${x > 1}">…</c:when>
        <!-- 可以出现0次或1次 -->
        <c:otherwise>…</c:otherwise>
    </c:choose>
    
    <!-- items      要被迭代的集合
         var        当前迭代项的名称
         varStatus  迭代状态的名称 -->
    <c:forEach items="${list}" var="i" varStatus="s">
        <div>
            <span>i</span> - <span>s.index</span> - <span>s.count</span>
        </div>
    </c:forEach>
    ```
    - fmt 标签
    ```html
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
    <c:forEach iteams="表示要遍历的集合或数组" var="指定绑定名，容器每次从集合取一个对象，然后绑定到 pageContext 上"
               varStatus="指定绑定名，绑定值是一个由容器创建的对象，该对象封装了当前迭代的状态">
        ...${e.name}...${s.index}...${s.count}...
    </c:forEach>
    ```
---

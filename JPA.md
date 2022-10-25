# JPA
- Java Persistence API
---
## Reference
1. [尚硅谷jpa开发教程全套完整版(初学者零基础入门)_哔哩哔哩](https://www.bilibili.com/video/BV1vW411M7zp)
---
## JPA 与 Hibernate 的关系
- JPA 与 Hibernate 的关系就像 JDBC 和 JDBC 驱动的关系一样
    - JPA 是一个规范
    - Hibernate 是 JPA 的一种实现，是一个框架
---
## JPA 开发基本步骤
1. [创建 JPA 配置文件](jpa/src/main/resources/META-INF/persistence.xml)
    - 必须防放在 META-INF 目录下
2. [创建实体类](jpa/src/main/java/com/ljh/entity/many2one/ua/Customer.java)
3. [使用 JPA API 访问数据库](jpa/src/test/java/com/ljh/JpaTest.java)
---
## JPA 注解
- [基本注解](jpa/src/main/java/com/ljh/entity/many2one/ua/Customer.java)

| annotation       | position | desc                                    |
|:-----------------|:---------|:----------------------------------------|
| @Entity          | class    | 表示类是一个实体，映射到与类名相同的数据库表                  |
| @Table           | class    | 与 @Entity 配合使用，使实体映射到与 name 属性相同名称的数据库表 |
| @SecondaryTable  | class    | 一个实体可以映射多个表，定义单个表的名字、主键等属性              |
| @SecondaryTables | class    | 一个实体可以映射多个表，包裹 @SecondaryTable          |
| @Id              | property | 表示属性映射为数据库表的主键                          |
| @GeneratedValue  | property | 指定主键的生成策略                               |
| @Basic           | property | 表示属性映射为数据库表字段（默认使用）                     |
| @Column          | property | 表示属性映射为与 name 属性相同的数据库表字段               |
| @Transient       | property | 表示属性不是数据库字段                             |
| @Temporal        | property | 指定 Date/Calendar 属性映射到数据表的类型            |
| @Lob             | property | 表示属性映射为数据库表大对象字段                        |
| @Version         | property | 表示属性映射为数据库表乐观锁字段                        |
| @Enumerated      | property | 指定枚举属性映射方式                              |
---
## EntityManager  VS Session
| EntityManager  | 描述             | Session        | 行为是否相同                        |
|:---------------|:---------------|:---------------|:------------------------------|
| /              |                | save()         |                               |
| persist()      | 使实例被管理变为持久化状态  | persist()      | 相同                            |
| /              |                | saveOrUpdate() |                               |
| /              |                | update()       |                               |
| merge()        | 将实体状态合并到持久化上下文 | merge()        | 相同                            |
| find()         | 查找             | get()          | 相同                            |
| remove()       | 删除实例           | delete()       | remove() 不能删除游离对象，delete() 可以 |
| getReference() | 惰性查找           | load()         | 查找对象的 OID 在数据库不存在时，抛出的异常不同    |
|                |                | replicate()    |                               |
| flush()        | 同步持久化上下文到底层数据库 | flush()        | 相同                            |
| refresh()      | 从数据库刷新实例状态     | refresh()      | 相同                            |
| detach()       | 从持久化上下文删除给定实体  | evict()        | 相同                            |
| clear()        | 清理持久化上下文       | clear()        | 相同                            |
| lock()         | ???            | lock()         | 相同                            |
---
## 关联关系
| annotation  | fetch default   |
|:------------|:----------------|
| @ManyToOne  | FetchType.EAGER |
| @OneToMany  | FetchType.LAZY  |
| @OneToOne   | FetchType.EAGER |
| @ManyToMany | FetchType.LAZY  |
1. [单向多对一](jpa/src/main/java/com/ljh/entity/many2one/ua/Order.java)
2. [单向一对多](jpa/src/main/java/com/ljh/entity/one2many/ua/Customer2.java)
3. [双向多对一](jpa/src/main/java/com/ljh/entity/many2one/ba/Customer3.java)
4. [双向一对一](jpa/src/main/java/com/ljh/entity/one2one/Department.java)
5. [双向多对多](jpa/src/main/java/com/ljh/entity/many2many/Item.java)
---
## 二级缓存
1. [persistence.xml](jpa/src/main/resources/META-INF/persistence.xml)
```xml
<persistence xmlns="https://jakarta.ee/xml/ns/persistence" version="3.0">
    <persistence-unit name="jpa">
        <shared-cache-mode>ENABLE_SELECTIVE</shared-cache-mode>
        <properties>
            <property name="hibernate.cache.region.factory_class" value="ehcache"/>
            <property name="hibernate.cache.use_second_level_cache" value="true"/>
            <property name="hibernate.cache.use_query_cache" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
```
2. [ehcache.xml](jpa/src/main/resources/ehcache.xml)
---
## [JPQL](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#hql)
- Java Persistence Query Language
- 关注对象模型、非类型安全
- HQL 的子集：JPQL 总是有效的 JQL
- 支持 SELECT | UPDATE | DELETE 语句
### Query
1. 通过 EntityManager 创建 Query
    1. entityManager.createQuery(qlString)
    2. entityManager.createNamedQuery(name)
        - name 对应 entity 文件里的 `@NamedQuery(name = "", query = "")`
    3. entityManager.createNativeQuery(sqlString, resultClass)
2. 动态绑定参数 query.setParameter()
    1. 按照参数名字绑定：:name
    2. 按照参数位置绑定：?n
3. 调用 javax.persistence.Query API
    1. executeUpdate()：执行 UPDATE | DELETE
    2. getResultList()：执行 SELECT 并返回结果集
    3. getSingleResult()：执行 SELECT 并返回单个结果
    4. setFirstResult(int startPosition)：设置从哪个位置开始检索
    5. setMaxResults(int maxResult)：设置检索的最大结果数
    6. setFlushMode(FlushModeType)：设置 flush 模式
    7. setHint(String hintName, Object value)：设置与查询对象供应商属性或提示
---
## Spring JPA
1. 由 Spring IOC 容器来管理 EntityManagerFactory
```xml
<beans>
    <bean id="dataSource" class="">
        <!-- <property/> -->
    </bean>
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="jpaVendorAdapter">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"/>
        </property>
        <property name="packagesToScan" value="com.ljh.entity"/>
        <property name="jpaProperties">
            <!--  <props/> -->
        </property>
    </bean>
</beans>
```
2. JpaTransactionManager
```xml
<beans>
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>
    <tx:annotation-driven transaction-manager="transactionManager"/>
</beans>
```
3. [Dao 注入 EntityManager](spring-jpa/src/main/java/com/ljh/dao/PersonDao.java)

```java
@Repository
public class DaoImpl implements Dao {
   @PersistenceContext
   private SessionFactory sessionFactory;
}
```
---

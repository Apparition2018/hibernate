# JPA
- Java Persistence API
---
## JPA 与 Hibernate 的关系
- JPA 与 Hibernate 的关系就像 JDBC 和 JDBC 驱动的关系一样
    - JPA 是一个规范
    - Hibernate 是 JPA 的一种实现，是一个框架
---
## JPA 开发基本步骤
1. [创建 JPA 配置文件](src/main/resources/META-INF/persistence.xml)
    - 必须防放在 META-INF 目录下
2. [创建实体类](src/main/java/com/ljh/entity/Customer.java)
3. [使用 JPA API 访问数据库](src/test/java/com/ljh/JpaTest.java)
---
## JPA 基本注解
1. @Entity：指定类是一个实体
2. @Table：指定主表。可以使用 @SecondaryTable 或 @SecondaryTables 指定其它表
3. @Id：指定主键
    - Primitive Type, Wrapper Type, String, Date, BigDecimal, BigInteger
4. @GeneratedValue：指定主键值的生成策略规范
5. @Column：指定持久属性或字段的映射列。默认使用 ?
6. @Basic
7. @Transient
8. @Temporal
--- 

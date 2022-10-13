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
## JPA 注解
- [基本注解](src/main/java/com/ljh/entity/Customer.java)

| 注解               | 使用位置 | 解释                                      |
|:-----------------|:-----|:----------------------------------------|
| @Entity          | 类    | 表示类是一个实体，映射到与类名相同的数据库表                  |
| @Table           | 类    | 与 @Entity 配合使用，使实体映射到与 name 属性相同名称的数据库表 |
| @SecondaryTable  | 类    | 一个实体可以映射多个表，定义单个表的名字、主键等属性              |
| @SecondaryTables | 类    | 一个实体可以映射多个表，包裹 @SecondaryTable          |
| @Id              | 属性   | 表示属性映射为数据库表的主键                          |
| @GeneratedValue  | 属性   | 指定主键的生成策略                               |
| @Basic           | 属性   | 表示属性映射为数据库表字段（默认使用）                     |
| @Column          | 属性   | 表示属性映射为与 name 属性相同的数据库表字段               |
| @Transient       | 属性   | 表示属性不是数据库字段                             |
| @Temporal        | 属性   | 指定 Date/Calendar 属性映射到数据表的类型            |
| @Lob             | 属性   | 表示属性映射为数据库表大对象字段                        |
| @Version         | 属性   | 表示属性映射为数据库表乐观锁字段                        |
| @Enumerated      | 属性   | 指定枚举属性映射方式                              |
--- 
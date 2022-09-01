# Hibernate

---
## Reference
1. [Hibernate. Everything data.](https://hibernate.org/)
2. [Hibernate ORM User Guide](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html)
3. [Hibernate 中文文档](https://hibernate.net.cn)
4. [尚硅谷佟刚Hibernate框架教程_哔哩哔哩](https://www.bilibili.com/video/BV1KW411u7GJ)
---
## ORM
ORM (Object/Relation Mapping): 对象/关系 映射
- ORM 主要解决对象-关系的映射

| 面向对象概念 | 面向关系概念  |
|:------:|:-------:|
|   类    |    表    |
|   对象   | 表的行(记录) |
|   属性   | 表的列(字段) |
- ORM 的思想：将关系数据库中表中的记录映射成为对象，以对象的形式展现，程序员可以把对数据库的操作转化为对对象的操作
- ORM 采用元数据来描述对象-关系映射细节，元数据通常采用 XML 格式，并且存放在专门的对象-关系映射文件中
---
## Hibernate 开发步骤
1. [创建 Hibernate 配置文件](./src/main/resources/hibernate.cfg.xml)
2. [创建持久化类](./src/main/java/com/ljh/entity/News.java)
3. [创建对象/关系映射文件](./src/main/resources/hbm/News.hbm.xml)
4. [通过 Hibernate API 编写访问数据库的代码](./src/test/java/com/ljh/HibernateTest.java)
---
## [Hibernate 配置文件](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#configurations)
- 用于配置数据库连接和 Hibernate 运行时所需的各种属性
- 每个 Hibernate 配置文件对应一个 Configuration 对象
- Hibernate 配置文件有两种格式：
    - hibernate.properties
    - hibernate.cfg.xml
---
## [对象/关系映射文件](https://docs.jboss.org/hibernate/orm/3.6/reference/en-US/html/mapping.html)
- hbm.xml：Hibernate Mapping
- 通过映射文件，Hibernate 可以理解类与数据表之间的对应关系，类属性与数据表列之间的对应关系
- 在运行时 Hibernate 将根据这个映射文件来生成各种 SQL 语句
### 映射文件结构
- hibernate-mapping
    - 类：class
        - 主键：id
        - 基本属性：property
        - 实体引用类：many-to-one | one-to-one
        - 集合：set | list | map | array
            - one-to-many
            - many-to-many
        - 子类：subclass | joined-subclass
        - 其它：component | any | ...
    - 查询语句：query
### 映射对象标识符
- Hibernate 使用对象标识符(OID)来建立内存中的对象和数据库中记录的对应关系，对象的 OID 和数据表的主键对应。Hibernate 通过标识符生成器来为主键赋值
- Hibernate 推荐在数据表中使用代理主键，即不具备业务含义的字段，代理主键通常为整数类型，因为整数类型比字符串类型要节省更多的数据库空间
- 在对象/关系映射文件中，&lt;id&gt;元素用来设置对象标识符，&lt;generator&gt;子元素用来设定标识符生成器
- Hibernate 提供了标识符生成器接口：IdentifierGenerator，并提供了各种内置实现
### 映射关系
1. [映射组成关系](./src/main/resources/hbm/component/Worker.hbm.xml)
2. 映射关联关系
    1. [单向多对一](./src/main/resources/hbm/many2one/ua/Order.hbm.xml)
    2. [双向多对一/双向一对多](./src/main/resources/hbm/many2one/ba/Customer.hbm.xml)
    1. [基于主键的一对一](./src/main/resources/hbm/one2one/primary/Department.hbm.xml)
    2. [基于外键的一对一](./src/main/resources/hbm/one2one/foreign/Department.hbm.xml)
    5. 单向多对多
    6. 双向多对多
3. 映射继承关系
    1. subclass
    2. joined-subclass
    3. union-subclass
---
## Session
- 应用程序与数据库之间交互操作的一个单线程对象，是 Hibernate 的运作中心

### [Session 缓存](./src/test/java/com/ljh/SessionCacheTest.java)
- Session 缓存是 Hibernate 的一级缓存
- Session 缓存中的对象成为持久化对象
#### 操作 Session 缓存
1. flush()：按照 Session 缓存中的对象来同步更新数据库
    - flush() 时间点
        1. 显示调用 flush()
        2. 调用 Transaction 的 commit() 时，会先隐式调用 flush()
        3. 查询时 (HQL 或 Query by Criteria)，如果缓存中的持久化对象发生改变，会先隐式调用 flush()，以保证查询结果为持久化对象的最新状态
    - Session 的 setFlushMode() 可以设置 flush 模式

|     清理缓存的模式      | 各种查询方法 | Transaction 的 commit() | Session 的 flush() |
|:----------------:|:------:|:----------------------:|:-----------------:|
|  FlushMode.AUTO  |   清理   |           清理           |        清理         |
| FlushMode.COMMIT |  不清理   |           清理           |        清理         |
| FlushMode.NEVER  |  不清理   |          不清理           |        清理         |
2. refresh()：会发送 SELECT 语句，并根据当前事务隔离级别，更新 Session 缓存
3. clear()：清理缓存
### 对象的状态
|        状态        | OID  | 在 Session 缓存中 | 数据库有对应记录 |
|:----------------:|:----:|:-------------:|:--------:|
| 临时状态 (Transient) | null |       ×       |    ×     |
| 持久化状态 (Persist)  |  √   |       √       |    √     |
|  删除状态 (Removed)  |  √   |       ×       |    ×     |
| 游离状态 (Detached)  |  √   |       ×       |  maybe   |
<img src="https://static.oschina.net/uploads/space/2017/0816/212113_Lbhn_3375733.png" width="500" alt="对象的状态转换"/>

### [Session 方法](./src/test/java/com/ljh/SessionTest.java)
- 获取
    - get(): 立即加载；获取不到值返回 null
    - load(): 当 lazy="true" 时，延迟加载；获取不到值抛出异常
- 保存/更新
    - save(): 临时 → 持久化
    - persist(): 临时 → 持久化；如果是代理生成主键，在调用 persist() 之前，OID 必须为 null
    - update(): 持久化/游离 → 持久化
    - saveOrUpdate()
- 删除
    - delete(): 游离/持久化 → 删除
- 事务
    - beginTransaction(): 开启一个事务
- 缓存
    - flush(): Session → Database；按照 Session 缓存对象来同步更新数据库
    - refresh()：Database → Session
    - evict(Object obj): 移除 Session 缓存中指定对象；持久化 → 游离
    - clear(): 清除 Session 所有缓存；持久化 → 游离
- 其它
    - close(): 通过释放 JDBC 连接并进行清理来结束 Session；持久化 → 游离
    - isOpen(): 检查 Session 是否仍然打开
    - doWork(Work work): 通过 JDBC 的 Connection 执行 JDBC 操作
---
## Hibernate 检索策略
### 类级别的检索策略
- 类级别可选的检索策略
    - 立即加载检索
    - 延迟加载检索
- 类级别的检索策略可以通过 <class> 元素的 lazy 属性进行设置
- 
---

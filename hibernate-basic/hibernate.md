# Hibernate

---
## Reference
1. [Hibernate. Everything data.](https://hibernate.org/)
2. [Hibernate ORM User Guide](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html)
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
## [配置文件](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#configurations)
- 用于配置数据库连接和 Hibernate 运行时所需的各种属性
- 每个 Hibernate 配置文件对应一个 Configuration 对象
- Hibernate 配置文件有两种格式：
    - hibernate.properties
    - hibernate.cfg.xml
---
## [对象/关系映射文件](https://docs.jboss.org/hibernate/orm/3.6/reference/en-US/html/mapping.html)
- .hbm.xml：Hibernate Mapping
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
        - 子类：subclass | joined-subclass | union-subclass
        - 其它：component | any | ...
    - 查询语句：query | sql-query
### 映射对象标识符
- Hibernate 使用对象标识符(OID)来建立内存中的对象和数据库中记录的对应关系，对象的 OID 和数据表的主键对应。Hibernate 通过标识符生成器来为主键赋值
- Hibernate 推荐在数据表中使用代理主键，即不具备业务含义的字段，代理主键通常为整数类型，因为整数类型比字符串类型要节省更多的数据库空间
- 在 .hbm.xml 中，`<id>`元素用来设置对象标识符，`<generator>`子元素用来设定标识符生成器
- Hibernate 提供了标识符生成器接口：IdentifierGenerator，并提供了各种内置实现
### 映射关系
1. [映射组成关系](./src/main/resources/hbm/component/Worker.hbm.xml)
2. 映射关联关系
    1. [单向多对一](./src/main/resources/hbm/many2one/ua/Order.hbm.xml)
    2. [双向多对一/双向一对多](./src/main/resources/hbm/many2one/ba/Customer.hbm.xml)
    3. [基于外键的一对一](./src/main/resources/hbm/one2one/foreign/Department.hbm.xml)
    4. [基于主键的一对一](./src/main/resources/hbm/one2one/primary/Department.hbm.xml)
    5. [单向多对多](./src/main/resources/hbm/many2many/ua/Teacher.hbm.xml)
    6. [双向多对多](./src/main/resources/hbm/many2many/ba/Teacher.hbm.xml)
3. 映射继承关系
    1. [subclass](./src/main/resources/hbm/inheritance/subclass/Person.hbm.xml)
    2. [joined-subclass](./src/main/resources/hbm/inheritance/joined.subclass/Person.hbm.xml)
    3. [union-subclass](./src/main/resources/hbm/inheritance/union.subclass/Person.hbm.xml)
---
## Session
- 应用程序与数据库之间交互操作的一个单线程对象，是 Hibernate 的运作中心

### [Session 缓存](./src/test/java/com/ljh/SessionCacheTest.java)
- Session 缓存是 Hibernate 的一级缓存，属于事务范围的缓存，默认开启
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

### [Session 方法](./src/test/java/com/ljh/SessionMethodTest.java)
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
### [管理 Session](./src/test/java/com/ljh/SessionContextTest.java)
- 三种管理 Session 的方法：`<property name="hibernate.current_session_context_class"/></property>`
    1. Session 对象的生命周期与本地线程绑定：thread
    2. Session 对象的生命周期与 JTA 事务绑定：jta
    3. Hibernate 委托程序管理 Session 对象的生命周期：managed
---
## [检索策略](./src/test/java/com/ljh/QueryStrategyTest.java)
1. 类级别的检索策略，`<class/>`
    - lazy：是否延迟检索
        - false：立即检索
        - true：延迟检索
            - 调用 load() 时返回目标对象的代理对象，仅存储 OID
            - 第一次访问非 OID 属性时，才发送 SELECT 语句
2. 一对多和多对多的检索策略，`<set/>`
    - lazy：是否延迟检索
        - true：延迟检索
            - 调用集合的 iterator(), size(), isEmpty(), contains() 等方法会初始化集合
            - 调用 Hibernate.initialize() 显式初始化
        - extra：增强延迟检索
            - 调用集合的 iterator() 会初始化集合
            - 调用集合的 size(), contains(), iesEmpty()，不会初始化集合，仅通过特定 SELECT 语句查询必要的信息，
                如：size() 会发送 SELECT count(OID) 语句
    - fetch：
        - select：
            - 没有设置 batch-size：等值查询，`select ... from O where C_ID=?`
            - 设置了 batch-size：in 查询，`select ... from O where C_ID in (?,?...)`
        - subselect：忽略 batch-size 属性
            - 子查询，`select ... from O where C_ID in (select C_ID from C)`
        - join：忽略 lazy 属性；HQL 忽略 fetch="join"
            - 迫切左外连接查询，`select ... from C c left outer join O o on c.C_ID=o.C_ID where c.C_ID=?`
   - batch-size：批量检索的数量，采用 in 查询
3. 多对一和一对一的检索策略
    - `<many-to-one lazy/>`：是否延迟检索
    - `<many-to-one fetch/>`：参考 `<set fetch/>`
    - `<class batch-size/`>`：批量检索的数量，采用 in 查询
---
## [检索方式](./src/test/java/com/ljh/QueryWayTest.java)
1. 导航对象图：根据已经加载的对象导航到其他对象
2. OID：对象的 OID
3. [HQL](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#hql)：Hibernate Query Language，以对象模型为中心的非类型安全查询
    1. 通过 Session 创建 Query 对象
        1. createQuery(queryString)
        2. getNamedQuery(queryName)：queryName 对应 .hbm.xml 文件的 `<query name/>`
    2. 动态绑定参数：setParameter()
        - 依赖于 JDBC API 中的 PreparedStatement 的预定义 SQL 语句功能
        - 方式：
            1. 按照参数名字绑定：:name
            2. 按照参数位置绑定：?n
    3. 调用 Query 相关方法：如 list() 等
    - 检索策略
        - 如果 HQL 中没有显式指定检索策略，将使用 .hbm.xml 中配置的检索策略
        - HQL 忽略 .hbm.xml 中配置的迫切左外连接(fetch="join")
4. QBC：Query By Criteria，类型安全的查询
    1. [New Criteria Queries](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#criteria)
    2. [Legacy Criteria Queries](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#appendix-legacy-criteria)
5. 本地 SQL 查询
    1. [New Native Queries](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#sql)
    2. [Legacy Native Queries](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#appendix-legacy-native-queries)
---
## [缓存](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#caching)
### 二级缓存
- 二级缓存是 SessionFactory 级别的，属于进程范围的缓存
- SessionFactory 缓存分为两类：
    1. 内置缓存：Hibernate 自带，只读，不可卸载；通常在 Hibernate 的初始化阶段，缓存 .hbm.xml 文件内容
    2. 外置缓存：即 Hibernate 二级缓存，缓存数据库数据，物理介质可以是内存或硬盘
- 不适合放入二级缓存的数据
    1. 经常被修改的数据
    2. 不允许出现并发问题的数据
    3. 与其它应用程序共享的数据
- 四种缓存并发策略

| Strategy             |              | Isolation Level  |
|:---------------------|:-------------|:-----------------|
| NONSTRICT_READ_WRITE | read-through | Read Uncommitted |
| READ_WRITE           | write-though | Read Committed   |
| TRANSACTIONAL        | write-though | Repeatable Read  |
| READ_ONLY            |              | Serializable     |
- 配置步骤
    1. hibernate.cfg.xml
    ```xml
    <hibernate-configuration>
        <session-factory>
            <property name="hibernate.cache.region.factory_class">ehcache</property>
            <!-- 是否开启二级缓存 -->
            <property name="hibernate.cache.use_second_level_cache">true</property>
            
            <!-- class-cache: 类级别二级缓存
                如果不在这里配置，可以在 .hbm.xml 的 <class/> 配置 <cache/> -->
            <class-cache class="com.ljh.entity.query.strategy.Customer3" usage="read-write"/>
            <class-cache class="com.ljh.entity.query.strategy.Order3" usage="read-write"/>
            <!-- collection-cache: 集合级别二级缓存
                如果不在这里配置，可以在 .hbm.xml 的 <class/><set/> 配置 <cache/>-->
            <collection-cache collection="com.ljh.entity.query.strategy.Customer3.orders" usage="read-write"/>
        </session-factory>
    </hibernate-configuration>
    ```
    2. [ehcache.xml](./src/main/resources/ehcache.xml)（没有配置，则默认加载 ehcache-failsafe.xml）
### 查询缓存
- 使用步骤
    1. 配置二级缓存
    2. hibernate.cfg.xml
    ```xml
    <hibernate-configuration>
        <session-factory>
            <!-- 是否启用查询缓存。每个查询仍需要 query.setCacheable(true); -->
            <property name="hibernate.cache.use_query_cache">true</property>
        </session-factory>
    </hibernate-configuration>
    ```
    3. 使用方式
        1. Jakarta Persistence：`query.setHint("org.hibernate.cacheable", true);`
        2. Hibernate native API：`query.setCacheable(true);`
---
## [批量操作](./src/test/java/com/ljh/BatchOperationTest.java)
1. Session
    - 应即使从缓存中清空已经处理完毕并且不会再访问的对象。即在处理完一个对象或小批量对象后，立即 flush()，然后 clear()
    ```
    News news = null;
    for (int i = 0; i < 1000000; i++) {
        news = new News().setTitle("--" + i);
        session.save(news);
        if ((i + 1) % 20 == 0) {
            session.flush();
            session.clear();
        }
    }
    ```
    - 在 .cfg.xml 中设置 JDBC 单次批量处理的数目，应保证每次向数据库发送的批量 SQL 语句数目与 batch_size 属性一致 ???
    - 若对象的主键 generator 为 identity，则 Hibernate 无法在 JDBC 层进行批量插入操作 ???
    - 建议关闭二级缓存
    - 可滚动的结果 org.hibernate.ScrollableResults：不包含任何对象，只包含用于在线定位记录的游标。只有当程序遍历访问 ScrollableResults 对象的特定元素时，才会到数据库中加载相应的对象
    ```
    ScrollableResults sr = session.createQuery("FROM News").scroll();
    int count = 0;
    while (sr.next()) {
        News news = (News) sr.get(0);
        news.setTitle(news.getTitle()) + "*");
        if ((count++ + 1) % 100 == 0) {
            session.flush();
            session.clear();
        }
    }
    ```
2. HQL：支持 `INSERT INTO ... SELECT ...`，不支持 `INSERT INTO ... VALUES ...`
3. StatelessSession：与 Session 对比有以下区别
    - 没有缓存，通过 StatelessSession 来加载、保存或更新后的对象处于游离状态
    - 不与 Hibernate 二级缓存交互
    - 调用 save()、update()、delete() 后，立即发送相应 SQL 语句，而不是计划发送 SQL 语句
    - 不会对关联的对象进行任何级联操作
    - 通过同一 StatelessSession 加载两个 OID 相同的对象，两对象内存地址不同
    - StatelessSession 所做操作可被 Interceptor 捕获到，但是会被 Hibernate 的事件处理系统忽略掉
4. JDBC API（推荐）
```
session.doWork(new Work() {
    @Override
    public void execute(Connection conn) throws SQLException {
    }
}
```
---

# Hibernate

---
## Reference
1. [Hibernate. Everything data.](https://hibernate.org/)
2. [Hibernate ORM 5.6.7.Final User Guide](https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html)
3. [Hibernate 中文文档](https://hibernate.net.cn)
4. [尚硅谷佟刚Hibernate框架教程_哔哩哔哩](https://www.bilibili.com/video/BV1KW411u7GJ)
---
## 对象的持久化
- 狭义："持久化"仅仅指把对象永久保存到数据库中
- 广义："持久化"包括和数据库相关的各种操作
    1. 保存
    2. 更新
    3. 删除
    4. 查询
    5. 加载：根据特定的OID(Object Identifier)，把一个对象从数据库加载到内存中
### 持久化对象的状态
- 站在持久化的角度，Hibernate 把对象分为4种状态：

|  状态   |     英     | OID | 位于 Session 中 | 数据库对应记录 |
|:-----:|:---------:|:---:|:------------:|:-------:|
| 临时对象  | Transient |  ×  |      ×       |    ×    |
| 持久化对象 |  Persist  |  √  |      √       |    √    |
| 游离对象  | Detached  |  √  |      ×       |  maybe  |
| 删除对象  |  Removed  |  √  |      ×       |    ×    |
<img src="https://static.oschina.net/uploads/space/2017/0816/212113_Lbhn_3375733.png" width="600" alt="对象的状态转换"/>

>#### 临时对象
>1. Java 对象的 OID 为 null
>2. 映射文件为 &lt;id&gt; 设置了 unsaved-value 属性，并且 Java 对象的 OID 取值与这个属性值相等
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
1. 创建 Hibernate 配置文件
2. 创建对象/关系映射文件
3. 创建持久化类
4. 通过 Hibernate API 编写访问数据库的代码
---
## [Hibernate 配置文件](https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#configurations)
- 用于配置数据库连接和 Hibernate 运行时所需的各种属性
- 每个 Hibernate 配置文件对应一个 Configuration 对象
- Hibernate 配置文件有两种格式：
    - hibernate.properties
    - hibernate.cfg.xml
---
## [对象/关系映射文件](https://docs.jboss.org/hibernate/orm/3.5/reference/en/html/mapping.html#mapping-declaration-mapping)
- POJO 类和关系数据库之间的映射可以用一个 XML 文档来定义
- 通过 POJO 类的数据库映射文件，Hibernate 可以理解持久化类和数据表之间的对应关系，也可以理解持久化类属性与数据库表列之间的对应关系
- 在运行时 Hibernate 将根据这个映射文件来生成各种 SQL 语句
- 映射文件的扩展名为 .hbm.xml
### 映射文件结构
- hibernate-mapping
    - 类：class
        - 主键：id
            - 基本属性：property
            - 实体引用类：many-to-one | one-to-many
            - 集合：set | list | map | array
                - one-to-many
                    - many-to-one
            - 子类：subclass | joined-subclass
            - 其它：component | any | ...
    - 查询语句：query
### 映射对象标识符
- Hibernate 使用对象标识符(OID)来建立内存中的对象和数据库中记录的对应关系，对象的 OID 和数据表的主键对应。Hibernate 通过标识符生成器来为主键赋值
- Hibernate 推荐在数据表中使用代理主键，即不具备业务含义的字段，代理主键通常为整数类型，因为整数类型比字符串类型要节省更多的数据库空间
- 在对象/关系映射文件中，&lt;id&gt;元素用来设置对象标识符，&lt;generator&gt;子元素用来设定标识符生成器
- Hibernate 提供了标识符生成器接口：IdentifierGenerator，并提供了各种内置实现
### 映射关联关系
1. 基于主键的一对一
2. 基于外键的一对一
3. 单向一对多
4. 双向一对多
5. 单向多对多
6. 双向多对多
### 映射继承关系
1. subclass
2. joined-subclass
3. union-subclass
---
## Configuration
- Configuration 负责管理 Hibernate 的配置信息：
```
1. 数据库的 URL、用户名、密码、JDBC 驱动类、数据库 Dialect、数据库连接池等
    hibernate.cfg.xml 文件

2. 持久化类雨数据表的映射关系 
    *.hbm.xml 文件
```
- 创建 Configuration 的两种方式：
```
1. 属性文件：hibernate.properties
    Configuration cfg = new Configuration();

2. XML 文件：hibernate.cfg.xml
    Configuration cfg = new Configuration().configure();
```
---
## SessionFactory
- 针对单个数据库映射关系经过编译后的内存镜像，是线程安全的。
- 一旦构建完毕，即被赋予特定的配置信息
- 非常消耗资源，一般情况下一个应用中只初始化一个
    - `SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();`
---
## Session
- 应用程序与数据库之间交互操作的一个单线程对象，所有持久化对象必须在 session 的管理下才可以进行持久化操作
- 生命周期很短
- 有一个一级缓存，显式执行 flush 之前，所有的持久层操作的数据都缓存在 session 对象处
- "相当于" JDBC 中的 Connection
### Session 核心方法
- 获取
    - get(): 立即检索
    - load(): 延迟检索
- 保存/更新
    - save(): 临时 → 持久化，设置 id 无效
    - persist(): 临时 → 持久化，设置 id 会抛出异常
    - update(): 游离/持久化 → 持久化
    - saveOrUpdate()
- 删除
    - delete(): 游离/持久化 → 删除
- 事务
    - beginTransaction(): 开启一个事务
- 其它
    - isOpen(): 判断 Session 对象是否被打开
    - evict(): 持久化 → 游离
    - doWork(): 获取 Connection，执行 jdbc 操作
    - flush(): 将 session 缓存中的数据与数据库同步
    - clear(): 清除 session 中的缓存数据
    - close()
    - ...
### Session 缓存
- Session 接口的实现中包含了一系列的 Java 集合，这些 Java 集合构成了 Session 缓存，只要 Session 实例没有结束生命周期，且没有清理缓存，则存放在它缓存中的对象也不会结束生命周期
- Session 缓存可以减少 Hibernate 应用程序访问数据库的频率
>#### flush()
>- flush(): Session 按照缓存中对象的属性变化来同步更新数据库  
>- 默认情况下 Session 在以下时间点刷新缓存：
>```
>1. 显示调用 Session 的 flush()
>2. 当应用程序调用 Transaction 的 commit()，该方法先刷新缓存，然后再向数据库提交事务
>3. 当应用程序执行了一些查询 (HQL, Criteria) 操作时，如果缓存中持久化对象的属性已经发生了改变，会先 flush 缓存，以保证查询结果能够持久化对象的最新状态
>```
>- flush 缓存的例外情况：如果对象使用 native 生成器生成 OID，且采用的主键生成方式需数据库生成 OID，那么当调用 Session 的 save() 保存对象时，会立即执行向数据库插入该实体的 INSERT 语句
>- 若希望改变 flush() 的默认时间点，可以通过 Session 的 setFlushMode() 显式设定 flush() 的时间点
>
>|清理缓存的模式|各种查询方法|Transaction 的 commit()|Session 的 flush()|
>|:----------:|:---------:|:--------------------:|:----------------:|
>|FlushMode.AUTO|   清理   |         清理         |       清理       |
>|FlushMode.COMMIT| 不清理 |         清理         |       清理       |
>|FlushMode.NEVER| 不清理  |        不清理        |       清理       |
>#### refresh()
>- 强制发送 SELECT 语句，使 Session 缓存中对象的状态和数据表中的对应的记录保持一致。
>- 需要配置事务隔离级别为 Read Committed，在 hibernate.cfg.xml 中配置
>   - `<property name="hibernate.connection.isolation">2</property>`
### Hibernate 与触发器协同工作
1. 触发器使 session 缓存中的持久化对象与数据库中对应的数据不一致
   - 解决：在执行完 session 的相关操作后，立即调用 flush() 和 refresh()，迫使 session 缓存与数据库同步
2. session 的 update() 无论游离对象的属性是否发生变化都会执行 UPDATE 语句，从而盲目触发触发器
   - 解决：在映射文件的 <class> 设置 select-before-update="true"，当更新一个游离对象时，会先执行 SELECT 语句，获得当前游离对象在数据库中的最新数据，只有在不一致的情况下才会执行 UPDATE 语句
---
## Transaction
```
Transaction tx = session.beginTransaction();
```
- 常用方法：commit() rollback() wasCommitted()
### 数据库的隔离级别
- 对于同时运行的多个事务，当这些事务访问数据库中相同的数据时，如果没有采用必要的隔离机制，就会导致各种并发问题：
```
1. 脏读：对于两个事物 T1 T2，T1 读取了已经被 T2 更新但还没有被提交的字段之后，若 T2 回滚，T1 读取的内容就是临时且无效的
2. 不可重复读：对于两个事物 T1 T2，T1 读取了一个字段，然后 T2 更新了该字段之后，T1 再次读取同一个字段，值就不同了
3. 幻读：对于两个事物 T1 T2，T1 从一个表中读取了一个字段，然后 T2 在该表中插入了一些新的行之后，如果 T1 再次读取同一个表，就会多出几行
```
- 一个事务与其它事务隔离的程度成为隔离级别，数据库规定了多种事务隔离级别，不同隔离级别对应不同的干扰程度，隔离级别越高，数据一致性就越好，但并发性越弱
- 数据库提供了4种隔离级别：

|       隔离级别       | 脏读  | 不可重复读 | 幻读  |
|:----------------:|:---:|:-----:|:---:|
| Read Uncommitted |  √  |   √   |  √  |
|  Read Committed  |  ×  |   √   |  √  |
| Repeatable Read  |  ×  |   ×   |  √  |
|   Serializable   |  ×  |   ×   |  ×  |
- Oracle 支持2种事务隔离级别：Read Committed 和 Serializable，默认 Read Committed
- MySQL 支持4种事务隔离级别，默认 Repeatable Read
---
## Hibernate 检索策略
### 类级别的检索策略
- 类级别可选的检索策略
    - 立即加载检索
    - 延迟加载检索
- 类级别的检索策略可以通过 <class> 元素的 lazy 属性进行设置
- 
---
# Spring Data JPA
- Spring Data JPA 致力于减少数据访问层(DAO)的开发量，开发者唯一要做的，就只是声明持久层的接口，其它都交给 Spring Data JPA 来完成。
---
## Reference
1. [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
2. [Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
---
## [Spring Data Repository](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories)
1. Repository 是一个标记接口，不含有任何属性和方法
2. 定义持久层接口继承 Repository，使其纳入到 IOC 容器中
3. 可以使用 `@RepositoryDefinition` 注解来替代继承 Repository 接口
### Repository 子接口
```
CrudRepository                  继承 Repository                       CRUD
PagingAndSortingRepository      继承 CrudRepository                   分页和排序
JpaRepository                   继承 PagingAndSortingRepository       JPA
MongoRepository                 继承 PagingAndSortingRepository       Mongo
```
### Spring Data JPA 开发步骤
1. 自定义存储接口 [XxxRepository](./src/main/java/com/ljh/repositories/PersonRepository.java)
    - 继承 JpaRepository 和 JpsSpecificationExecutor
2. [定义查询方法](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.details)
    1. 方法名称派生查询：find…By, exists…By
        - 支持属性表达式
            - 如果当前类有对应名称的属性，则优先使用，而不使用属性表达式
            - 如果当前类有对应名称的属性，但想使用属性表达式，可以使用 `-` 手动定义遍历点：：@see SpringDataTest#testPropertyExpressions()
        - 特殊参数处理：Pageable, Slice, Sort
        - 限制查询结果：First / Top
            - `findTop3ByLastname`, `findFirst10ByLastname`
        - 支持返回值为 Collections 和 Iterables：Iterable, List, Set, Streamable, Iterable 的自定义扩展, Vavr
        - 流式查询结果：`Stream<User> findByLastname();`
        - 异步查询结果
        ```
        @Asnyc
        Future<User> findByFirstname(String firstname); 
        CompletableFuture<User> findOneByFirstname(String firstname);
        ListenableFuture<User> findOneByLastname(String lastname);
        ```
    2. 手动定义查询
3. [创建存储接口代理实例](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.create-instances)
    - JavaConfig：`@EnableJpaRepositories`
    - XML：`<jpa:repositories base-package entity-manager-factory-ref/>`
### 为 Repository 添加自定义方法
1. 单个 Repository
    1. [定义一个接口](./src/main/java/com/ljh/repositories/custom/dao/PersonDao.java)：声明要添加的自定义方法
    2. [提供该接口的实现类](./src/main/java/com/ljh/repositories/PersonRepository.java)：类名结尾为 impl，并实现接口方法
        - 默认情况下，SpringData 会在 base-package 中查找 "xxxImpl" 作为实现类，
          也可以通过 repository-impl-postfix 声明后缀
    3. [声明 Repository 接口](./src/main/java/com/ljh/repositories/PersonRepository.java)，并实现 1. 接口
2. [所有 Repository](https://blog.csdn.net/saafdgvsdg/article/details/80603581)
---
## [Introduction](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.introduction)
- Spring Namespace
- Annotation-based Configuration
- Bootstrap Mode
---
## [Persisting Entities](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.entity-persistence)

---
## [查询方法](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods)
1. 方法名称派生查询
    - [subject keywords](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#appendix.query.method.subject)
    - [predicate keywords and modifiers](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#appendix.query.method.predicate)
2. [JPA Named Queries](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.named-queries)
3. [@Query](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query)：使用 @Query 注解自定义 JPQL 语句实现更灵活的查询
    - LIKE 表达式：支持在 JPQL 上直接添加 `%`： @see PersonRepository#queryByLastNameLikeAndEmailLike
    - 原生 SQL：`Query(value = "…", nativeQuery = true)`
4. [@Modifying](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.modifying-queries)：同时使用 @Modifying 和 @Query 注解，可以自定义更新操作 (UPDATE/DELETE)
    - 方法返回值应该为 int，表示语句影响的行数
    - 在调用的地方添加非只读事务 @Transactional
5. [SpEL](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.named-parameters)
---
## [Specifications](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#specifications)
- 继承 JpsSpecificationExecutor
---
## [Query by Example](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example)
```
Person person = new Person();
person.setFirstname("Dave");
Example<Person> example = Example.of(person);
```
---
## [Locking](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#locking)
```
@Lock(LockModeType.READ)
    List<User> findByLastname(String lastname);
}
```
---
## Auditing
- [Auditing](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing)
- [JPA Auditing](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.auditing)
- [Auditing with JPA, Hibernate, and Spring Data JPA | Baeldung](https://www.baeldung.com/database-auditing-jpa)
---

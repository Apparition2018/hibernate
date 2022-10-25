package com.ljh;

import com.ljh.entity.Person;
import com.ljh.repositories.PersonRepository;
import com.ljh.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * SpringDataTest
 * SpringData JPA:https://blog.csdn.net/qq122516902/article/details/82940705
 *
 * @author ljh
 * created on 2019/11/13 14:04
 */
@Slf4j
public class SpringDataTest {

    private final ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    private final PersonRepository personRepository = ctx.getBean(PersonRepository.class);
    private final PersonService personService = ctx.getBean(PersonService.class);

    /**
     * 测试数据源配置是否成功
     */
    @Test
    public void testDataSource() {
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.err.println("dataSource = " + dataSource);
    }

    /**
     * 测试实体管理工厂 (EntityManagerFactory) 是否配置成功
     * 查看数据库是否生成表
     */
    @Test
    public void testEntityManagerFactory() {
    }

    @Test
    public void testHelloWord() {
        System.err.println("Proxy: " + personRepository.getClass().getName());

        Person person = personRepository.findByLastName("AA");
        System.err.println("person = " + person);
    }

    /**
     * 测试关键字
     */
    @Test
    public void testModifyingAndKeyWord() {
        List<Person> personList = personRepository.findByLastNameStartingWithAndIdLessThan("X", 10);
        System.err.println("personList = " + personList);

        personList = personRepository.findByLastNameEndingWithAndIdLessThan("X", 10);
        System.err.println("personList = " + personList);

        personList = personRepository.findByEmailInAndBirthLessThan(Arrays.asList("AA@ljh.com", "FF@ljh.com", "SS@ljh.com"), new Date());
        System.err.println("personList = " + personList);
    }

    /**
     * 测试属性表达式
     */
    @Test
    public void testPropertyExpressions() {
        List<Person> personList = personRepository.findByAddress_IdGreaterThan(1);
        System.err.println("personList = " + personList);
    }

    /**
     * 测试 @Query
     */
    @Test
    public void testQueryAnnotation() {
        // 无参
        Person person = personRepository.queryByMaxId();
        System.err.println("person = " + person);

        // 传参：占位符
        List<Person> personList = personRepository.queryByLastNameAndEmail("AA", "aa@ljh.com");
        System.err.println("personList = " + personList);

        // 传参：命名参数
        personList = personRepository.queryByLastNameAndEmail2("AA", "aa@ljh.com");
        System.err.println("personList = " + personList);

        // 传参：like，SpringData 允许在 JPQL 语句上添加 %
        personList = personRepository.queryByLastNameLikeAndEmailLike("x", "%bb%");
        System.err.println("personList = " + personList);

        // 原生 SQL
        long count = personRepository.queryCntId();
        System.err.println("count = " + count);
    }

    /**
     * 测试 @Modifying
     */
    @Test
    public void testModifying() {
        int affectedRows = personService.updateEmailById("mm@ljh.com", 1);
        System.err.println("affectedRows = " + affectedRows);
    }

    /**
     * 测试 CurdRepository
     */
    @Test
    public void testCurdRepository() {
        // 删除所有
        personRepository.deleteAll();

        // 批量保存
        List<Person> personList = new ArrayList<>();
        for (int i = 'a'; i <= 'z'; i++) {
            Person person = new Person();
            person.setAddressId(i + 1);
            person.setBirth(new Date());
            person.setEmail((char) i + "" + (char) i + "@ljh.com");
            person.setLastName((char) i + "" + (char) i);

            personList.add(person);
        }
        personService.saveAll(personList);
    }

    /**
     * 测试 PagingAndSortingRepository
     */
    @Test
    public void testPagingAndSortingRepository() {
        // pageNo 从 0 开始
        int pageNo = 3 - 1, pageSize = 5;
        // 排序
        Sort.Order order1 = new Sort.Order(Sort.Direction.DESC, "Id");
        Sort.Order order2 = new Sort.Order(Sort.Direction.ASC, "email");
        Sort sort = Sort.by(order1, order2);
        // Pageable 接口通常使用其实现类 PageRequest，其中封装了需要分页的信息
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Person> page = personRepository.findAll(pageable);
        System.err.println("总记录数：" + page.getTotalElements());
        System.err.println("总页数：" + page.getTotalPages());
        System.err.println("当前第几页：" + (page.getNumber() + 1));
        System.err.println("当前页面的 List：" + page.getContent());
        System.err.println("当前页面的 记录数：" + page.getNumberOfElements());
    }

    /**
     * 测试 JpaRepository
     */
    @Test
    public void testJpaRepository() {
        Person person = new Person();
        person.setBirth(new Date());
        person.setEmail("XYZ@ljh.com");
        person.setLastName("xyz");

        // person 主键为空，所以为 INSERT 语句
        Person person2 = personRepository.saveAndFlush(person);
        System.err.println(person == person2);  // true

        // person 主键不为空，所以为 UPDATE 语句
        person2.setEmail("xyz@ljh.com");
        person2.setId(27);
        Person person3 = personRepository.saveAndFlush(person2);
        System.err.println(person2 == person3); // false
    }

    /**
     * 测试 JpsSpecificationExecutor
     * <p>
     * 实现带查询条件的分页
     * 调用 JpsSpecificationExecutor 的 Page<T> findAll(Specification<T> spec, Pageable pageable);
     * Specification: 封装了 JPA Criteria 查询的查询条件
     * Pageable: 封装了请求分页的信息：pageNo, pgeSize, Sort
     */
    @Test
    public void testJpsSpecificationExecutor() {
        int pageNo = 3 - 1, pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        // 通常使用 Specification 的匿名内部类
        Specification<Person> specification = new Specification<Person>() {

            /**
             * @param root  代表查询的实体类
             * @param query 1.可以从中得到 Root 对象，即告知 JPA Criteria 查询要查询哪一个实体类
             *              2.可以用来添加查询条件
             *              3.结合 EntityManager 对象得到最终查询的 TypedQuery 对象
             * @param criteriaBuilder   用于创建 Criteria 相关对象的工厂
             * @return Predicate 代表一个查询条件
             */
            @Override
            public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.greaterThan(root.get("id"), 5);
            }
        };

        Page<Person> page = personRepository.findAll(specification, pageable);
        System.err.println("总记录数：" + page.getTotalElements());
        System.err.println("总页数：" + page.getTotalPages());
        System.err.println("当前第几页：" + (page.getNumber() + 1));
        System.err.println("当前页面的 List：" + page.getContent());
        System.err.println("当前页面的 记录数：" + page.getNumberOfElements());
    }

    /**
     * 测试 为 Repository 添加自定义方法
     */
    @Test
    public void testCustomRepositoryMethod() {
        personRepository.custom();
    }
}

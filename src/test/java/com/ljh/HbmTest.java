package com.ljh;

import com.ljh.entity.News;
import com.ljh.entity.inheritance.joined.subclass.Person2;
import com.ljh.entity.inheritance.joined.subclass.Student2;
import com.ljh.entity.inheritance.sublcass.Person;
import com.ljh.entity.inheritance.sublcass.Student;
import com.ljh.entity.inheritance.union.subclass.Person3;
import com.ljh.entity.inheritance.union.subclass.Student3;
import org.hibernate.Hibernate;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

/**
 * hbm.xml 文件配置
 *
 * @author ljh
 * created on 2020/1/13 17:33
 */
public class HbmTest extends BaseTest {

    /**
     * 测试 <class dynamic-update="true"/>
     */
    @Test
    public void testClassDynamicUpdate() {
        News news = session.get(News.class, 1);
        news.setAuthor("LJH");
        // dynamic-update="false", update NEWS set TITLE=?, AUTHOR=?, Date=? where ID=?
        // dynamic-update="true", update NEWS set AUTHOR=? where ID=?
    }

    /**
     * 测试 <property update="false/>
     */
    @Test
    public void testPropertyUpdate() {
        News news = session.get(News.class, 1);
        // 没有发送 UPDATE 语句
        news.setTitle("JAVA");
    }

    /**
     * 测试 <property formula="..."/>
     */
    @Test
    public void testPropertyFormula() {
        News news = session.get(News.class, 1);
        System.out.println(news.getDesc());
        // Hibernate:
        //     select
        //         news0_.ID as id1_0_0_,
        //         news0_.TITLE as title2_0_0_,
        //         news0_.AUTHOR as author3_0_0_,
        //         news0_.DATE as date4_0_0_,
        //         (SELECT
        //             concat(news0_.author,
        //             ': ',
        //             news0_.title)
        //         FROM
        //             NEWS n
        //         WHERE
        //             n.id = news0_.id) as formula1_0_
        //     from
        //         NEWS news0_
        //     where
        //         news0_.ID=?
        // LJH2: Java
    }

    /**
     * 测试 <column sql="mediumblob"/>
     */
    @Test
    public void testColumnBlob() throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get("hibernate-logo.svg"));
        System.out.println(inputStream.available());
        Blob image = Hibernate.getLobCreator(session).createBlob(inputStream, inputStream.available());

        News news = new News();
        news.setTitle("Go");
        news.setAuthor("Rob Pike");
        news.setDate(new Date());
        news.setContent("Go Go Go");
        news.setImage(image);
        session.save(news);
    }

    /**
     * 测试 <subclass/> 继承
     * 缺点：
     * 1. 使用了辨别者列
     * 2. 子类独有的字段不能添加非空约束
     * 3. 若继承层次较深，则数据表的字段也会较多
     */
    @Test
    public void testSubClassSave() {
        // 插入操作:
        // 1. 对于子类对象只需把记录插入到一张数据表中
        // 2. 辨别者列由 Hibernate 自动维护
        Person person = new Person();
        person.setAge(11);
        person.setName("AA");

        session.save(person);

        Student student = new Student();
        student.setAge(22);
        student.setName("BB");
        student.setSchool("ATGUIGU");

        session.save(student);
    }

    @Test
    public void testSubClassQuery() {
        // 查询操作:
        // 1. 查询父类记录，只需要查询一张数据表
        List<Person> persons = session.createQuery("FROM Person").list();
        System.out.println(persons.size());
        // 2. 对于子类记录，只需要查询一张数据表
        List<Student> students = session.createQuery("FROM Student").list();
        System.out.println(students.size());
    }

    /**
     * 测试 <joined-subclass/> 继承
     * 优点：
     * 1. 无需使用辨别者列
     * 2. 子类独有的字段能添加非空约束
     * 3. 没有冗余的字段
     */
    @Test
    public void testJoinedSubClassSave() {
        // 插入操作:
        // 1.对于子类至少需要插入两张数据表
        Person2 person = new Person2();
        person.setAge(11);
        person.setName("AA");

        session.save(person);

        Student2 student = new Student2();
        student.setAge(22);
        student.setName("BB");
        student.setSchool("ATGUIGU");

        session.save(student);
    }

    @Test
    public void testJoinedSubClassQuery() {
        // 查询操作:
        // 1. 查询父类记录，做一个左外连接查询
        List<Person2> persons = session.createQuery("FROM Person2").list();
        System.out.println(persons.size());
        // 2. 对于子类记录，做一个内连接查询
        List<Student2> students = session.createQuery("FROM Student2").list();
        System.out.println(students.size());
    }

    /**
     * 测试 <union-subclass/> 继承
     * 优点：
     * 1. 无需使用辨别者列
     * 2. 子类独有的字段能添加非空约束
     * 缺点：
     * 1. 存在冗余字段
     * 2. 若更新父表的字段，则更新的效率较低
     */
    @Test
    public void testUnionSubClassSave() {
        // 插入操作: 性能不错
        Person3 person = new Person3();
        person.setAge(11);
        person.setName("AA");

        session.save(person);

        Student3 student = new Student3();
        student.setAge(22);
        student.setName("BB");
        student.setSchool("ATGUIGU");

        session.save(student);
    }

    @Test
    public void testUnionSubClassQuery() {
        // 查询操作:
        // 1.查询父类记录，需把父表和子表记录汇总到一起再查询，性能较差
        List<Person3> persons = session.createQuery("FROM Person3").list();
        System.out.println(persons.size());
        // 2. 对于子类记录，只需要查询一张数据表
        List<Student3> students = session.createQuery("FROM Student3").list();
        System.out.println(students.size());
    }

    @Test
    public void testUnionSubClassUpdate() {
        String hql = "UPDATE Person3 p SET p.age = 20";
        session.createQuery(hql).executeUpdate();
    }
}

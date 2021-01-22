package me.minjae.spring.user.dao;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.sql.SQLException;
import java.util.EmptyStackException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import me.minjae.spring.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/test-applicationContext.xml")
// 2-20. 수동 DI를 적용
@DirtiesContext
public class UserDaoTest {
	
	// 2-17. 스프링 테스트 컨텍스트
	UserDao dao;
	
	// 2-15. 중복 코드를 제거
	private User user1;
	private User user2;
	private User user3;
	
	@Before
	public void setUp() {
//		this.dao = this.context.getBean("userDao", UserDao.class);
		dao = new UserDao();
		DataSource dataSource = new SingleConnectionDataSource("jdbc:mariadb://127.0.0.1:3306/testdb", "root", "111111", true);
		dao.setDataSource(dataSource);
	}
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {
		
		// 2-9. added addAndGet()
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		User user = new User();
		user.setId("hyumee");
		user.setName("박성철");
		user.setPassword("springno1");

		dao.add(user);
		assertThat(dao.getCount(), is(1));
		
		User user2 = dao.get(user.getId());
		
		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getPassword(), is(user.getPassword()));
	}
	
	@Test
	public void count() throws  ClassNotFoundException, SQLException {
		
		user1 = new User("hyumee", "박성철", "springno1");
		user2 = new User("leegw70", "이길", "springno2");
		user3 = new User("hbumni", "박범", "springno3");

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	
	}
	
	// 2-13
//	@Test(expected = EmptyStackException.class)
	public void getUserFailure() throws ClassNotFoundException, SQLException {
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknown_id");
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		JUnitCore.main("me.minjae.spring.user.dao.UserDaoTest");
//		// ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
//		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
//		UserDao dao = context.getBean("userDao", UserDao.class);
//		
//		User user = new User();
//		user.setId("whiteShip");
//		user.setName("백기선");
//		user.setPassword("married");
//		
//		dao.add(user);
//		
//		System.out.println(user.getId() + " 등록 성공");
//		
//		User user2 = dao.get(user.getId());
//		System.out.println(user2.getName());
//		System.out.println(user2.getPassword());
//		
//		System.out.println(user2.getId() + " 조회 성공");
	}
}

package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.dto.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/applicationContext.xml")
public class UserDaoTest {

	@Autowired
	UserDao dao;
	
	User user1;
	User user2;
	User user3;
	
	@Before
	public void before() {
		user1 = new User("jimin", "박지민", "pass1");
		user2 = new User("minchae", "박민채", "pass2");
		user3 = new User("jiyoung", "김지영", "pass3");
	}
	
	@Test
	public void addAndGet() throws SQLException {
		dao.deleteAll();
		Assert.assertThat(dao.count(), is(0));
		
		dao.add(user1);
		Assert.assertThat(dao.count(), is(1));
		
		User tempUser = dao.getUser(user1.getId());
		Assert.assertThat(tempUser.getName(), is(user1.getName()));
		Assert.assertThat(tempUser.getPassword(), is(user1.getPassword()));
	}
	
	@Test
	public void count() throws SQLException{
		dao.deleteAll();
		Assert.assertThat(dao.count(), is(0));
		
		dao.add(user1);
		Assert.assertThat(dao.count(), is(1));
		
		dao.add(user2);
		Assert.assertThat(dao.count(), is(2));
		
		dao.add(user3);
		Assert.assertThat(dao.count(), is(3));
	}
	
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		dao.deleteAll();
		Assert.assertEquals(dao.count(), 0);
		
		dao.getUser("unknown");
	}
	
	@Test(expected=PSQLException.class)
	public void addDuplicate() throws SQLException {
		dao.deleteAll();
		Assert.assertEquals(dao.count(), 0);
		
		dao.add(user1);
		Assert.assertEquals(dao.count(), 1);
		
		dao.add(user1);
		
	}
}

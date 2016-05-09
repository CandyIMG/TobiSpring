package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.domain.Level;
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
		user1 = new User("jimin", "박지민", "pass1", Level.BASIC, 1, 0);
		user2 = new User("minchae", "박민채", "pass2", Level.SILVER, 55, 10);
		user3 = new User("jiyoung", "김지영", "pass3", Level.GOLD, 100, 40);
	}
	
	@Test
	public void addAndGet() throws SQLException {
		dao.deleteAll();
		Assert.assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		Assert.assertThat(dao.getCount(), is(1));
		
		User tempUser = dao.getUser(user1.getId());
		checkSameUser(tempUser, user1);
	}
	
	@Test
	public void getCount() throws SQLException{
		dao.deleteAll();
		Assert.assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		Assert.assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		Assert.assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		Assert.assertThat(dao.getCount(), is(3));
	}
	
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		dao.deleteAll();
		Assert.assertEquals(dao.getCount(), 0);
		
		dao.getUser("unknown");
	}
	
	@Test(expected=DuplicateKeyException.class)
	public void addDuplicate() throws SQLException {
		dao.deleteAll();
		Assert.assertEquals(dao.getCount(), 0);
		
		dao.add(user1);
		Assert.assertEquals(dao.getCount(), 1);
		
		dao.add(user1);
		
	}
	
	private void checkSameUser(User user1, User user2) {
		Assert.assertThat(user1.getId(), is(user2.getId()));
		Assert.assertThat(user1.getName(), is(user2.getName()));
		Assert.assertThat(user1.getPassword(), is(user2.getPassword()));
		Assert.assertThat(user1.getLevel(), is(user2.getLevel()));
		Assert.assertThat(user1.getLogin(), is(user2.getLogin()));
		Assert.assertThat(user1.getRecommend(), is(user2.getRecommend()));
		
	}
}

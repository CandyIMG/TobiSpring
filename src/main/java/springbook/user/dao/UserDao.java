package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.Level;
import springbook.user.dto.User;

public class UserDao {
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void add(final User user) {
//		this.jdbcTemplate.update(new PreparedStatementCreator() {
//			
//			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//				PreparedStatement ps = con.prepareStatement("INSERT INTO users VALUES (?, ?, ?)");
//				ps.setString(1, user.getId());
//				ps.setString(2, user.getName());
//				ps.setString(3, user.getPassword());
//				return ps;
//			}
//		});
//		
		this.jdbcTemplate.update("INSERT INTO users VALUES (?, ?, ?, ?, ?, ?)", new Object[] {user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend()});
	}
	
	public User getUser(final String id) {
		// 템플릿 활용 소스
//		return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[] {id}, new RowMapper<User>() {
//			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//				User user = new User();
//				user.setId(rs.getString("id"));
//				user.setName(rs.getString("name"));
//				user.setPassword(rs.getString("password"));
//				return user;
//			}
//		});
//		
		
		return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[] {id}, this.userMapper);
		// 템플릿 활용 + beanMapper 활용
//		return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[] {id}, new BeanPropertyRowMapper<User>(User.class));

	}
	
	public List<User> getAll() {
		// 사용자 mappe 사용
//		return this.jdbcTemplate.query("SELECT * FROM users", this.userMapper);
		
		// 템플릿 활용 + beanMapper 활용
		return this.jdbcTemplate.query("SELECT * FROM users", new BeanPropertyRowMapper<User>(User.class));
	}
	
	
	public int getCount() {
		return this.jdbcTemplate.queryForObject("SELECT count(*) FROM users", Integer.class);
	}
	
	public void deleteAll() {
		this.jdbcTemplate.update("DELETE FROM users");
	}
	
	private RowMapper<User> userMapper = new RowMapper<User>() {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			return user;
		}
	};
}

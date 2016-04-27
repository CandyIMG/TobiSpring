package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import springbook.user.dto.User;

public class UserDao {
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void add(User user) throws SQLException {
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("INSERT INTO users VALUES (?, ?, ?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		ps.close();
		c.close();
	}
	
	public User getUser(String id) throws SQLException {
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
		ps.setString(1, id);
		ResultSet rs = null;
		User user = null;
		
		rs = ps.executeQuery();
		rs.next();
		user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
		
		ps.close();
		c.close();
		
		return user;
		
	}
	
	
	public int count() throws SQLException {
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("SELECT count(*) FROM users");
		ResultSet rs = null;
		int count = 0;
		
		rs = ps.executeQuery();
		rs.next();
		count = rs.getInt(1);
		
		ps.close();
		c.close();
		return count;
	}
	
	public void deleteAll() throws SQLException {
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("DELETE FROM users");
		ps.executeUpdate();
		ps.close();
		c.close();
	}
}

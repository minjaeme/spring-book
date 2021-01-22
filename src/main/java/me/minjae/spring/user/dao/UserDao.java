package me.minjae.spring.user.dao;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import me.minjae.spring.user.domain.Level;
import me.minjae.spring.user.domain.User;

/**
 * JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
 *
 * 1-42 DataSource를 사용하는 UserDao
 */
public class UserDao {
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private RowMapper<User> userMapper = 
			new RowMapper<User>() {
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
	
	public void add(final User user) throws ClassNotFoundException, SQLException {
		this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend) values(?, ?, ?, ?, ?, ?)", 
				user.getId(), user.getName(), user.getPassword(), user.getLevel(), user.getLogin(), user.getRecommend());
	}
	
	public void deleteAll() {
		this.jdbcTemplate.update("delete from users");
	}


	public int getCount() {
		return this.jdbcTemplate.query(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// TODO Auto-generated method stub
				return con.prepareStatement("select count(*) from users");
			}}, 
				new ResultSetExtractor<Integer>() {
				public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
					rs.next();
					return rs.getInt(1);
			}
		});
	}
	
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?",
				new Object[] {id}, this.userMapper);
	}
}

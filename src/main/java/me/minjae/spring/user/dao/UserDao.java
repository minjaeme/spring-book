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

import me.minjae.spring.user.domain.User;

/**
 * JDBC를 이용한 등록과 조회 기능이 있는 UserDao 클래스
 *
 * 1-42 DataSource를 사용하는 UserDao
 */
public class UserDao {
	private JdbcContext jdbcContext;
	private JdbcTemplate jdbcTemplate;
	
	// 3-22 
	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}

	// 1-42 UserDao
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.jdbcContext = new JdbcContext();
		jdbcContext.setDataSource(dataSource);
	}
	
	// 3-6
	
	// 사용자 데이터 추가
	public void add(final User user) throws ClassNotFoundException, SQLException {
		
		// 3-48
		this.jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)", user.getId(), user.getName(), user.getPassword());
		
		// 1
		// 3-16 중첩 클래스
//		this.jdbcContext.workWithStatementStrategy(
//			new StatementStrategy() {
//				public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
//					PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
//					ps.setString(1, user.getId());
//					ps.setString(2, user.getName());
//					ps.setString(3, user.getPassword());
//					return ps;
//				}
//			});
	}
	
	// 2.7 deleteAll() 메소드
	public void deleteAll() {
		
		this.jdbcTemplate.update("delete from users");
		
		// 2
//		this.jdbcTemplate.update(
//				new PreparedStatementCreator() {
//					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//						return con.prepareStatement("delete from users");
//					}
//				}
//				);
		
		// 1
//		this.jdbcContext.executeSql("delete from users");
	}
		
	
	
	// 3-11
	public void jdbcContextWtihStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		
		try {
			ps=stmt.makePreparedStatement(c);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null) { try { ps.close(); } catch (SQLException e) { }}
			if (c != null) { try { c.close(); } catch (SQLException e) { }}
		}
	}

	// 2.8 getCount() 메소드
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
		
		// 1
//		Connection c = dataSource.getConnection();
//		
//		PreparedStatement ps = c.prepareStatement("select count(*) from users");
//		
//		ResultSet rs = ps.executeQuery();
//		rs.next();
//		int count = rs.getInt(1);
//		
//		rs.close();
//		ps.close();
//		c.close();
//		
//		return count;
	}
	
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id", 
				new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		});
	}

	
	// 사용자 데이터 가져오기
	public User get(String id) throws ClassNotFoundException, SQLException {

		return this.jdbcTemplate.queryForObject("select * from users where id = ?",
				new Object[] {id},
				new RowMapper<User>() {
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				return user;
			}
		});
		
		
		// 1
//		Connection c = dataSource.getConnection();
//		
//		// 2. SQL을 담은 Statement 또는 PreparedStatement 를 만든다.
//		PreparedStatement ps = c.prepareStatement(
//				"SELECT * FROM users WHERE id = ?");
//		ps.setString(1, id);
//		
//		// 3. 만들어진 Statement를 실행한다.
//		// 실행 결과를 ResultSet으로 받아서 정보를 저장할  오브젝트에 옮긴다.
//		ResultSet rs = ps.executeQuery();
//		
//		User user = null;
//		if (rs.next()) {
//			user = new User();
//			user.setId(rs.getString("id"));
//			user.setName(rs.getString("name"));
//			user.setPassword(rs.getString("password"));
//		}
//		
//		// 4. 작업중에 생성된 Connection, Statement, ResultSet 리소스를 닫아 준다.
//		rs.close();
//		ps.close();
//		c.close();
//		
////		if (user == null) throw new 
//		return user;
	}
}

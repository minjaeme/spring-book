package me.minjae.spring.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import me.minjae.spring.user.domain.User;

// 3-13	
public class AddStatement implements StatementStrategy {
	
	User user;
	
	public AddStatement(User user) {
		this.user = user;
	}
	
	public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
		PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
		
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		return ps;
	}

}

package com.epam.training.jp.jdbc.excercises.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.epam.training.jp.jdbc.excercises.dao.MenuFoodDao;

public class JdbcMenuFoodDao extends GenericJdbcDao implements MenuFoodDao {

	public JdbcMenuFoodDao(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void removeMenuFoods(int menuId) {
		String sql = "delete from menu_food where menu_id=?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, menuId);
			
			ps.execute();
			System.out.println("delete menu_food");
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException();
		}
		
	}
}

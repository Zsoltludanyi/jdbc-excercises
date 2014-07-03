package com.epam.training.jp.jdbc.excercises.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.epam.training.jp.jdbc.excercises.dao.FoodDao;
import com.epam.training.jp.jdbc.excercises.domain.Food;

public class JdbcFoodDao extends GenericJdbcDao implements FoodDao {

	public JdbcFoodDao(DataSource dataSource) {
		super(dataSource);
	}
	
	
	@Override
	public Food findFoodByName(String name) {
		String sql = "Select * from food where name=?";
		Food food = new Food();
		try (Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, name);			
			ps.execute();
			ResultSet rs = ps.getResultSet();
			
			while (rs.next()) {		
				food.setId(rs.getInt(1));
				food.setCalories(rs.getInt(2));
				food.setVegan(rs.getBoolean(3));
				food.setName(rs.getString(4));
				food.setPrice(rs.getInt(5));
							
			}
			return food;
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public void updateFoodPriceByName(String name, int newPrice) {
		//TODO: implement
		String sql = "UPDATE FOOD set price=? where name =?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, newPrice);
			ps.setString(2, name);
			
			ps.executeUpdate();
			System.out.println("UPDATED " +ps.getUpdateCount() + " record in the food table");
			
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException();
		}

	}


	@Override
	public void save(List<Food> foods) {
		//TODO: This one will be the homework!!!
		//      implement with batch
		String sql = "insert food (ID,CALORIES,ISVEGAN,NAME,PRICE) values (?,?,?,?,?)";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			conn.setAutoCommit(false);
			for(Food i: foods) {
				ps.setInt(1, i.getId());
				ps.setInt(2, i.getCalories());
				ps.setBoolean(3, i.isVegan());
				ps.setString(4, i.getName());
				ps.setInt(5, i.getPrice());
				ps.addBatch();
			}
			
			ps.executeBatch();
			ps.clearBatch();
			conn.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException();
		}

	}
	
	



	

}

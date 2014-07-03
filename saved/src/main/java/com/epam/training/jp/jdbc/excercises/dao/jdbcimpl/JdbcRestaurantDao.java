package com.epam.training.jp.jdbc.excercises.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.epam.training.jp.jdbc.excercises.dao.RestaurantDao;
import com.epam.training.jp.jdbc.excercises.domain.Address;
import com.epam.training.jp.jdbc.excercises.domain.Food;
import com.epam.training.jp.jdbc.excercises.domain.Restaurant;
import com.epam.training.jp.jdbc.excercises.dto.RestaurantWithAddress;

public class JdbcRestaurantDao extends GenericJdbcDao implements RestaurantDao {

	public JdbcRestaurantDao(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public List<Food> getFoodsAvailable(Date date, String restaurantName) {
		String sql = "select food.id, food.Calories, food.ISVEGAN , food.name, food.price  from restaurant inner join menu inner join menu_food inner join" + 
				" food where  restaurant.name = ?  and menu.fromdate > ?  group by food.name ";
		List<Food> list = new ArrayList<Food>();
		try (Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, restaurantName);
			ps.setDate(2, new java.sql.Date(date.getTime()));
			
			ps.execute();
			ResultSet rs = ps.getResultSet();
			
			while (rs.next()){
				Food food = new Food();
				food.setId(rs.getInt(1));
				food.setCalories(rs.getInt(2));
				food.setVegan(rs.getBoolean(3));
				food.setName(rs.getString(4));
				food.setPrice(rs.getInt(5));
				list.add(food);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException();
		}
		return list;	
	}

	@Override
	public List<RestaurantWithAddress> getAllRestaurantsWithAddress() {
		String sql = "select r.*, a.*  from restaurant r inner join address a ";
		List<RestaurantWithAddress> list = new ArrayList<RestaurantWithAddress>();
		try (Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)) {
						
			ps.execute();
			ResultSet rs = ps.getResultSet();
			
			while (rs.next()){
				Restaurant restaurant= new Restaurant();
				Address address = new Address();
				restaurant.setId(rs.getInt(1));
				restaurant.setName(rs.getString(2));
				restaurant.setAddressId(rs.getInt(3));
				address.setId(rs.getInt(4));
				address.setCity(rs.getString(5));
				address.setCountry(rs.getString(6));
				address.setStreet(rs.getString(7));
				address.setZipCode(rs.getString(8));
				list.add(new RestaurantWithAddress(restaurant,address));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException();
		}
		return list;
		
	}
	



}

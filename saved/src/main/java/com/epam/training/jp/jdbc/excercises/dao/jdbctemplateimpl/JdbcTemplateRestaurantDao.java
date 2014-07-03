package com.epam.training.jp.jdbc.excercises.dao.jdbctemplateimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.epam.training.jp.jdbc.excercises.dao.RestaurantDao;
import com.epam.training.jp.jdbc.excercises.domain.Address;
import com.epam.training.jp.jdbc.excercises.domain.Food;
import com.epam.training.jp.jdbc.excercises.domain.Restaurant;
import com.epam.training.jp.jdbc.excercises.dto.RestaurantWithAddress;

public class JdbcTemplateRestaurantDao extends JdbcDaoSupport implements RestaurantDao {

	public JdbcTemplateRestaurantDao(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<Food> getFoodsAvailable(Date date, String restaurantName) {
		//TODO: implement, use RowMapper
		String sql = "select food.id, food.Calories, food.ISVEGAN , food.name, food.price  from restaurant inner join menu inner join menu_food inner join" + 
				" food where  restaurant.name = ?  and menu.fromdate > ?  group by food.name ";
		List<Food> list = this.getJdbcTemplate().query(sql, new FoodMapper(),restaurantName, new Date());
		return list;		
	}

	@Override
	public List<RestaurantWithAddress> getAllRestaurantsWithAddress() {
		//TODO: implement, use RowMapper
		//throw new UnsupportedOperationException();
		String sql = "select r.*, a.*  from restaurant r inner join address a ";
		List<RestaurantWithAddress> list = this.getJdbcTemplate().query(sql, new RestaurantWithAddressRowMapper());
		return list;
	}
	
	private static final class FoodMapper implements RowMapper<Food> {
		@Override
		public Food mapRow(ResultSet rs, int rowNum) throws SQLException {
			Food food = new Food();
			food.setId(rs.getInt(1));
			food.setCalories(rs.getInt(2));
			food.setVegan(rs.getBoolean(3));
			food.setName(rs.getString(4));
			food.setPrice(rs.getInt(5));
			return food;
		}
	}
	private static final class RestaurantWithAddressRowMapper implements RowMapper<RestaurantWithAddress> {
		@Override
		public RestaurantWithAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
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
			return new RestaurantWithAddress(restaurant,address);
		}
	}
}

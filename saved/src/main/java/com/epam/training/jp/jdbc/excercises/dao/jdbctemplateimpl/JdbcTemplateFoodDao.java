package com.epam.training.jp.jdbc.excercises.dao.jdbctemplateimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.epam.training.jp.jdbc.excercises.dao.FoodDao;
import com.epam.training.jp.jdbc.excercises.domain.Food;

import static org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils.createBatch;


public class JdbcTemplateFoodDao extends JdbcDaoSupport implements FoodDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate; 
	
	public JdbcTemplateFoodDao(DataSource dataSource) {
		setDataSource(dataSource);
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public Food findFoodByName(String name) {
		//TODO: implement
		String sql = "Select * from food where name=?";
		Food food = this.getJdbcTemplate().queryForObject(
				sql, 
				new Object[]{name}, 
				new RowMapper<Food>() {
					public Food mapRow (ResultSet rs, int rowNum) throws SQLException {
						Food food = new Food();
						food.setId(rs.getInt(1));
						food.setCalories(rs.getInt(2));
						food.setVegan(rs.getBoolean(3));
						food.setName(rs.getString(4));
						food.setPrice(rs.getInt(5));
						return food;
					}
				});
		return food;
	}


	@Override
	public void updateFoodPriceByName(String name, int newPrice) {
		//TODO: implement
		String sql = "UPDATE FOOD set price= ? where name = ?";
		int  affectedRow = this.getJdbcTemplate().update(sql,newPrice, name );
		if(affectedRow != 1) {
			throw new RuntimeException("Not one row updated!");
		}
	}

	@Override
	public void save(List<Food> foods) {
		//TODO: implement with batch
		SqlParameterSource[] batch = createBatch(foods.toArray());
		String sql = "insert INTO food VALUES(null, :Calories, :Vegan, :Name , :Price)";
		namedParameterJdbcTemplate.batchUpdate(sql, batch);
		
	}
}

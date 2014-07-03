package com.epam.training.jp.jdbc.excercises.dao.jdbctemplateimpl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.epam.training.jp.jdbc.excercises.dao.AddressDao;
import com.epam.training.jp.jdbc.excercises.domain.Address;
import com.epam.training.jp.jdbc.excercises.domain.Food;

public class JdbcTemplateAddressDao extends JdbcDaoSupport implements AddressDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private SimpleJdbcInsert insertAddress;
	
	public JdbcTemplateAddressDao(DataSource dataSource) {		
		setDataSource(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.insertAddress = new SimpleJdbcInsert(dataSource).withTableName("Address").usingGeneratedKeyColumns("id"); 
	}

	public void add(Address address) {
		Map<String, Object> parameters = new HashMap<String, Object>(4);
		//parameters.put("id", address.getId());
		parameters.put("city", address.getCity());
		parameters.put("country", address.getCountry());
		parameters.put("street", address.getStreet());
		parameters.put("zipCode", address.getZipCode());
		Number newId = insertAddress.executeAndReturnKey(parameters);
		//insertAddress.executeAndReturnKey(parameters);
		address.setId(newId.intValue());
	}
	
	@Override
	public void save(Address address) {
		//TODO: implement, use NamedParameterJdbcTemplate
		//simplejdbcinsert  átírni és 1 updated is kell
		/*KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = "INSERT INTO address ( CITY, COUNTRY, STREET, ZIPCODE) VALUES (:city, :country, :street, :zipCode)";
		SqlParameterSource paramsource = new BeanPropertySqlParameterSource(address);
		
		this.namedParameterJdbcTemplate.update(sql,paramsource,keyHolder);
		address.setId(keyHolder.getKey().intValue());*/
		add(address);
	}

}

package com.epam.training.jp.jdbc.excercises.dao.jdbctemplateimpl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.epam.training.jp.jdbc.excercises.dao.MenuDao;

public class JdbcTemplateMenuDao extends JdbcDaoSupport implements MenuDao {

	public JdbcTemplateMenuDao(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public void removeMenu(int id) {
		//TODO: implement
		String sql = "delete from menu where id=?";
		int  affectedRow = this.getJdbcTemplate().update(sql,id);
		System.out.println("delete from menu rows " + affectedRow );
	}
}

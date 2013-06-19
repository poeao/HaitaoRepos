package com.me.smvc.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.me.smvc.bean.PageHelper;
import com.me.smvc.dao.Jdbc;

@Repository
@Transactional
public class JdbcSupport implements Jdbc {
	
	@Resource
	protected JdbcTemplate jdbcTemplate;
	
	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public int update(String sql, Object... args) {
		return jdbcTemplate.update(sql, args);
	}
	
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public long stat(String sql, Object... args) {
		return jdbcTemplate.queryForLong(sql, args);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public <T> T find(String sql, Class<T> clazz, Object... args) {
		try {
			return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(clazz), args);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public <T> List<T> query(String sql, Class<T> clazz, Object... args) {
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(clazz), args);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public <T> PageHelper<T> query(String sql, long count, Class<T> clazz, Object... args) {
		int page = 0;
		int size = 0;
		
		if (args != null && args.length >= 2) {
			page = (Integer)args[args.length - 2];
			size = (Integer)args[args.length - 1];
		}
		page = (page < 1) ? 1 : page;
		size = (size < 1) ? 1 : size;
		int from = (page - 1) * size;
		
		args[args.length - 2] = size + from;
		//for oracle paging
		args[args.length - 1] = from;
		
		List<T> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(clazz), args);
		
		return new PageHelper<T>(list, count, page, size);
	}
	
	@Override
	public <T> int updateNamed(String namedSql, T bean) {
		String sql = NamedParameterUtils.parseSqlStatementIntoString(namedSql);
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(namedSql);
		
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(bean);
		List<SqlParameter> params = NamedParameterUtils.buildSqlParameterList(parsedSql, source);
		
		Object[] args = NamedParameterUtils.buildValueArray(parsedSql, source, params);
		
		return jdbcTemplate.update(sql, args);
	}
	
	@Override
	public <T> int[] updateNamed(String namedSql, List<T> beans) {
		String sql = NamedParameterUtils.parseSqlStatementIntoString(namedSql);
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(namedSql);
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		
		for(T bean : beans){
			BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(bean);
			List<SqlParameter> params = NamedParameterUtils.buildSqlParameterList(parsedSql, source);
			
			Object[] args = NamedParameterUtils.buildValueArray(parsedSql, source, params);
			batchArgs.add(args);
		}
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
	@Override
	public int updateNamedMap(String namedSql, Map<String, Object> paramMap) {
		String sql = NamedParameterUtils.parseSqlStatementIntoString(namedSql);
		Object[] args = NamedParameterUtils.buildValueArray(namedSql, paramMap);
		
		return jdbcTemplate.update(sql, args);
	}
	
	@Override
	public int[] updateNamedMap(String namedSql, List<Map<String, Object>> paramMaps) {
		String sql = NamedParameterUtils.parseSqlStatementIntoString(namedSql);
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		
		for(Map<String, Object> paramMap : paramMaps){
			Object[] args = NamedParameterUtils.buildValueArray(namedSql, paramMap);	
			batchArgs.add(args);
		}
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}
}
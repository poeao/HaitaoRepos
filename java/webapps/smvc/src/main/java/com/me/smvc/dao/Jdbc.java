package com.me.smvc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.me.smvc.bean.PageHelper;

public interface Jdbc {

	// JdbcTemplate
	    public JdbcTemplate getJdbcTemplate();
	    // 更新
	    public int update(String sql, Object... args);
	     
	    // 统计
	    public long stat(String sql, Object... args);
	     
	    // 查找
	    public <T> T find(String sql, Class<T> clazz, Object... args);
	    // 查询
	    public <T> List<T> query(String sql, Class<T> clazz, Object... args);
	    // 分页
	    public <T> PageHelper<T> query(String sql, long count, Class<T> clazz, Object... args);
	    // 命名更新
	    public <T> int updateNamed(String namedSql, T bean);
	     
	    // 批量命名更新
	    public <T> int[] updateNamed(String namedSql, List<T> beans);
	     
	    // 命名更新
	    public int updateNamedMap(String namedSql, Map<String, Object> paramMap);
	    // 批量命名更新
	    public int[] updateNamedMap(String namedSql, List<Map<String, Object>> paramMaps);

}

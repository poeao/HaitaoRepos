package com.me.smvc.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.me.smvc.bean.PageHelper;
import com.me.smvc.bean.test.User;
import com.me.smvc.dao.Jdbc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JdbcTest {

	@Resource
	private Jdbc jdbc;
	
	@Test
	public void update(){
		jdbc.update("delete from user where id = ?", 110);
	}
	
	@Test
	public void updateNamed(){
		User user = new User();
		user.setUsername("username");
		user.setRealname("realname");
		user.setPassword("password");
		
		jdbc.updateNamed("insert into user(username, realname, password, memo) "+
				         "values(:username, :realname, :password, :memo)", user);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void batchUpdate(){
		List<User> list = new ArrayList<User>(){{
			add(new User(1, null, null, "changePassword", null));
			add(new User(2, null, null, "changePassword", null));
			add(new User(3, null, null, "changePassword", null));
		}};
		
		jdbc.updateNamed("update user set password = :password where id = :id", list);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void updateMap(){
		Map<String, Object> map = new HashMap<String, Object>(){{
			put("password", "changePawword");
			put("id", 110);	
		}};
		
		jdbc.updateNamedMap("update user set password = :password where id = :id", map);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void batchUpdateMap(){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list.add(new HashMap<String, Object>() {{
			put("password", "changePawword");
			put("id", 110);
		}});
		
		list.add(new HashMap<String, Object>() {{
			put("password", "changePawword");
			put("id", 109);
		}});
		
		list.add(new HashMap<String, Object>() {{
			put("password", "changePawword");
			put("id", 108);
		}});
		
		jdbc.updateNamedMap("update user set password = :password where id = :id", list);
	}
	
	@Test
	public void query(){
		User user = jdbc.find("select * from user where id = ?", User.class, 110);
		System.out.println(user);
		
		List<User> list = jdbc.query("select * from user", User.class);
		for(User obj : list){
			System.out.println(obj);
		}
		
		long count = jdbc.stat("select count(*) from user");
		PageHelper<User> result = jdbc.query("select * from user limit ?, ?", count, User.class, 1, 10);
		for(User oo : result.getList()){
			System.out.println(oo);
		}
	}
}

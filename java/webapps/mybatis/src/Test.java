import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.me.ibatis.bean.Person;


public class Test {

	 public static void main(String[] args) {  
	        SqlSessionFactory factory = null;  
	        try {  
	            factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("Configuration.xml"));  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        SqlSession sqlSession = factory.openSession();  
	        Person p = (Person) sqlSession.selectOne("com.me.dao.PersonDao.selectUserById", 5);  
	        System.out.println(p.getName());  
	    }  
}

package com.me.smvc.test.base;


import org.junit.runner.RunWith;   
import org.springframework.test.context.ContextConfiguration;   
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;   
import org.springframework.transaction.annotation.Transactional;   

/**
 * 能够自动回滚的用于测试的父类
 * @author Haitao Song
 *
 */
@ContextConfiguration(locations = "classpath:applicationContext.xml")  
@RunWith(SpringJUnit4ClassRunner.class) 
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)   
public abstract class AbstractTestCase extends AbstractTransactionalJUnit4SpringContextTests  {   
  
}  
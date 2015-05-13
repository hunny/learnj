package hh.learnj.testj;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
//事务回滚
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
//事务
@Transactional 
@ContextConfiguration(locations = {"classpath:spring-base.xml", "classpath:spring-mybatis.xml"})
public class TestJunitSpring {
	
	private static final Logger logger = Logger.getLogger(TestJunitSpring.class);
	
	@Test
	public void testLogger() {
		logger.debug("test logger.");
	}

}

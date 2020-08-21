package org.kpu.ihpweb.transaction.test;


import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class MyBatisTest {

	@Autowired
	private SqlSessionFactory sqlFactory;
	
	@Test
	public void testFactory() throws Exception {
		try(SqlSession session = sqlFactory.openSession()) {
			System.out.println(session);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

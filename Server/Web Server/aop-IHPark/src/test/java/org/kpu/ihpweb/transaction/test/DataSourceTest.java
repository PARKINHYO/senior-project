package org.kpu.ihpweb.transaction.test;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml" })
public class DataSourceTest {

	@Autowired
	private DataSource ds;
	
	@Test
	public void testConntection() throws Exception {
		try(Connection con = ds.getConnection()) {
			System.out.println(con);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}

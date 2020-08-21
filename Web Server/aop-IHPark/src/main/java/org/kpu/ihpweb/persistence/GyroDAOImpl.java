package org.kpu.ihpweb.persistence;

import org.apache.ibatis.session.SqlSession;
import org.kpu.ihpweb.domain.GyroVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GyroDAOImpl implements GyroDAO {

	@Autowired
	SqlSession sqlSession;

	private static final String namespace = "org.kpu.ihpweb.mapper.GyroMapper";

	@Override
	public GyroVO read(String device_name) throws Exception {
		return sqlSession.selectOne(namespace + ".read", device_name);
	}

}

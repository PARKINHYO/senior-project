package org.kpu.ihpweb.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.kpu.ihpweb.domain.ChildVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ChildDAOImpl implements ChildDAO {

	@Autowired
	SqlSession sqlSession;

	private static final String namespace = "org.kpu.ihpweb.mapper.ChildMapper";

	@Override
	public List<ChildVO> listAll() throws Exception {
		
		return sqlSession.selectList(namespace + ".listAll"); 
	}

	@Override
	public ChildVO read(String device_name) throws Exception {
		return sqlSession.selectOne(namespace + ".read", device_name);
	}

}

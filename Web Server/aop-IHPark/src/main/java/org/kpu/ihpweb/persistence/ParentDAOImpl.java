package org.kpu.ihpweb.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.kpu.ihpweb.domain.ParentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ParentDAOImpl implements ParentDAO {

	@Autowired
	SqlSession sqlSession;

	private static final String namespace = "org.kpu.ihpweb.mapper.ParentMapper";

	@Override
	public List<ParentVO> listAll() throws Exception {
		return sqlSession.selectList(namespace + ".listAll");

	}

	@Override
	public ParentVO read(int bno) throws Exception {
		return sqlSession.selectOne(namespace + ".read", bno);
		
	}

}

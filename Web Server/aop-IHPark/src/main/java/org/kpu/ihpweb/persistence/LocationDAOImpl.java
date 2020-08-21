package org.kpu.ihpweb.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.kpu.ihpweb.domain.LocationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDAOImpl implements LocationDAO {

	@Autowired
	SqlSession sqlSession;

	private static final String namespace = "org.kpu.ihpweb.mapper.LocationMapper";

	@Override
	public List<LocationVO> listChildLocation(String device_name) throws Exception {
		return sqlSession.selectList(namespace + ".listChildLocation", device_name);
	}

	/*
	 * @Override public List<HashMap<String, String>> listChildLocationJson(String
	 * device_name) throws Exception { // TODO Auto-generated method stub return
	 * sqlSession.selectList(namespace + ".listChildLocationJson", device_name); }
	 */

}

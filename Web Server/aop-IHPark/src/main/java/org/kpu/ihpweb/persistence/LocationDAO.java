package org.kpu.ihpweb.persistence;

import java.util.List;

import org.kpu.ihpweb.domain.LocationVO;

public interface LocationDAO {
	
	public List<LocationVO> listChildLocation(String device_name) throws Exception;
	// public List<HashMap<String, String>> listChildLocationJson(String device_name) throws Exception;

	
}

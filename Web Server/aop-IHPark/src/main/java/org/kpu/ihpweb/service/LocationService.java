package org.kpu.ihpweb.service;

import java.util.List;

import org.kpu.ihpweb.domain.LocationVO;

public interface LocationService {
	public List<LocationVO> listChildLocation(String device_name) throws Exception;
	
}

package org.kpu.ihpweb.service;

import java.util.List;

import org.kpu.ihpweb.domain.LocationVO;
import org.kpu.ihpweb.persistence.LocationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService{
	
	@Autowired
	private LocationDAO locationDAO;

	@Override
	public List<LocationVO> listChildLocation(String device_name) throws Exception {
		return locationDAO.listChildLocation(device_name);
	}

}

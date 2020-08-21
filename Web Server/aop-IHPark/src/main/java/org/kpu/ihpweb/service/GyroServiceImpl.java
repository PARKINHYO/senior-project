package org.kpu.ihpweb.service;

import org.kpu.ihpweb.domain.GyroVO;
import org.kpu.ihpweb.persistence.GyroDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GyroServiceImpl implements GyroService{
	
	@Autowired
	private GyroDAO gyroDAO;

	@Override
	public GyroVO read(String device_name) throws Exception {
		return gyroDAO.read(device_name);
	}

}

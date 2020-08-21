package org.kpu.ihpweb.service;

import org.kpu.ihpweb.domain.GyroVO;

public interface GyroService {
	public GyroVO read(String device_name) throws Exception;

}

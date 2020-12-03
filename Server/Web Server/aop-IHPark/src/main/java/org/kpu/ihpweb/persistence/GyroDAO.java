package org.kpu.ihpweb.persistence;

import org.kpu.ihpweb.domain.GyroVO;

public interface GyroDAO{
	
	public GyroVO read(String device_name) throws Exception;
}

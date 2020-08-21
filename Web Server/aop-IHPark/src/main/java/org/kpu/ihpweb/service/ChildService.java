package org.kpu.ihpweb.service;

import java.util.List;

import org.kpu.ihpweb.domain.ChildVO;

public interface ChildService {
	public List<ChildVO> listAll() throws Exception;
	public ChildVO read(String device_name) throws Exception;
}

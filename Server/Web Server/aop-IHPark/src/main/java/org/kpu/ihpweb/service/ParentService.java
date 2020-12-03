package org.kpu.ihpweb.service;

import java.util.List;

import org.kpu.ihpweb.domain.ParentVO;

public interface ParentService {
	public List<ParentVO> listAll() throws Exception;
	public ParentVO read(int id) throws Exception;
}

package org.kpu.ihpweb.persistence;

import java.util.List;

import org.kpu.ihpweb.domain.ChildVO;

public interface ChildDAO {
	public List<ChildVO> listAll() throws Exception;
	public ChildVO read(String device_name) throws Exception;
}

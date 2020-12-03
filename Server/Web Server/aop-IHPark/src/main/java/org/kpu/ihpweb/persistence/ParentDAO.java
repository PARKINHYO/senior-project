package org.kpu.ihpweb.persistence;

import java.util.List;

import org.kpu.ihpweb.domain.ParentVO;

public interface ParentDAO {
	public List<ParentVO> listAll() throws Exception;
	public ParentVO read(int id) throws Exception;
}

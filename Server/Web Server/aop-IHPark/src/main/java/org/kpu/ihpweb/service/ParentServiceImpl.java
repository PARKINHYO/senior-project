package org.kpu.ihpweb.service;

import java.util.List;

import org.kpu.ihpweb.domain.ParentVO;
import org.kpu.ihpweb.persistence.ParentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentServiceImpl implements ParentService{
	
	@Autowired
	private ParentDAO parentDAO;

	@Override
	public List<ParentVO> listAll() throws Exception {
		return parentDAO.listAll();
		
	}

	@Override
	public ParentVO read(int id) throws Exception {
		return parentDAO.read(id);
		
	}
	
}

package org.kpu.ihpweb.service;

import java.util.List;

import org.kpu.ihpweb.domain.ChildVO;
import org.kpu.ihpweb.persistence.ChildDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildServiceImpl implements ChildService{
	
	@Autowired
	private ChildDAO childDAO;

	@Override
	public List<ChildVO> listAll() throws Exception {
		
		return childDAO.listAll();
	}

	@Override
	public ChildVO read(String device_name) throws Exception {
		return childDAO.read(device_name);
		
	}
	
}

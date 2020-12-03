package org.kpu.ihpweb.controller;

import java.util.List;

import org.kpu.ihpweb.domain.ChildVO;
import org.kpu.ihpweb.service.ChildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/child")
public class ChildController {

	@Autowired(required = true)
	private ChildService childService;
	
	@RequestMapping("list")
	public ModelAndView list() throws Exception {
		
		List<ChildVO> list = childService.listAll();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("child/list");
		mav.addObject("list", list);
		return mav;
	}
	
	@RequestMapping(value = "read", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String device_name) throws Exception {
		ChildVO childVO = new ChildVO();
		childVO = childService.read(device_name);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("child/view");
		mav.addObject("dto", childVO);
		return mav;
	}

}

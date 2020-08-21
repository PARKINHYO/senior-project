package org.kpu.ihpweb.controller;

import java.util.List;

import org.kpu.ihpweb.domain.ParentVO;
import org.kpu.ihpweb.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/parent")
public class ParentController {
	
	@Autowired(required = true)
	private ParentService parentService;
	
	@RequestMapping("list")
	public ModelAndView list() throws Exception {
		List<ParentVO> list = parentService.listAll();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("parent/list");
		mav.addObject("list", list);
		return mav;
	}
	
	@RequestMapping(value = "read", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam int id) throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("parent/view");
		mav.addObject("dto", parentService.read(id));
		return mav;
	}
	
	

}

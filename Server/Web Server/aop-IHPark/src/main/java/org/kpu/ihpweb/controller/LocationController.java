package org.kpu.ihpweb.controller;

import java.util.List;

import org.kpu.ihpweb.domain.LocationVO;
import org.kpu.ihpweb.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
@RequestMapping("/location")
public class LocationController {
	
	@Autowired(required = true)
	private LocationService locationService;
	
	@RequestMapping(value = "listChdLoc", method = RequestMethod.GET)
	public ModelAndView list(String device_name) throws Exception {
		List<LocationVO> list = locationService.listChildLocation(device_name);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("location/list");
		mav.addObject("list", list);
		return mav;
	}
	
	@RequestMapping(value = "listChdLocJson", method = RequestMethod.GET)
	public ModelAndView listJson(String device_name) throws Exception {
		List<LocationVO> list = locationService.listChildLocation(device_name);
		Gson gson = new Gson();
		String out = gson.toJson(list);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("location/listJson");
		mav.addObject("json", out);
		
		return mav;
	}

}

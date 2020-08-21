package org.kpu.ihpweb.controller;

import org.kpu.ihpweb.service.GyroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
@RequestMapping("/gyro")
public class GyroController {

	@Autowired(required = true)
	private GyroService gyroService;

	@RequestMapping(value = "read", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String device_name) throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("gyro/list");
		mav.addObject("dto", gyroService.read(device_name));
		return mav;
	}
	
	@RequestMapping(value = "readJson", method = RequestMethod.GET)
	public ModelAndView listJson(String device_name) throws Exception {
		
		Gson gson = new Gson();
		String out = gson.toJson(gyroService.read(device_name));

		ModelAndView mav = new ModelAndView();
		mav.setViewName("gyro/listJson");
		mav.addObject("json", out);
		
		return mav;
	}

}

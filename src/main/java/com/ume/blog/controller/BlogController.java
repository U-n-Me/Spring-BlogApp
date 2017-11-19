package com.ume.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ume.blog.services.BlogServices;

@RestController
public class BlogController {
	
	/*@Autowired
	BlogServices blogService;*/
	
	@RequestMapping(path = "/blog", method = RequestMethod.GET)
	public String getBlog(){
		return "{haa: 'kuuta'}";
	}
}

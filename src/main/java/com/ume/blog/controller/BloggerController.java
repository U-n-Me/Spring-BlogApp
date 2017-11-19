package com.ume.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ume.blog.lib.CreateBloggerEntity;
import com.ume.blog.services.BloggerService;

@RestController
public class BloggerController {
	@Autowired
	BloggerService bloggerService;
	
	
	@RequestMapping(path = "/blogger", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CreateBloggerEntity createBlogger(@RequestBody CreateBloggerEntity blogger) {
		System.out.println("Got request");
		return bloggerService.createBlogger(blogger);
	}

	@RequestMapping(path = "/blogger/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CreateBloggerEntity getBlogger(@PathVariable String userId) {
		return bloggerService.getBlogger(userId);
	}
}

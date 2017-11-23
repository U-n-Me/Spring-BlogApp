package com.ume.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ume.blog.lib.ApiResponseEntity;
import com.ume.blog.lib.BlogGlimseEntity;
import com.ume.blog.lib.BlogResponseEntity;
import com.ume.blog.lib.CreateBlogEntity;
import com.ume.blog.services.BlogServices;

@RestController
public class BlogController {
	
	@Autowired
	BlogServices blogService;	

	@RequestMapping(path = "/blog", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponseEntity<BlogResponseEntity> createBlog(@RequestBody CreateBlogEntity blog) {
		System.out.println("Got request");
		return blogService.createBlog(blog);
	}

	@RequestMapping(path = "/blog/{blogId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponseEntity<BlogResponseEntity> getBlog(@PathVariable String blogId) {
		return blogService.getBlog(blogId);
	}
	
	@RequestMapping(path = "/Allblogs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponseEntity<List<BlogGlimseEntity>> getBlogGlimseAll() {
		return blogService.getBlogGlimseAll();
	}
	
	@RequestMapping(path = "/Allblogs/{bloggerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponseEntity<List<BlogGlimseEntity>> getBlogGlimseForBlogger(@PathVariable String bloggerId) {
		return blogService.getBlogGlimseForBlogger(bloggerId);
	}
	
	@RequestMapping(path = "/like/{blogId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ApiResponseEntity<String> likeBlog(@PathVariable String blogId) {
		return blogService.likeBlog(blogId);
	}
	
}

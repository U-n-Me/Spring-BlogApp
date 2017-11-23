package com.ume.blog.services;

import java.util.List;

import com.ume.blog.lib.ApiResponseEntity;
import com.ume.blog.lib.BlogGlimseEntity;
import com.ume.blog.lib.BlogResponseEntity;
import com.ume.blog.lib.CreateBlogEntity;

public interface BlogServices {

	ApiResponseEntity<BlogResponseEntity> createBlog(CreateBlogEntity blog);

	ApiResponseEntity<BlogResponseEntity> getBlog(String blogId);
	
	void addBlog(String blogger, String blogId);
	
	ApiResponseEntity<String> likeBlog(String blogId);

	ApiResponseEntity<List<BlogGlimseEntity>> getBlogGlimseAll();

	ApiResponseEntity<List<BlogGlimseEntity>> getBlogGlimseForBlogger(String bloggerId);
	
}

package com.ume.blog.services;

import com.ume.blog.lib.ApiResponseEntity;
import com.ume.blog.lib.BloggerResponseEntity;
import com.ume.blog.lib.CreateBloggerEntity;


public interface BloggerService {
	
	public ApiResponseEntity<BloggerResponseEntity> createBlogger(CreateBloggerEntity blogger);
	
	public ApiResponseEntity<BloggerResponseEntity> getBlogger(String userId);
}

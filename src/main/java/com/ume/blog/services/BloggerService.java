package com.ume.blog.services;

import com.ume.blog.lib.CreateBloggerEntity;


public interface BloggerService {
	
	public CreateBloggerEntity createBlogger(CreateBloggerEntity blogger);
	
	public CreateBloggerEntity getBlogger(String userId);
}

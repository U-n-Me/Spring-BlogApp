package com.ume.blog.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.CqlTemplate;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.ume.blog.lib.CreateBloggerEntity;

@Service
public class BloggerServiceImpl implements BloggerService{
	@Autowired
	CqlTemplate cqlTmplete;
	
	public CreateBloggerEntity createBlogger(CreateBloggerEntity blogger) {
		// TODO Auto-generated method stub
		String userId = blogger.getUserId(), pass = blogger.getPassword();
		String query = "SELECT userId from blogSpace.bloggers WHERE userId = '"+ userId +"';";
		ResultSet rslt = cqlTmplete.query(query);
		if(rslt.all().size() > 0)
			System.out.println("User already exists");
		else{
			PreparedStatement preparedStatement = cqlTmplete.getSession()
					.prepare("insert into blogSpace.bloggers (userId, password,  blogs) values (?, ?, ?)");
			Statement insertStatement = preparedStatement.bind(userId, pass, new ArrayList<String>());
			cqlTmplete.execute(insertStatement);
		}
		return blogger;
	}

	public CreateBloggerEntity getBlogger(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}

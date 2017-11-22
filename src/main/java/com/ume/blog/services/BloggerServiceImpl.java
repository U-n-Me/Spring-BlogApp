package com.ume.blog.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.CqlTemplate;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.ume.blog.lib.ApiResponseEntity;
import com.ume.blog.lib.BloggerResponseEntity;
import com.ume.blog.lib.CreateBloggerEntity;

@Service
public class BloggerServiceImpl implements BloggerService {
	@Autowired
	CqlTemplate cqlTmplete;

	public ApiResponseEntity<BloggerResponseEntity> createBlogger(
			CreateBloggerEntity blogger) {
		ApiResponseEntity<BloggerResponseEntity> response = new ApiResponseEntity<>();
		String userId = blogger.getUserId(), pass = blogger.getPassword();
		List<String> blogs = new ArrayList<String>();
		String query = "SELECT userId from blogSpace.bloggers WHERE userId = '"
				+ userId + "';";
		ResultSet rslt = cqlTmplete.query(query);
		if (rslt.all().size() > 0) {
			response.setStatus("FAILURE");
			response.setCause("USER ALREADY EXISTS");
			return response;
		}

		PreparedStatement preparedStatement = cqlTmplete
				.getSession()
				.prepare(
						"insert into blogSpace.bloggers (userId, password,  blogs) values (?, ?, ?)");
		Statement insertStatement = preparedStatement.bind(userId, pass,
				blogs);
		cqlTmplete.execute(insertStatement);
		response.setStatus("SUCCESS");
		BloggerResponseEntity resEnt = BloggerResponseEntity.builder()
				.userId(blogger.getUserId()).blogs(blogs).build();
		response.setDetails(resEnt);
		return response;
	}

	public ApiResponseEntity<BloggerResponseEntity> getBlogger(String userId) {
		ApiResponseEntity<BloggerResponseEntity> response = new ApiResponseEntity<>();
		ResultSet rslt = cqlTmplete.query("SELECT blogs FROM blogSpace.bloggers where userId = '"+ userId +"';");
		for(Row row : rslt.all()){
			response.setStatus("SUCCESS");
			BloggerResponseEntity entity = BloggerResponseEntity.builder()
					.userId(userId).blogs(row.getList("blogs", String.class)).build();
			response.setDetails(entity);
			return response;
		}
		response.setStatus("FAILURE");
		response.setCause("NO SUCH USER EXISTS");
		return response;
	}
	
}

package com.ume.blog.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.CqlTemplate;
import org.springframework.stereotype.Service;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.utils.UUIDs;
import com.ume.blog.lib.ApiResponseEntity;
import com.ume.blog.lib.BlogResponseEntity;
import com.ume.blog.lib.CreateBlogEntity;

@Service
public class BlogServiceImpl implements BlogServices{
	@Autowired
	CqlTemplate cql;
	
	public ApiResponseEntity<BlogResponseEntity> createBlog(
			CreateBlogEntity blog) {
		ApiResponseEntity<BlogResponseEntity> response = new ApiResponseEntity<BlogResponseEntity>();
		String blogger = blog.getBlogger(), desc = blog.getDescription(), blogData = blog.getBlogData();
		// check if blogger exists
		ResultSet rslt = cql.query("SELECT userId FROM blogSpace.bloggers WHERE userId = '"+ blogger +"';");
		if(rslt.all().size() == 0){
			response.setStatus("FAILURE");
			response.setCause("NO SUCH USER EXISTS");
			return response;
		}
		String id = UUIDs.timeBased().toString();
		PreparedStatement preparedStatement = cql.getSession()
				.prepare("insert into blogSpace.blogs (blogId, likes,  blogData, description, blogger) values (?, ?, ?, ?, ?)");
		Statement insertStatement = preparedStatement.bind(id, 0, blogData, desc, blogger);
		cql.execute(insertStatement);
		// add blog to blogger
		addBlog(blogger, id);
		
		BlogResponseEntity entity = BlogResponseEntity.builder().blogData(blogData).blogger(blogger)
				.blogId(id).likes(0).description(desc).build();
		response.setStatus("SUCCESS");
		response.setDetails(entity);
		return response;
	}

	
	public ApiResponseEntity<BlogResponseEntity> getBlog(String blogId) {
		ApiResponseEntity<BlogResponseEntity> response = new ApiResponseEntity<BlogResponseEntity>();
		ResultSet rslt = cql.query("SELECT * FROM blogSpace.blogs WHERE blogId = '"+ blogId +"';");
		for(Row row : rslt.all()){
			response.setStatus("SUCCESS");
			BlogResponseEntity entity = BlogResponseEntity.builder().blogData(row.getString("blogData"))
					.blogger(row.getString("blogger")).blogId(row.getString("blogId"))
					.likes(row.getInt("likes")).description(row.getString("description")).build();
			response.setDetails(entity);
			return response;
		}
		response.setStatus("FAILURE");
		response.setCause("NO SUCH BLOG EXISTS");
		return response;
	}	

	// appends blog to blogger's blog list
	public void addBlog(String blogger, String blogId){
		cql.query("UPDATE blogSpace.bloggers SET blogs = blogs + ['" + blogId + "'] "
				+ "WHERE userId = '"+ blogger +"';");
	}
	
	// like a blog
	public ApiResponseEntity<String> likeBlog(String blogId){
		ApiResponseEntity<String> response = new ApiResponseEntity<String>();
		
		return response;
	}

}

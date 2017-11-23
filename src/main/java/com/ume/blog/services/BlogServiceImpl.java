package com.ume.blog.services;

import java.util.ArrayList;
import java.util.List;
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
import com.ume.blog.lib.BlogGlimseEntity;
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
				.prepare("INSERT INTO blogSpace.blogs (blogId, blogger, description, blogData) VALUES (?, ?, ?, ?)");
						
		Statement insertStatement = preparedStatement.bind(id, blogger, desc, blogData);
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
			long likes = 0;
			ResultSet rsltLike = cql.query("SELECT likes FROM blogSpace.blogLikes WHERE blogId = '"+ blogId +"';");
			for(Row r : rsltLike.all())likes = r.getLong("likes");
			BlogResponseEntity entity = BlogResponseEntity.builder().blogData(row.getString("blogData"))
					.blogger(row.getString("blogger")).blogId(row.getString("blogId"))
					.likes(likes).description(row.getString("description")).build();
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
		String query = "SELECT blogId FROM blogSpace.blogs WHERE blogId = '"+ blogId +"';";
		ResultSet rslt = cql.query(query);
		if(rslt.all().size() == 0){
			response.setStatus("FAILURE");
			response.setCause("NO SUCH BLOG EXISTS");
			return response;
		}
		query = "UPDATE blogSpace.blogLikes SET likes = likes + 1 "
				+ "WHERE blogId = '"+ blogId +"';";
		cql.execute(query);
		response.setStatus("SUCCESS");
		response.setDetails("LIKED");
		return response;
	}


	public ApiResponseEntity<List<BlogGlimseEntity>> getBlogGlimseAll() {
		ApiResponseEntity<List<BlogGlimseEntity>> response = new ApiResponseEntity<List<BlogGlimseEntity>>();
		List<BlogGlimseEntity> entity = new ArrayList<BlogGlimseEntity>();
		String query = "SELECT blogId, blogger, description FROM blogSpace.blogs;";
		ResultSet rslt = cql.query(query);
		for(Row row : rslt.all()){
			String blogId = row.getString("blogId");
			long likes = 0;
			ResultSet rsltLike = cql.query("SELECT likes FROM blogSpace.blogLikes WHERE blogId = '"+ blogId +"';");
			for(Row r : rsltLike.all())likes = r.getLong("likes");
			entity.add(BlogGlimseEntity.builder().blogId(blogId)
					.likes(likes).description(row.getString("description"))
					.blogger(row.getString("blogger")).build());
		}
		response.setStatus("SUCCESS");
		response.setDetails(entity);
		return response;
	}


	public ApiResponseEntity<List<BlogGlimseEntity>> getBlogGlimseForBlogger(
			String bloggerId) {
		ApiResponseEntity<List<BlogGlimseEntity>> response = new ApiResponseEntity<List<BlogGlimseEntity>>();
		List<BlogGlimseEntity> entity = new ArrayList<BlogGlimseEntity>();
		String query = "SELECT blogs FROM blogSpace.bloggers WHERE userId = '"+ bloggerId +"';";
		ResultSet blogRslt = cql.query(query);
		
		
		for(Row row : blogRslt.all()){
			response.setStatus("SUCCESS");
			List<String> blogIds = row.getList("blogs", String.class);
			
			for(String blogId : blogIds){
				long likes = 0;
				System.out.print(blogId);
				ResultSet rsltLike = cql.query("SELECT likes FROM blogSpace.blogLikes WHERE blogId = '"+ blogId +"';");
				for(Row r : rsltLike.all())likes = r.getLong("likes");
				
				ResultSet rsltBlog = cql.query("SELECT blogger, description "
						+ " FROM blogSpace.blogs WHERE blogId = '"+ blogId +"';");
				for(Row rowBlog : rsltBlog)
					entity.add(BlogGlimseEntity.builder().blogId(blogId)
						.likes(likes).description(rowBlog.getString("description"))
						.blogger(rowBlog.getString("blogger")).build());
				
			}
			
			response.setDetails(entity);
			return response;
		}
		
		response.setStatus("FAILURE");
		response.setCause("NO SUCH USER EXISTS");
		return response;
	}

}

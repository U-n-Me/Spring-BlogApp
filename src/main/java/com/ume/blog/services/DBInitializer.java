package com.ume.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.CqlTemplate;
import org.springframework.stereotype.Service;

@Service
public class DBInitializer {
	private CqlTemplate cqlTemplate;
	
	@Autowired
	public DBInitializer(CqlTemplate template){
		cqlTemplate = template;
		createKeySpace();
		createColumnFamilies();
	}

	private void createColumnFamilies() {
		String query = "CREATE TABLE IF NOT EXISTS blogSpace.BlogsMini( blogId text PRIMARY KEY,description text,"
				+ "blogger text;";
				//+ ") WITH CLUSTERING ORDER BY(likes DESC);";
		cqlTemplate.execute(query);
		
		query = "CREATE TABLE IF NOT EXISTS blogSpace.Blogs(blogId text, blogData text, likes counter "
				+ " PRIMARY KEY(blogId, blogData)) ;";
		
		query = "CREATE TABLE IF NOT EXISTS blogSpace.Bloggers(userId text PRIMARY KEY, "
				+ "password text, blogs List<text>) ;";
		
		cqlTemplate.execute(query);
		System.out.println("Tables created");
	}

	private void createKeySpace() {
		String query = "DROP KEYSPACE IF EXISTS blogSpace;";
		//cqlTemplate.execute(query);
		
		query = "CREATE KEYSPACE IF NOT EXISTS blogSpace WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : 1};";
		cqlTemplate.execute(query);
		System.out.println("Keyspace Created");
	}
}

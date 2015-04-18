package com.cocome.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class PostsDAOImpl implements PostsDAO {
	private Connection db_connection;
	private PreparedStatement statement;
	private String query;
	public PostsDAOImpl() throws ClassNotFoundException, SQLException{
		db_connection=DBConnection_Singleton.getInstance().getDBConnection();
	}
	public boolean insertPosts(Posts posts) throws SQLException{
		query="insert into posts(user_id,content,post_date,likes_count) values(?,?,?,?)";
		statement=(PreparedStatement) db_connection.prepareStatement(query);
		statement.setString(1,posts.getUser_id());
		statement.setString(2, posts.getContent());		
		statement.setTimestamp(3,posts.getPost_date());
		statement.setInt(4, posts.getLikes_count());
		
		int val=statement.executeUpdate();
		statement.close();
		if(val>0)
			return true;
		else
			return false;
	}
	
	@Override
	public List<Posts> getPostsOfUser(String user_id) throws SQLException {
		List<Posts> posts=new ArrayList<Posts>();
		query="select * from posts where user_id=?";
		statement=(PreparedStatement) db_connection.prepareStatement(query);
		statement.setString(1, user_id);
		ResultSet rs=statement.executeQuery();
		while(rs.next()){
			Posts post=new Posts();
			post.setPost_id(rs.getInt("post_id"));
			post.setUser_id(rs.getString("user_id"));
			post.setContent(rs.getString("content"));
			post.setPost_date(new Timestamp(rs.getDate("post_date").getTime()));
			post.setLikes_count(rs.getInt("likes_count"));
			posts.add(post);
		}
		statement.close();
		return posts;		
	}
		
}
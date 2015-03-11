package org.pti.poster.repository.post;

import org.pti.poster.model.post.BasicPost;
import org.pti.poster.model.post.Post;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository("inMemoryPostRepository")
public class InMemoryPostRepository implements PostRepository {

	public final static PostRepositoryType TYPE=PostRepositoryType.INMEMORY;
	Map<String, Post> allPosts;

	@PostConstruct
	public void init()
	{
		allPosts=new HashMap<>();
	}

	@Override
	public Post getPostById(String id) {
		return allPosts.get(id);
	}

	@Override
	public Post savePost(Post post) {
		// If everything is OK, DB should return ID of a new post entry, so return new Post with this ID
		String id= UUID.randomUUID().toString();
		Post savedPost= new BasicPost(id, post.getText());
		allPosts.put(id,savedPost);

		return savedPost;
	}
}

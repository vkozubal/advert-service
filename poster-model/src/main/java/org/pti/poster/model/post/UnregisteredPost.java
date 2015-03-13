package org.pti.poster.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;


public class UnregisteredPost extends AbstractPost {

	public final static PostType TYPE = PostType.UNREGISTERED_POST;

	public UnregisteredPost(String userId, String text) {
		super(TYPE, UUID.randomUUID().toString(), UUID.randomUUID().toString(), text);
	}

	@JsonIgnore
	@Override
	public String getId() {
		return super.getId();
	}

	@JsonIgnore
	@Override
	public String getDate() {
		return super.getDate();
	}
}

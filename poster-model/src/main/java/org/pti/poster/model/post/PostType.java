package org.pti.poster.model.post;


public enum PostType {
	BASIC_POST;

	private String typeName;

	private PostType() {
		this.typeName = name().replaceAll("_", ".").toLowerCase();
	}

	public String getTypeName() {
		return typeName;
	}

	@Override
	public String toString() {
		return typeName;
	}
}

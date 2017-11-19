package com.ume.blog.lib;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogResponseEntity {
	private String blogId;
	private String blogData;
	private String blogger;
	private int likes;
}

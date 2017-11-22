package com.ume.blog.lib;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBlogEntity {
	private String description;
	private String blogData;
	private String blogger;
}

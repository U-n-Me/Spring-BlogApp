package com.ume.blog.lib;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BloggerResponseEntity {
	private String userId;
	private List<String> blogs;
}

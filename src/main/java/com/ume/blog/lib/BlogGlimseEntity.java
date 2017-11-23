package com.ume.blog.lib;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogGlimseEntity {
	
 private String blogId;
 private String description;
 private long likes;
 private String blogger;
 
}

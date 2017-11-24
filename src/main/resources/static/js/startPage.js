
			$(function() {
				var bloggerParam = window.location.search.substring(1);
				var bloggerId = '';
				if(bloggerParam !== ''){
					bloggerParam = bloggerParam.split('=');
					if(bloggerParam[0] === 'blogger')
						bloggerId = bloggerParam[1];
					else alert("Incorrect Params");
				}
				var req = "api"+window.location.pathname+'/'+bloggerId;
				$.getJSON(req, function(data){
					renderData(data);
				});
			});
	 
			var container = $('.container');
			function renderData(data){
				if(data.status === 'SUCCESS'){
					var blogs = data.details;
					blogs.forEach(function(blog){
						var section = $('<section onclick = "openURL(\'blog?blogId='+blog.blogId+'\')"></section>');
						section.append(blog.description);
						var bloggerNlikes = $('<div class = "bloggerNlikes"></div>');
						bloggerNlikes.append('<div class = "blogger">'+blog.blogger+'</div>');
						bloggerNlikes.append('<div class = "likes">'+blog.likes+'</div>');
						section.append(bloggerNlikes);
						container.append(section);
					});
				}else{
					alert(data.status+': '+data.cause);
				}
			}
			
			function openURL(url) {
				window.location.href = url;
			}
			
			
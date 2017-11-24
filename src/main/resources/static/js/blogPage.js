
   var domBlogger = $('.container > p'),
   domBlogTitle = $('.container > section'),
   domBlogData = $('.container > article'),
   domLikes = $('.like > span');
   var blogId = 'INVALID_ID';
    $(function(){
		var urlParam = window.location.search.substring(1);
		if(urlParam !== ''){
			urlParam = urlParam.split('=');
			if(urlParam[0] === 'blogId')
				blogId = urlParam[1];
			else alert("Incorrect Params");
		}

		var req = "api"+window.location.pathname+'/'+blogId;
		$.getJSON(req, function(data){
			renderData(data);
		});
    });
    
    function renderData(data){
    	if(data.status == 'SUCCESS'){
    		var blog = data.details;
        	domBlogger.html(blog.blogger);
        	domBlogTitle.html(blog.description);
        	domBlogData.html(blog.blogData);
        	domLikes.html(blog.likes);
    	}else{
    		alert(data.status+': '+data.cause);
    	}
    }
    
    function likeBlog(){
    	$.getJSON("api/like/"+blogId, function(data){
    		if(data.status == 'SUCCESS'){
    			var likes = 1+parseInt(domLikes.html());
    			domLikes.html(likes);
    		}else{
    			alert(data.status+': '+data.cause);
    		}
    	});
    }
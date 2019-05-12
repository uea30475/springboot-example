$(function(){
	
	var listFriends;
	switch(title){
	case'Home':
		loadListFriends();
		break;
		
	}

//	$('#searchTerm').click(function(e){
//		  $(".search").addClass("active");
//		  e.stopPropagation();
//	});
	
	
//	$('.wrap').on("click", function(e) {
//	    if ($(e.target).is("#searchTerm") === false) {
//	      $(".search").removeClass("active");
//	    }
//	  });
		

	function loadListFriends(){

		$.ajax({
			 type:"GET", 
			  url: '/get/friends',
			  success: function( data ) {
				  listFriends = data;
				  var html = '';
				  data.forEach(function(element) {
					  html +='<li id="'+element.id+'"><div class="item"><img src="https://s3.amazonaws.com/uifaces/faces/twitter/brad_frost/128.jpg" /><div class="profile-content"><a href="#" class="text-bold">'+element.fullName+'</a> <a class="text-grey">'+element.befriends.length+'</a></div><div class="action "><a '+'onclick=deleteFriend("' + element.id + '")'+' class="text-grey text-bold">Unfriend</a></div></div></li>';
					
				  });
				  $('#list-friends').html(html);
			  },
			  error: function(jqXHR, textStatus, errorThrown) {
		            alert(jqXHR.status);
		        },
		   dataType: "json"
			
			});
	} 
});

function deleteFriend(id){
	$.ajax({
	    url: 'delete/friend/'+ id,
	    method: 'PUT',
	    contentType:'text',
	    success: function(result) {
	    	$('#'+id).remove();
	    },
	    error: function(request,msg,error) {
	       alert("somethings went wrong");
	    },
	    dataType: "text"
	});
}
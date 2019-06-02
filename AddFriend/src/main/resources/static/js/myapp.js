$(function(){
	
	const monthNames = ["January", "February", "March", "April", "May", "June",
		  "July", "August", "September", "October", "November", "December"
		];
		  var qntYears = 16;
		  var selectYear = $("#year");
		  var selectMonth = $("#month");
		  var selectDay = $("#day");
		  var currentYear = new Date().getFullYear();
		  
		  for (var y = 0; y < qntYears; y++){
		    let date = new Date(currentYear);
		    var yearElem = document.createElement("option");
		    yearElem.value = currentYear;
		    yearElem.textContent = currentYear;
		    selectYear.append(yearElem);
		    currentYear--;
		  } 

		  for (var m = 0; m < 12; m++){
		      let monthNum = new Date(2018, m).getMonth()
		      let month = monthNames[monthNum];
		      var monthElem = document.createElement("option");
		      monthElem.value = monthNum; 
		      monthElem.textContent = month;
		      selectMonth.append(monthElem);
		    }

		    var d = new Date();
		    var month = d.getMonth();
		    var year = d.getFullYear();
		    var day = d.getDate();

		    selectYear.val(year); 
		    selectYear.on("change", AdjustDays);  
		    selectMonth.val(month);    
		    selectMonth.on("change", AdjustDays);

		    AdjustDays();
		    selectDay.val(day)
		    
		    function AdjustDays(){
		      var year = selectYear.val();
		      var month = parseInt(selectMonth.val()) + 1;
		      selectDay.empty();
		      
		      //get the last day, so the number of days in that month
		      var days = new Date(year, month, 0).getDate(); 
		      
		      //lets create the days of that month
		      for (var d = 1; d <= days; d++){
		        var dayElem = document.createElement("option");
		        dayElem.value = d; 
		        dayElem.textContent = d;
		        selectDay.append(dayElem);
		      }
		    } 
		    
		    $('#year,#month,#day').change(function () {
		        var y = $('#year').find("option:selected").text();
		        var m = $('#month').find("option:selected").text();
		        var d = $('#day').find("option:selected").text();
		        var birthDate= d +"/" +m +"/" +y;
		        $('#birthDate').val(birthDate);
		    });
	
//	 $('#addnew').on('submit', function (e) {
//
//         $.ajax({
//           type: 'post',
//           url: '/add-new',
//           data: $('#addnew').serialize(),
//           success: function (data) {
//        	 var gender = 'Male' ;
//        	 if(data.sex){
//        		 gender = 'Female';
//        	 }
//             var html='<tr class="'+data.id+'"><th scope="row">'+data.id+'</th><td>'+data.fullName+'</td><td>'+gender+'</td><td>'+data.birthDate+'</td><td >'+data.phone+'</td><td>'+data.email+'</td><td class="icon"><i class="fa fa-times-circle" onclick="deleteFriend(\''+data.id+'\')"></i></td><td class="icon"><i class="fa fa-edit"></i></td></tr>';
//             $('#friend_table').append(html);
//           },
//        		
//         });
//         return false; // avoid to execute the actual submit of the form.
//
//       });
});
       
//	$("form").on("submit", function (e) {
//	    e.preventDefault();
//	    alert("ahihi");
//	$.ajax({
//	    url: '/send-friendrequest/'+id,
//	    method: 'POST',
//	    contentType:'text',
//	    success: function(result) {
//	    	$('#'+id).text("Cancel friend request");
//	    },
//	    error: function(request,msg,error) {
//	       alert("somethings went wrong");
//	    },
//	    dataType: "text"
//	});
//	});

//	$('#searchTerm').click(function(e){
//		  $(".search").addClass("active");
//		  e.stopPropagation();
//	});
	
	
//	$('.wrap').on("click", function(e) {
//	    if ($(e.target).is("#searchTerm") === false) {
//	      $(".search").removeClass("active");
//	    }
//	  });
		

//	function loadListFriends(){
//
//		$.ajax({
//			 type:"GET", 
//			  url: '/get/friends',
//			  success: function( data ) {
//				  listFriends = data;
//				  var html = '';
//				  data.forEach(function(element) {
//					  html +='<li id="'+element.id+'"><div class="item"><img src="https://s3.amazonaws.com/uifaces/faces/twitter/brad_frost/128.jpg" /><div class="profile-content"><a href="#" class="text-bold">'+element.fullName+'</a> <a class="text-grey">'+element.befriends.length+'</a></div><div class="action "><a '+'onclick=deleteFriend("' + element.id + '")'+' class="text-grey text-bold">Unfriend</a></div></div></li>';
//					
//				  });
//				  $('#list-friends').html(html);
//			  },
//			  error: function(jqXHR, textStatus, errorThrown) {
//		            //alert(jqXHR.status);
//		        },
//		   dataType: "json"
//			
//			});
//	} 
//});

function deleteFriend(id){
	$.ajax({
	    url: '/delete-user/'+ id,
	    method: 'DELETE',
	    success: function(result) {
	    	$('.'+id).remove();
	    },
	    error: function(request,msg,error) {
	       console.log(msg);
	    },
	    dataType: "json"
	});
}

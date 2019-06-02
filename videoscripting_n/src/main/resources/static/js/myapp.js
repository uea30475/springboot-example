$(function () {
    var vid = document.getElementById('my-video');
    
    $(window).on('resize',function(){
    	$('#video-script-sidebar').css({height:$('#my-video')[0].clientHeight})
    });
    
    tracks_info = [];
    for (var i = 0; i < mediaHref.listVttFile.length; i++) {
    	tracks_info.push({
        	src: "/videos/"+course+"/"+mediaHref.code+"/vtt/"+mediaHref.listVttFile[i],
          label: mediaHref.listVttFile[i].split('.')[0].split('-')[1].toUpperCase(),
          kind: "captions",
          mode: i==0 ? 'showing':''
        	
        });
	}
    
    player = videojs("my-video",{
    	sources:[{
      	src:"/videos/"+course+"/"+mediaHref.code+"/"+mediaHref.code+".mp4",
     		type: "video/mp4"
     	}],
      tracks: tracks_info,
      width: 400,
      height: 17,
    }, 
    	()=>player.on('loadedmetadata', function() {
        	// Fires when the browser has loaded meta data for the video.
    		$('#video-script-sidebar').css({height:$('#my-video')[0].clientHeight})
    		loadScript(player.textTracks().tracks_);
    		
        }));
        
        $('#video-script-sidebar').on('click','span',(function (e) {
           vid.currentTime = e.currentTarget.attributes['data-start'].value
        }));
        // find and move scroll bar to scripting
        var videoPlaying;
        var currentScriptId;
        vid.onplaying = function(){
        	videoPlaying = setInterval(function(){
            	 var ls = $('span[data-start]').filter(function () {
                     return  vid.currentTime >= $(this).attr('data-start') && vid.currentTime <= $(this).attr('data-end')
                 });
            	 if (ls.length >= 1 && currentScriptId!=ls[ls.length - 1].id) {
            		 currentScriptId = ls[ls.length - 1].id;
                     var element = document.getElementById(ls[ls.length - 1].id);
                    
                     $('#video-script-sidebar > span').removeClass('active');
                     $('#' + ls[ls.length - 1].id).toggleClass('active');
                     element.scrollIntoView({ behavior: "smooth", block: "center", inline: "start" })
                 }
            	if(vid.duration==vid.currentTime){
            		clearInterval(videoPlaying);
            	}
            	},100)   	
            	
        }
        vid.onpause = function(){
        	clearInterval(videoPlaying);
        	
        }
        
        vid.addEventListener('seeking', (event) => {
        	clearInterval(videoPlaying);
            var ls = $('span[data-start]').filter(function () {
                return vid.currentTime >= $(this).attr('data-end') ||vid.currentTime >= $(this).attr('data-start')
            });
            if (ls.length > 1) {
                var element = document.getElementById(ls[ls.length - 1].id);
                $('#video-script-sidebar > span').removeClass('active');
                $('#' + ls[ls.length - 1].id).toggleClass('active');
                element.scrollIntoView({ behavior: "smooth", block: "center", inline: "start" })
            }
        });
        
});



function loadScript(tracks) {
	
	if(tracks.length>0){
    var min = 0;
    var second = 0;
    var cue;
    for (let i = 0; i < tracks[0].cues.length; i++) {
        cue = tracks[0].cues[i];
        min = Math.floor(cue.startTime / 60);
        second = Math.floor(cue.startTime % 60);
        min = min >= 10 ? min : '0'+min;
        second = second >= 10 ? second : '0'+second
        var iTime = $('<small/>', { 'text': min + ":" + second });
        var spanAppend = $('<span/>', { "html": cue.text, "id": "cue" + cue.id, "data-start": cue.startTime, "data-end": cue.endTime });
        spanAppend.prepend(iTime);
        $('#video-script-sidebar').append(spanAppend);

    }
    //var tracks = player.textTracks().tracks_;
    /*for (var i = 0; i < tracks.length; i++) {
    	  var track = tracks[i];
    	  track.addEventListener('cuechange', function () {
    	        currentCue = this.activeCues;
    	        if (currentCue.length > 0) {
    	            $('#video-script-sidebar > span').removeClass('active')
    	            $('#cue' + this.activeCues[0].id).toggleClass('active');
    	            var element = document.getElementById('cue' + this.activeCues[0].id)
    	            if (element != null) {
    	                element.scrollIntoView({ behavior: "smooth", block: "center", inline: "nearest" })
    	            }
    	    
    	        }
    	    
    	    })

    	}*/

	}else{
		var notExist = $('<div/>', { 'text': 'Script not exists','id':'video-sidebar'});
		var html = '<div id="video-sidebar"><button type="button" class="btn btn-primary btn-block">Primary</button></div>'
		$('#video-script-sidebar').remove();
		$('.video').append(notExist);
	}

}


$(function () {

    var vid = document.getElementById('myvideo');

    var videoElement = document.querySelector("video");
    var textTracks = videoElement.textTracks; // one for each track element
    var textTrack = textTracks[0];
    

    
    $('#video-script-sidebar').on('load', function() {
    	loadScript(textTrack);
	})
	
    $('#video-script-sidebar > span').click(function (e) {
       
       vid.currentTime = e.target.attributes['data-start'].value
    }).children().on("click", function(e) {
        $(this).closest('span').click();
        e.stopPropagation();
    });

    vid.addEventListener('seeking', (event) => {
        var ls = $('span[data-start]').filter(function () {
            return vid.currentTime >= $(this).attr('data-end') ||vid.currentTime >= $(this).attr('data-start')
        });
        if (ls.length > 1) {
            var element = document.getElementById(ls[ls.length - 1].id);
            $('#video-script-sidebar > span').removeClass('active');
            $('#' + ls[ls.length - 1].id).toggleClass('active');
            element.scrollIntoView({ behavior: "smooth", block: "center", inline: "nearest" })
        }
    });
    
  

});



function loadScript(textTrack) {
    var min = 0;
    var second = 0;
    var cue;
    for (let i = 0; i < textTrack.cues.length; i++) {
        cue = textTrack.cues[i];
        min = Math.floor(cue.startTime / 60);
        second = Math.floor(cue.startTime % 60);
        var iTime = $('<small/>', { 'text': min + ":" + second });
        var spanAppend = $('<span/>', { "html": cue.text, "id": "cue" + cue.id, "data-start": cue.startTime, "data-end": cue.endTime });
        spanAppend.prepend(iTime);
        $('#video-script-sidebar').append(spanAppend);

    }
    textTrack.addEventListener('cuechange', function () {
        currentCue = this.activeCues;
        if (currentCue.length > 0) {
            $('#video-script-sidebar > span').removeClass('active')
            $('#cue' + this.activeCues[0].id).toggleClass('active');
            var element = document.getElementById('cue' + this.activeCues[0].id)
            if (element != null) {
                element.scrollIntoView({ behavior: "smooth", block: "center", inline: "nearest" })
            }
    
        }
    
    });

}
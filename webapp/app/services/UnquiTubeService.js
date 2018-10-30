app.service("UnquiTubeService", ["$http", "$rootScope", UnquiTubeService]);

function UnquiTubeService($http,$rootScope) {

    const self = this;

    self.getPlaylist = function (playlistType, successCallback, errorCallback) {
        $http.get($rootScope.service + "/channel/1").then( 
            function (response) {
                successCallback && successCallback(response);
            },
            function (error) {
                errorCallback && errorCallback(error);
            }
        );
        /*
        // HARDCODEADO para pruebas ********************************************************************
        return [
            new Video("Escape Room Trailer #1 (2019) | Movieclips Trailers", "https://www.youtube.com/embed/8W6yYBAUxv4", ""),
            new Video("Aquaman TV Spot - Waves (2018) | Movieclips Trailers", "https://www.youtube.com/embed/nmGmu-knybU", ""),
            new Video("The Curse of La Llorona Teaser Trailer #1 (2019) | Movieclips Trailers", "https://www.youtube.com/embed/MNp1LPm3Krs", ""),
            new Video("Aladdin Teaser Trailer #1 (2019) | Movieclips Trailers", "https://www.youtube.com/embed/TzWlGGokGeE", ""),
            new Video("Glass Trailer #2 (2019) | Movieclips Trailers", "https://www.youtube.com/embed/oAALE9m47dc", ""),
            new Video("Jonathan Trailer #1 (2018) | Movieclips Trailers", "https://www.youtube.com/embed/V17pEDh6VYw", ""),
            new Video("The Best of Chucky Mashup | Movieclips", "https://www.youtube.com/embed/QQCv4rxlHVg", ""), 
            new Video("In Theaters Now: Halloween | Weekend Ticket", "https://www.youtube.com/embed/9gWsyEkKO1A", ""), 
            new Video("Jamie Lee Curtis on The Legacy of Halloween’s Laurie Strode - Part 2", "https://www.youtube.com/embed/dvbePi1o_Q0", ""), 
            new Video("Five Favorite Horror Films: Jason Blum | Rotten Tomatoes", "https://www.youtube.com/embed/v6p6ftyb_nM", ""), 
            new Video("Horror Stars Reveal Their Scariest Movies of All Time | Rotten Tomatoes", "https://www.youtube.com/embed/lLMiu_xWS70", ""), 
            new Video("Brenton Thwaites on New 'Titans' Robin and Breaking Up With Batman | Rotten Tomatoes", "https://www.youtube.com/embed/xKWY3umXJZU", ""),
            new Video("Halloween Trailer #1 (2018) | Movieclips Trailers", "https://www.youtube.com/embed/aMCLVSlk1Tk", ""), 
            new Video("The Hate U Give Trailer #1 (2018) | Movieclips Trailers", "https://www.youtube.com/embed/1nl9If0lAtY", ""), 
            new Video("Wildlife Teaser Trailer #1 (2018) | Movieclips Trailers", "https://www.youtube.com/embed/QQDL4stQCqo", ""), 
            new Video("Mid90s Trailer #1 (2018) | Movieclips Trailers", "https://www.youtube.com/embed/zXzSE0uqmjI", ""), 
            new Video("Can You Ever Forgive Me? Trailer #1 (2018) | Movieclips Trailers", "https://www.youtube.com/embed/OVZ1u36nrns", ""), 
            new Video("What They Had Trailer #1 (2018) | Movieclips Trailers", "https://www.youtube.com/embed/AoEsF02gnUQ", ""),
        ];*/
    };

    self.saveVideo = function (videoToSave, successCallback, errorCallback) {
        $http.post($rootScope.service + "/channel/1/video", videoToSave).then( 
            function (response) {
                successCallback && successCallback(response);
            },
            function (error) {
                errorCallback && errorCallback(error);
            }
        );
    };

}

function Video(title, url, description) {
    this.title = title;
    this.url = url;
    this.description = description;
}

// algo para sacar links desde la pagina de youtube
/*
var elems = $0.getElementsByTagName("ytd-grid-video-renderer");
for (var i=0; i<elems.length; i++) {
    var e = elems[i].getElementsByTagName("h3")[0].getElementsByTagName("a")[0];
    var title = e.text;
    var url = e.href;
    console.log(`new Video("${title}", "${url}", ""),`)
}
*/
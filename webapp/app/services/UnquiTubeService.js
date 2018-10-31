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
"use strict";

const PATH = "/player";

app
.config(function($routeProvider) {
    $routeProvider
        .when(PATH, {
            templateUrl : "./app/views/player/player.html",
            controller: ["$scope", "$log", "UnquiTubeService", PlayerCtrl],
            controllerAs: "playerCtrl",
            aCustomTitle: "Player"
        }
    );
});

/**
 * Controller de la página player
 */
function PlayerCtrl($scope, $log, UnquiTubeService) {

    const self = this;

    self.playlist = UnquiTubeService.getPlaylist("sarasa");

    self.playing = self.playlist[0];

    // const iframe = document.getElementById("player-video-frame").getElementsByTagName("iframe")[0];
    // iframe.src = self.playing.url;

}
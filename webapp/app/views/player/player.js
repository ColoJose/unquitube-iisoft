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

    self.saveVideo = function () {
        UnquiTubeService.saveVideo(self.newVideo, 
            function success(response) {
                $('#add-video-modal').modal("hide");
                $("#player-video-successfully-saved-toast").alert();
                console.log("Guardado de video exitoso");
            },
            function error(error) {
                $('#add-video-modal').modal("hide");
                window.alert("Sucedio un error al intentar guardar el video");
            }
        );
    };

    // self.addNewVideo = function () {
    //     let options = {};
    //     $('#add-video-modal').modal(options);
    // }

    $('#add-video-modal').on("show.bs.modal", function (event) {
        self.newVideo = {};
    });

    $('#add-video-modal').on("hide.bs.modal", function (event) {
        self.newVideo = null;
    });

}
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

    self.channel = null;

    UnquiTubeService.getPlaylist("sarasa", 
        function(response) {
            updatePlaylist(response.data);
            self.playing = self.playlist[0];
        },
        function(error) {
            window.alert("Sucedio un error al intentar obtener el canal");
            console.error(error);
        }
    );

    function updatePlaylist(channel) {
        self.channel = channel;
        self.playlist = self.channel.playlist; 
    }


    // const iframe = document.getElementById("player-video-frame").getElementsByTagName("iframe")[0];
    // iframe.src = self.playing.url;

    self.saveVideo = function () {
        UnquiTubeService.saveVideo(self.newVideo, 
            function success(response) {
                updatePlaylist(response.data);
                $('#add-video-modal').modal("hide");
                $("#player-video-successfully-saved-toast").alert();
                console.log("Guardado de video exitoso");
            },
            function error(error) {
                $('#add-video-modal').modal("hide");
                window.alert("Sucedio un error al intentar guardar el video");
                console.error(error);
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
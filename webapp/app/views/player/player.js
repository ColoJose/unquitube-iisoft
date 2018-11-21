"use strict";

const PATH = "/player/:idChannel";

app
.config(function($routeProvider) {
    $routeProvider
        .when(PATH, {
            templateUrl : "./app/views/player/player.html",
            controller: ["$scope", "$log", "UnquiTubeService", "$routeParams", PlayerCtrl],
            controllerAs: "playerCtrl",
            aCustomTitle: "Player"
        }
    );
});

/**
 * Controller de la página player
 */
function PlayerCtrl($scope, $log, UnquiTubeService, $routeParams) {

    const self = this;

    self.channel = null;
    self.channelCopy = null;

    self.urlRegex = /(http|https):\/\/(www.youtube.com\/watch\?v=|youtu.be\/)(\w+$)/;
    //Tipos de URLs validas:
    //https://www.youtube.com/watch?v=axkOqrLtD
    //https://youtu.be/axkOqrLtDXo


    UnquiTubeService.getPlaylist($routeParams.idChannel,
        function(response) {
            updatePlaylist(response.data);
            self.playing = self.playlist[0];
            self.playingurl = "https://www.youtube.com/embed/".concat(self.playing.url);
        },
        function(error) {
            window.alert("Sucedio un error al intentar obtener el canal");
            console.error(error);
        }
    );

    function updatePlaylist(channel) {
        self.channel = channel;
        self.channel.playlist.sort( (a,b) => a.id - b.id);
        self.playlist = self.channel.playlist;
    }

    //----- MODIFICAR CANAL ------//
    self.initChannelChange = function() {
        self.channelCopy = {... self.channel};
    }

    self.updateChannel = function(){
        UnquiTubeService.updateChannel(self.channelCopy);
        window.location = window.location.origin;
    }

    //---- ELIMINAR CANAL ------//

    self.deleteChannel = function() {
        UnquiTubeService.deleteChannel(self.channel.id);
        window.location = window.location.origin;
    }


    // const iframe = document.getElementById("player-video-frame").getElementsByTagName("iframe")[0];
    // iframe.src = self.playing.url;
    self.sendingNewVideo = false;
    self.saveVideo = function () {
        self.sendingNewVideo = true;
        self.newVideo.url = self.newVideo.url.split("/").pop().split("=").pop();
        UnquiTubeService.saveVideo($routeParams.idChannel, self.newVideo,
            function success(response) {
                $('#add-video-modal').modal("hide");
                updatePlaylist(response.data);

                $('#player-video-successfully-saved-toast').show()
                setTimeout(function () {
                    $('#player-video-successfully-saved-toast').hide()
                }, 5000);
                self.sendingNewVideo = false;
                console.log("Guardado de video exitoso");
            },
            function error(error) {
                $('#add-video-modal').modal("hide");
                window.alert("Sucedio un error al intentar guardar el video");
                self.sendingNewVideo = false;
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
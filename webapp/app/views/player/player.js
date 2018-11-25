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
    
    UnquiTubeService.addViewer($routeParams.idChannel, 
        function(response) {
            self.viewersCount = response.data.views;
            $scope.$root.tasksOnClose.removeViewerOnTabClosed = function () {
                UnquiTubeService.delViewer(self.channel.id, 
                    function (response) {},
                    function (error) {}
                );
            };
        }, 
        function(error) {
            alert("No se pudo acceder a la cantidad de usuarios")
        }
    );

    $scope.$on("$destroy", function(event) {
        $scope.$apply( () => UnquiTubeService.delViewer(self.channel.id, null, null) );
        delete $scope.$root.tasksOnClose.removeViewerOnTabClosed;
    });

    self.channel = null;
    self.channelCopy = null;
    // self.youtubeApiObjectLoaded = false;

    self.urlRegex = /(http|https):\/\/(www.youtube.com\/watch\?v=|youtu.be\/)([\-_a-zA-Z0-9]+$)/;
    //Tipos de URLs validas:
    //https://www.youtube.com/watch?v=axkOqrLtD
    //https://youtu.be/axkOqrLtDXo


    UnquiTubeService.getPlaylist($routeParams.idChannel,
        function(response) {
            updatePlaylist(response.data);
            if (!state.initialized)
                self.startPlaylist();
        },
        function(error) {
            window.alert("Sucedio un error al intentar obtener el canal");
            console.error(error);
        }
    );

    function updatePlaylist(channel) {
        self.channel = channel;
        self.channel.playlist.sort( (a,b) => a.id - b.id);
        self.setPlaylist(self.channel.playlist);
    }
    

    //----- MODIFICAR CANAL ------//
    self.initChannelChange = function() {
        self.channelCopy = {... self.channel};
    }

    self.updateChannel = function(){
        if (typeof self.channelCopy.tags === "string") {
            self.channelCopy.tags = self.channelCopy.tags.replace(/ /g,"").split(",");
        }
        UnquiTubeService.updateChannel(self.channelCopy,
            function(){
                window.location = window.location.origin;
            },
            function(error){
                window.alert("Ha habido un problema y no se a podido actualizar el canal");
                console.log(error);
            }
        );
    };

    //---- ELIMINAR CANAL ------//

    self.deleteChannel = function() {
        UnquiTubeService.deleteChannel(self.channel.id, 
            function(){
                window.location = window.location.origin;
            }, 
            function(error){
                window.alert("Ha habido un problema y no se a podido borrar el canal");
                console.log(error);
            }
        );
        
    };

    self.sendingNewVideo = false;
    self.saveVideo = function () {
        self.sendingNewVideo = true;
        self.newVideo.url = self.newVideo.url.split("/").pop().split("=").pop();
        UnquiTubeService.saveVideo($routeParams.idChannel, self.newVideo,
            function success(response) {
                $('#add-video-modal').modal("hide");
                updatePlaylist(response.data);
                if (state.loadedPlaylist && !state.initialized)
                    self.startPlaylist();

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

    // *************************
    // ** PLAYLIST MANAGEMENT **
    // *************************

    /** true solo cuando llega el playlist y el objeto self.player != null */
    let state = {
        loadedPlaylist: false,
        initialized: false,
    }
    /** objeto de la api de Youtube. Solo debería inicializarse una sola vez */
    let player = null;
    /** index del video en la playlist que se esta reproduciendo actualmente */
    let currentVideoIndex = 0;
    /** lista de videos de este canal. IMPORTANTE: solo debe actualizarce esta lista mediante self.playlist(newPlaylist) */
    self.playlist = [];
    /** video reproduciendose actualmente */
    self.currentVideo = null;
        
    self.setPlaylist = function (newPlaylist) {
        // esto solo debe ejecutarse la primera ves que encuentra una lista no vacia
        if (self.playlist.length == 0 && !state.loadedPlaylist)
            state.loadedPlaylist = true;

        self.playlist = newPlaylist;
    };

    self.startPlaylist = function () {
        if (!state.loadedPlaylist || self.playlist.length == 0) {
            console.log("La lista de reproduccion no esta lista para iniciarse"); 
            return;
        }

        self.player = new YT.Player("embeded-youtube-iframe", {
            videoId: self.playlist[0].url,
            playerVars: {
                rel: 0,
                modestbranding: 1
            },
            events: {
                onReady: (event) => {
                    $scope.$apply(()=>{
                        nextVideo();
                        state.initialized = true;
                    });
                    
                },
                onStateChange: (event) => {
                    $scope.$apply(()=>{
                        switch (event.data) {
                            case (YT.PlayerState.ENDED):
                                nextVideo();
                                break;
                        }
                    });
                }
            }
        });

        function nextVideo() {
            self.currentVideo = self.playlist[ currentVideoIndex % self.playlist.length ];
            currentVideoIndex++;
            console.log("PLAYER -> loading video with id ", self.currentVideo);
            self.player.loadVideoById({ videoId: self.currentVideo.url });
        }

    };


}
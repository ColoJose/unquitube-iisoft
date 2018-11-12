"use strict";

app.config(function($routeProvider) {

    $routeProvider
        .when("/", {
            templateUrl : "./app/views/home/home.html",
            controller: ["$scope", "$log", "UnquiTubeService", HomeCtrl],
            controllerAs: "homeCtrl",
            aCustomTitle: "Home"
        }
    );

});

/**
 * Controller de la página home
 */
function HomeCtrl($scope, $log, UnquiTubeService) {

    const self = this;

    self.chanelsList = null;

    //self.channelsList = UnquiTubeService.getChannelListHARD();

    self.channelsList = UnquiTubeService.getChannelsList(
        function(response) {
            updateChannelslist(response.data);
        },
        function(error) {
            window.alert("Sucedio un error al intentar obtener los canales");
            console.error(error);
        });

    function updateChannelslist(channels) {
        self.channelsList = channels;

        //self.channel.playlist.sort( (a,b) => a.id - b.id);
        //self.playlist = self.channel.playlist;
    }

}
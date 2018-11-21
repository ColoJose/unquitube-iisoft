"use strict";

app.config(function($routeProvider) {

    $routeProvider
        .when("/", {
            templateUrl : "./app/views/home/home.html",
            controller: ["$scope", "$log", "UnquiTubeService", "$routeParams", HomeCtrl],
            controllerAs: "homeCtrl",
            aCustomTitle: "Home"
        }
    );

    $routeProvider
        .when("/search/:tags", {
                templateUrl : "./app/views/home/home.html",
                controller: ["$scope", "$log", "UnquiTubeService", "$routeParams", HomeCtrl],
                controllerAs: "homeCtrl",
                aCustomTitle: "Home"
            }
        );

});

/**
 * Controller de la página home
 */
function HomeCtrl($scope, $log, UnquiTubeService, $routeParams) {

    const self = this;

    self.chanelsList = null;

    //self.channelsList = UnquiTubeService.getChannelListHARD();

    if ($routeParams.tags == undefined){

        self.channelsList = UnquiTubeService.getChannelsList(
            function(response) {
                updateChannelslist(response.data);
            },
            function(error) {
                window.alert("Sucedio un error al intentar obtener los canales");
                console.error(error);
            });

    } else {
        self.tagsBuscados = $routeParams.tags.replace(/,/g,"!");

        UnquiTubeService.getChannelsListByTag(
            self.tagsBuscados,
            function(response) {
                updateChannelslist(response.data);
            },
            function(error) {
                window.alert("Sucedio un error al intentar obtener los canales");
                console.error(error);
            });
    }

    function updateChannelslist(channels) {
        self.channelsList = channels;

        //self.channel.playlist.sort( (a,b) => a.id - b.id);
        //self.playlist = self.channel.playlist;
    }

}
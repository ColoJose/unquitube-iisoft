"use strict";

app.config(function($routeProvider) {

    $routeProvider
        .when("/", {
            templateUrl : "./app/views/home/home.html",
            controller: ["$scope", "$log","UnquiTubeService", HomeCtrl],
            controllerAs: "homeCtrl"
        }
    );

});

/**
 * Controller de la página home
 */
function HomeCtrl($scope, $log,UnquiTubeService) {

    const self = this;
    self.channels = null;

    UnquiTubeService.getAllChannels(
        function(response) {
            self.playlist = response.data;
        },
        function(error) {
            window.alert("Ha sucedido un error al obtener los canales");
            console.error(error);
        } 

    );        
}





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

    self.channelsList = UnquiTubeService.getChannelList();

}
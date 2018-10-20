"use strict";

app.config(function($routeProvider) {

    $routeProvider
        .when("/player", {
            templateUrl : "./app/views/player/player.html",
            controller: ["$scope", "$log", PlayerCtrl],
            controllerAs: "playerCtrl"
        }
    );

});

/**
 * Controller de la página player
 */
function PlayerCtrl($scope, $log) {

    $scope.title = "Player";

}
"use strict";

app.config(function($routeProvider) {

    $routeProvider
        .when("/", {
            templateUrl : "./app/views/home/home.html",
            controller: ["$scope", "$log", HomeCtrl],
            controllerAs: "homeCtrl"
        }
    );

});

/**
 * Controller de la página home
 */
function HomeCtrl($scope, $log) {

    

}
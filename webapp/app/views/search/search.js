"use strict";

app.config(function($routeProvider) {

    $routeProvider
        .when("/search/:tags", {
                templateUrl : "./app/views/search/search.html",
                controller: ["$scope", "$log", "UnquiTubeService", "$routeParams", searchCtrl],
                controllerAs: "searchCtrl",
                aCustomTitle: "Search"
            }
        );

});

/**
 * Controller de la página home
 */
function searchCtrl($scope, $log, UnquiTubeService, $routeParams) {

    const self = this;

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

    function updateChannelslist(channels) {
        self.channelsList = channels;
    }

}
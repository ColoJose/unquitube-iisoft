"use strict";

app.config(function($routeProvider) {

    $routeProvider
        .when("/", {
            templateUrl : "./app/views/home/home.html",
            controller: ["$scope", "$log", "UnquiTubeService", "$routeParams", "$interval", HomeCtrl],
            controllerAs: "homeCtrl",
            aCustomTitle: "Home"
        }
    );

    $routeProvider
        .when("/search/:tags", {
                templateUrl : "./app/views/home/home.html",
                controller: ["$scope", "$log", "UnquiTubeService", "$routeParams", "$interval", HomeCtrl],
                controllerAs: "homeCtrl",
                aCustomTitle: "Home"
            }
        );

});

/**
 * Controller de la página home
 */
function HomeCtrl($scope, $log, UnquiTubeService, $routeParams, $interval) {

    const self = this;

    //self.channelsList = UnquiTubeService.getChannelListHARD();

    getChannelListCorrespondiente(updateChannelslist, function(){window.alert("Sucedio un error al intentar obtener los canales")});


    function getChannelListCorrespondiente(succesCall, errorCall){
        if ($routeParams.tags == undefined){

            UnquiTubeService.getChannelsList(
                function(response) {
                    succesCall(response.data);
                },
                function(error) {
                    errorCall(error);
                    console.error(error);
                });

        } else {
            self.tagsBuscados = $routeParams.tags.replace(/,/g,"!");

            UnquiTubeService.getChannelsListByTag(
                self.tagsBuscados,
                function(response) {
                    succesCall(response.data);
                },
                function(error) {
                    errorCall(error);
                    console.error(error);
                });
        }
    }

    //Refresecar cantidad de usuarios

    $scope.intervalPromise = null;

    $scope.intervalPromise = $interval(function() {getChannelListCorrespondiente(actualizarViewersCanales,function () {window.alert("Sucedio un error al intentar actualizar las views")})}, 5000);

    $scope.$on('$destroy',function(){
        if(angular.isDefined($scope.intervalPromise)) {
            $interval.cancel($scope.intervalPromise);
        }
    });

    //Refrescar cantidad usuarios
    

    function actualizarViewersCanales(canalesActualizados) {
        self.channelsList.forEach(function (ch) {
           let maybeCanalActualizadas = getCanalConId(ch.id, canalesActualizados);
            if (maybeCanalActualizadas.length > 0) {
                ch.views = maybeCanalActualizadas[0].views;
            }
        });
    }

    //Busca en la listaCanalesFiltrar un canal con el id buscado; si lo encuentra retorna una lista con el canal; si no lo encuentra retorna una lista vacia
    function getCanalConId(id, listaCanalesFiltrar) {
        let ret = [];
        let indice  = 0;

        while (indice < listaCanalesFiltrar.length && ret.length === 0){
            if(listaCanalesFiltrar[indice].id === id){
                ret[0] = listaCanalesFiltrar[indice];
            }
            indice++;
        }

        return ret;
    }



    function updateChannelslist(channels) {
        self.channelsList = channels;

        //self.channel.playlist.sort( (a,b) => a.id - b.id);
        //self.playlist = self.channel.playlist;
    }

}
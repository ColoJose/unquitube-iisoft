"use strict";

const app = angular.module("unquiTube", ["ngRoute"]);

app.controller("unquiTubeCtrl", [
    "$http",
    "$scope", 
    "$log",
    "$route",
    UnquiTubeCtrl
]);
// prueba (esto se va)
// app.config(function($sce) {
//     $sce.trustAsResourceUrl("https://www.youtube.com/**");
// });

app.config( ['$sceDelegateProvider', function($sceDelegateProvider)
    {
        $sceDelegateProvider.resourceUrlWhitelist(['self','https://www.youtube.com/**']); 
    }
]);

/**
 * Controller principal de la página
 */
function UnquiTubeCtrl($http, $scope, $log, $route) {

    $scope.$root.service = "http://localhost:9096";

    $scope.redireccionABusqueda = function () {
        window.location = window.location.origin + "#!/search/" + $scope.searchTags.replace(/ /g,"")
    };

    /**
     * Actualiza el nombre de la página mostrado por el browser en la barra de título
     */
    function setPageTitle(aTitle) {

        let titleElement = document.getElementsByTagName("head")[0].getElementsByTagName("title")[0];
        titleElement.text = aTitle;
    }

    $('#add-channel-modal').on("show.bs.modal");

    $('#add-channel-modal').on("hide.bs.modal");

    $scope.newChannel = {
        "id": 0,
        "name": "",
        "playlist": [],
        "tags": []
    };

    $scope.tagsForNewChannel = "";

    $scope.efectivoSaveChannel = function() {
        $scope.newChannel.tags = $scope.tagsForNewChannel.replace(/ /g,"").split(",");

        $scope.saveChannel($scope.newChannel,
            function(response) {
                $scope.newChannel.name = "";
                $scope.newChannel.tags = [];
                $scope.tagsForNewChannel = "";
                //document.getElementById('myModal').style.display = "none";
                //$('#add-video-modal').modal("hide");
                $route.reload();
                window.alert("Se guardo el canal de forma correcta");
            },
            function(error) {
                window.alert("Sucedio un error al intentar guardar el canal");
                console.error(error);
            });
    };

    $scope.saveChannel = function (channelToSave, successCallback, errorCallback) {
        $http.post($scope.service + "/channel/", channelToSave).then(
            function (response) {
                successCallback && successCallback(response);
            },
            function (error) {
                errorCallback && errorCallback(error);
            }
        );
    };

    /**
     * Event listener para eschuchar cuando el evento $routeChangeSuccess se dispara.
     * Cuando ngRoute rutea a una nueva página, cambiar el nombre del título mostrado por el browser 
     * */
    $scope.$root.$on("$routeChangeSuccess", (angularEvent, current, previous)=>{
        let mainTitle = "UnquiTube";

        if (current.$$route.aCustomTitle) { // NOTA: "aCustomTitle" no es de angular. Lo meto para obtener un título desde la página ruteada
            setPageTitle(mainTitle + " - " + current.$$route.aCustomTitle)
            return;
        }
        setPageTitle(mainTitle)
    });

}



window.scp = function ($0) {
    window.scpp = angular.element($0).scope();
    return scpp;
}

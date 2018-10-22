"use strict";

const app = angular.module("unquiTube", ["ngRoute"]);

app.controller("unquiTubeCtrl", [
    "$scope", 
    "$log",
    UnquiTubeCtrl
]);

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
function UnquiTubeCtrl($scope, $log) {

    /**
     * Actualiza el nombre de la página mostrado por el browser en la barra de título
     */
    function setPageTitle(aTitle) {
        let titleElement = document.getElementsByTagName("head")[0].getElementsByTagName("title")[0];
        titleElement.text = aTitle;
    }

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
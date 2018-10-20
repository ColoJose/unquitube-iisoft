"use strict";

const app = angular.module("unquiTube", ["ngRoute"]);

app.controller("unquiTubeCtrl", [
    "$scope", 
    "$log",
    UnquiTubeCtrl
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
        let pageName = null;

        switch (current.originalPath) {
            case "/player":
                pageName = "Player";
                break;
        }
        if (pageName) {
            setPageTitle(mainTitle + " - " + pageName)
            return;
        }
        setPageTitle(mainTitle)
    });

}
/**
 * Created by Erik Macej on 16.12.16.
 */


portalControllers.controller('ForestsCtrl',
    function ($scope, $http, $rootScope) {

        loadForests($http, $scope);

        $scope.findAllForests = function () {
            loadForests($http,$scope);
        };

        $scope.findForestByName = function (name) {
            findForestByName(name, $scope, $http);
        };

    });

/**
 * Created by Erik Macej on 16.12.16.
 */

portalControllers.controller('ForestsCtrl',
    function ($scope, $http, $rootScope, ForestRestService, Utils) {

        ForestRestService.setAll($scope);

        $scope.deleteForest = function (forest) {
            $http.delete('rest/forest/' + forest.id ).then(
                function success(response) {
                    $rootScope.successAlert = 'Deleted forest "' + forest.name + '"';
                    loadForests($http, $scope);
                },
                function error(response) {
                    switch (response.status) {
                        case 409:
                            Utils.showError();
                            $rootScope.errorAlert = 'You can not delete this forest because visit have reference to forest';
                            break;
                        default:
                            if (response.data.errors) {
                                angular.forEach(response.data.errors, function (value) {
                                    $rootScope.errorAlert = 'Could not delete forest';
                                });
                            }
                            break;
                    }
                }
            );
        };


        $scope.resetForm = function(){
            $scope.name = null;
            $scope.type = null;
            $scope.date = null;
            $scope.fromDate = null;
            $scope.toDate = null;

            loadForests($http,$scope);
        };

        $scope.findForestByName = function (name) {
            findForestByName(name, $scope, $http);
        };

    });

portalControllers.controller('CreateForestCtrl',
    function ($scope, $http, $rootScope, $location) {

        $scope.forest = {
            'id': null,
            'name': '',
            'localityDescription': ''
        };

        $scope.createForest = function (forest) {
            $http({
                method: 'POST',
                url: 'rest/forest/create',
                data: forest
            }).then(function success(response) {
                    $rootScope.successAlert = 'Created "' + forest.name + '"';
                    $location.path("/forests");
                },
                function error(response) {
                    switch (response.status) {
                        case 409:
                            $rootScope.errorAlert = 'Forest with this name already exists';
                            break;
                        default:
                            if (response.data.errors) {
                                angular.forEach(response.data.errors, function (value) {
                                    $rootScope.errorAlert = 'Could not create forest';
                                });
                            }
                            break;
                    }
                }
            )
        }

    });

portalControllers.controller('UpdateForestCtrl',
    function ($scope , $routeParams, $http, $rootScope, $location) {

        var forestId = $routeParams.forestId;

        findForestById(forestId, $scope, $http);

        $scope.updateForest = function (forest) {
            $http({
                method: 'POST',
                url: 'rest/forest/update',
                data: forest
            }).then(function success(response) {
                    $rootScope.successAlert = 'Updated "' + forest.name + '"';
                    $location.path("/forests");
                },
                function error(response) {
                    switch (response.status) {
                        case 409:
                            $rootScope.errorAlert = 'Forest with this name already exists';
                            break;
                        default:
                            if (response.data.errors) {
                                angular.forEach(response.data.errors, function (value) {
                                    $rootScope.errorAlert = 'Could not update forest';
                                });
                            }
                            break;
                    }
                }
            )
        }

    });

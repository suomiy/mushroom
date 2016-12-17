/**
 * Created by Erik Macej on 16.12.16.
 */

portalControllers.controller('ForestsCtrl',
    function ($scope, $http, $rootScope) {

        loadForests($http, $scope);

        $scope.deleteForest = function (forest) {
            console.log("deleting forest with id=" + forest.id + ' (' + forest.name + ')');
            $http.delete('rest/forest/' + forest.id ).then(
                function success(response) {
                    console.log('deleted forest ' + forest.id + ' (' + forest.name + ')  on server');
                    $rootScope.successAlert = 'Deleted forest "' + forest.name + '"';
                    loadForests($http, $scope);
                },
                function error(response) {
                    console.log("error when deleting forest");
                    console.log(response);
                    switch (response.status) {
                        case 409:
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

        $scope.findAllForests = function () {
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
            console.log("Creating forest with name=" + forest.name);
            $http({
                method: 'POST',
                url: 'rest/forest/create',
                data: forest
            }).then(function success(response) {
                    console.log('Created forest ' + forest.name + '  on server');
                    $rootScope.successAlert = 'Created "' + forest.name + '"';
                    $location.path("/forests");
                },
                function error(response) {
                    console.log("Error when creating forest");
                    console.log(response);
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
            console.log("Updating forest with id=" + forest.id);
            $http({
                method: 'POST',
                url: 'rest/forest/update',
                data: forest
            }).then(function success(response) {
                    console.log('Updated forest ' + forest.id + '  on server');
                    $rootScope.successAlert = 'Updated "' + forest.name + '"';
                    $location.path("/forests");
                },
                function error(response) {
                    console.log("Error when updating forest");
                    console.log(response);
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

/**
 * Created by Erik Macej on 16.12.16.
 */

portalControllers.controller('AdminForestsCtrl',
    function ($scope, $http, $rootScope) {

        loadForests($http, $scope);

        $scope.deleteForest = function (forest) {
            console.log("deleting forest with id=" + forest.id + ' (' + forest.name + ')');
            $http.delete('rest/forest/' + forest.id ).then(
                function success(response) {
                    console.log('deleted forest ' + forest.id + ' (' + forest.name + ')  on server');
                    $rootScope.successAlert = 'Deleted forest "' + forest.name + '"';
                    loadAdminForests($http, $scope);
                },
                function error(response) {
                    console.log("error when deleting hunter");
                    console.log(response);
                    switch (response.data.code) {
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot delete forest - ' + value;
                            });
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

portalControllers.controller('AdminCreateForestCtrl',
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
                    $location.path("/admin/forests");
                },
                function error(response) {
                    console.log("error when creating forest");
                    console.log(response);
                    switch (response.data.code) {
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot create forest - ' + value;
                            });
                            break;
                    }
                }
            )
        }

    });

portalControllers.controller('AdminUpdateForestCtrl',
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
                    $location.path("/admin/forests");
                },
                function error(response) {
                    console.log("error when updating forest");
                    console.log(response);
                    switch (response.data.code) {
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot update forest - ' + value;
                            });
                            break;
                    }
                }
            )
        }

    });

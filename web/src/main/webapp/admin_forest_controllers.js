/**
 * Created by kisty on 16.12.16.
 */

function loadAdminForests($http, $scope) {
    $http.get('/pa165/rest/forest/findall').then(function (response) {
        $scope.forests = response.data;
        console.log('All forests loaded');
    });
};

function findForestByName($name, $scope, $http) {
    var forest;

    $http.get('/pa165/rest/forest/find?name=' + $name).then(function (response) {
        forest = response.data;

        if(response.data) {
            $scope.forests = [forest];
            console.log('Forest with name' + forest.name + 'loaded');
        }else{
            $scope.forests = [];
            console.log('Forest with name doesn t exists');
        }

    });
};

function findForestById($forestId, $scope, $http) {
    $http.get('/pa165/rest/forest/' + $forestId).then(function (response) {
        $scope.forest = response.data;
        console.log('Forest with name' + $scope.forest.name + 'loaded');
    })

};

portalControllers.controller('AdminForestsCtrl',
    function ($scope, $http, $rootScope) {

        loadAdminForests($http, $scope);

        $scope.deleteForest = function (forest) {
            console.log("deleting forest with id=" + forest.id + ' (' + forest.name + ')');
            $http.delete('/pa165/rest/forest/' + forest.id ).then(
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
            loadAdminForests($http,$scope);
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
                url: '/pa165/rest/forest/create',
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
                url: '/pa165/rest/forest/update',
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
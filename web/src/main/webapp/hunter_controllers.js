/**
 * Created by Erik Macej on 14.12.16.
 */

portalControllers.controller('HuntersCtrl',
    function ($scope, $rootScope, $http ) {

        loadHunters($http, $scope);

        $scope.deleteHunter = function (hunter) {
            console.log("deleting hunter with id=" + hunter.id + ' (' + hunter.email + ')');
            $http.delete('rest/hunter/' + hunter.id ).then(
                function success(response) {
                    console.log('deleted hunter ' + hunter.id + ' (' + hunter.email + ')  on server');
                    $rootScope.successAlert = 'Deleted hunter "' + hunter.email + '"';
                    loadHunters($http, $scope);
                },
                function error(response) {
                    console.log("error when deleting hunter");
                    console.log(response);
                    switch (response.data.code) {
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot delete hunter - ' + value;
                            });
                            break;
                    }
                }
            );
        };

        $scope.findAllHunters = function () {
            loadHunters($http,$scope);
        };

        $scope.findHunterByEmail = function (email) {
            findHunterByEmail(email, $scope, $http);
        };

    });

portalControllers.controller('HunterUpdateCtrl',
    function ($scope , $routeParams, $http, $rootScope, $location) {
        
        var hunterId = $routeParams.hunterId;
        $scope.showRanksAndRoles = true;
        $scope.ranks = ranks;
        $scope.types = roles;

        findHunterById(hunterId, $scope, $http);

        $scope.updateHunter = function (hunter) {
            console.log("updating hunter with id=" + hunter.id + ' (' + hunter.email + ')');
            $http({
                method: 'POST',
                url: 'rest/hunter/update',
                data: hunter
            }).then(function success(response) {
                    console.log('updated hunter ' + hunter.id + ' (' + hunter.email + ')  on server');
                    $rootScope.successAlert = 'Updated hunter "' + hunter.email + '"';
                    $location.path("/hunters");
                },
                function error(response) {
                    console.log("error when updating hunter");
                    console.log(response);
                    switch (response.data.code) {
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot update hunter - ' + value;
                            });
                        break;
                    }
                }
            )
        }

    });

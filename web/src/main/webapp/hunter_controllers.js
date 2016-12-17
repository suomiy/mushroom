/**
 * Created by Erik Macej on 14.12.16.
 */

portalControllers.controller('HuntersCtrl',
    function ($scope, $rootScope, $http, $location) {

        loadHunters($http, $scope);

        $scope.deleteHunter = function (hunter) {
            console.log("deleting hunter with id=" + hunter.id + ' (' + hunter.email + ')');
            $http.delete('rest/hunter/' + hunter.id).then(
                function success(response) {
                    if ($rootScope.user.isDeleted(hunter)) {
                        $rootScope.user.logout();
                        $location.path('/login');
                        $rootScope.successAlert = 'Your Account was deleted. Have a nice day.';
                    } else {
                        $rootScope.successAlert = 'Deleted hunter "' + hunter.email + '"';
                        loadHunters($http, $scope);
                    }
                },
                function error(response) {
                    console.log("error when deleting hunter");
                    console.log(response);
                    switch (response.data.code) {
                        default:
                            angular.forEach(response.data.errors, function (value) {
                                $rootScope.errorAlert = 'Cannot delete hunter - ' + value;
                            });
                            break;
                    }
                }
            );
        };

        $scope.findAllHunters = function () {
            loadHunters($http, $scope);
        };

        $scope.findHunterByEmail = function (email) {
            findHunterByEmail(email, $scope, $http);
        };

    });

portalControllers.controller('HunterUpdateCtrl',
    function ($scope, $routeParams, $http, $rootScope, $location) {

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
                    if ($rootScope.user.isEmailOfThisUserAltered(hunter)) {
                        $rootScope.user.logout();
                        $location.path('/login');
                        $rootScope.successAlert = 'Your Email was updated. Please login again.';
                    } else {
                        $rootScope.successAlert = 'Updated hunter "' + hunter.email + '"';
                        $location.path("/hunters");
                    }
                },
                function error(response) {
                    console.log("error when updating hunter");
                    console.log(response);
                    switch (response.status) {
                        case 409:
                            $rootScope.errorAlert = 'Account with this nick or email already exists';
                            break;
                        default:
                            if (response.data.errors) {
                                angular.forEach(response.data.errors, function (value) {
                                    $rootScope.errorAlert = 'Could not update hunter, please try latter';
                                });
                            }
                            break;
                    }
                }
            )
        }

    });

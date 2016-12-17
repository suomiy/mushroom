/**
 * Created by kisty on 16.12.16.
 */


portalControllers.controller('YourProfileCtrl', function ($rootScope, $scope, $http) {
    findHunterById($rootScope.user.id, $scope, $http);
});

portalControllers.controller('YourProfileUpdateCtrl',
    function ($scope, $routeParams, $http, $rootScope, $location) {

        findHunterById($rootScope.user.id, $scope, $http);
        $scope.showRanksAndRoles = false;

        $scope.updateHunter = function (hunter) {
            console.log("Updating hunter with id=" + hunter.id + ' (' + hunter.email + ')');
            $http({
                method: 'POST',
                url: '/rest/hunter/update',
                data: hunter
            }).then(function success(response) {
                    if ($rootScope.user.isEmailOfThisUserAltered(hunter)) {
                        $rootScope.user.logout();
                        $location.path('/login');
                        $rootScope.successAlert = 'Your Email was updated. Please login again.';
                    } else {
                        $rootScope.successAlert = 'Your profile was updated';
                        $location.path("/yourprofile");
                    }
                },
                function error(response) {
                    console.log("Error when updating hunter");
                    console.log(response);
                    switch (response.status) {
                        case 409:
                            $rootScope.errorAlert = 'Account with this nick or email already exists';
                            break;
                        default:
                            if (response.data.errors) {
                                angular.forEach(response.data.errors, function (value) {
                                    $rootScope.errorAlert = 'Could not update account, please try latter';
                                });
                            }
                            break;
                    }
                }
            )
        }

    });

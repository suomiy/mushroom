/**
 * Created by suomiy on 16.12.16.
 */

portalControllers.controller('RegisterCtrl',
    function ($scope, $http, $rootScope, $location) {
        $scope.hunter = {};

        $scope.register = function (hunter) {
            $http({
                method: 'POST',
                url: 'rest/hunter/register',
                data: hunter
            }).then(function success(response) {
                    $rootScope.user.email = hunter.email;
                    $rootScope.user.password = hunter.password;
                    $rootScope.login($http, function () {
                        $location.path('/yourvisits');
                    }, function () {
                        $location.path('/login');
                    })
                },
                function error(response) {
                    switch (response.status) {
                        case 409:
                            $rootScope.errorAlert = 'Account with this nick or email already exists';
                            break;
                        default:
                            $rootScope.errorAlert = 'Could not sign up, please try latter';
                            break;
                    }
                }
            )
        }
    }
)
;

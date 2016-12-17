/**
 * Created by suomiy on 16.12.16.
 */

portalControllers.controller('LoginCtrl',
    function ($scope, $http, $rootScope, $location) {

        $scope.login = function (credentials) {
            if (credentials.email) {
                $rootScope.user.email = credentials.email;
            }

            if (credentials.password) {
                $rootScope.user.password = credentials.password;
            }

            $rootScope.login($http, function (result) {
                $location.path('/yourvisits');
            },function (result) {
                $rootScope.errorAlert = 'Incorrect credentials';
            });
        }
    }
);

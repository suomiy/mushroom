portalControllers.controller('LoginCtrl',
    function ($scope, $rootScope, $location, LoginService) {

        $scope.login = function (credentials) {
            if (credentials.email) {
                $rootScope.user.email = credentials.email;
            }

            if (credentials.password) {
                $rootScope.user.password = credentials.password;
            }

            LoginService.login(function () {
                $location.path('/yourvisits');
            }, function () {
                $rootScope.errorAlert = 'Incorrect credentials';
            });
        }
    }
);

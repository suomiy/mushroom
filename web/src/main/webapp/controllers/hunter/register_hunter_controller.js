portalControllers.controller('RegisterCtrl',
    function ($scope, $rootScope, $location, HunterRestService, LoginService) {
        $scope.hunter = {};

        $scope.register = function (hunter) {
            HunterRestService.getRegisterPromise(hunter).then(function success() {
                    $rootScope.user.email = hunter.email;
                    $rootScope.user.password = hunter.password;
                    LoginService.login(function () {
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
);

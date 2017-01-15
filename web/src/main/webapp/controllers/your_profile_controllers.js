portalControllers.controller('YourProfileCtrl', function ($rootScope, $scope, HunterRestService) {
    HunterRestService.setFindById($scope, $rootScope.user.id);
});

portalControllers.controller('YourProfileUpdateCtrl',
    function ($scope, $routeParams, $rootScope, $location, RestErrorHandlers, Utils, HunterRestService) {

        HunterRestService.setFindById($scope, $rootScope.user.id);
        $scope.showRanksAndRoles = false;

        $scope.updateHunter = function (hunter) {
            HunterRestService.getUpdatePromise(hunter).then(function success() {
                    if ($rootScope.user.isEmailOfThisUserAltered(hunter)) {
                        $rootScope.user.logout();
                        $location.path('/login');
                        Utils.showSuccessOnLocationChange('Your Email was updated. Please login again.');
                    } else {
                        $location.path("/yourprofile");
                        Utils.showSuccessOnLocationChange('Your profile was updated');
                    }
                }, RestErrorHandlers.makeUpdateErrorHandler("hunter", 'Account with this nick or email already exists')
            )
        }

    }
);

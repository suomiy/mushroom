portalControllers.controller('HunterUpdateCtrl',
    function ($scope, $routeParams, $rootScope, $location, RestErrorHandlers, Utils, HunterRestService) {

        var hunterId = $routeParams.hunterId;
        $scope.showRanksAndRoles = true;
        $scope.ranks = ranks;
        $scope.types = createRoles;

        HunterRestService.setFindById($scope, hunterId);

        $scope.updateHunter = function (hunter) {
            HunterRestService.getUpdatePromise(hunter).then(function success() {
                    if ($rootScope.user.isEmailOfThisUserAltered(hunter)) {
                        $rootScope.user.logout();
                        $location.path('/login');
                        Utils.showSuccessOnLocationChange('Your Email was updated. Please login again.');
                    } else {
                        $location.path("/hunters");
                        Utils.showSuccessOnLocationChange('Updated hunter "' + hunter.email + '"');

                    }
                }, RestErrorHandlers.makeUpdateErrorHandler("hunter", 'Account with this nick or email already exists')
            )
        }
    }
);

portalControllers.controller('HuntersCtrl',
    function ($scope, $rootScope, $location, Utils, RestErrorHandlers, HunterRestService) {

        HunterRestService.setAll($scope);

        $scope.deleteHunter = function (hunter) {
            HunterRestService.getDeletePromise(hunter.id).then(
                function success() {
                    if ($rootScope.user.isDeleted(hunter)) {
                        $rootScope.user.logout();
                        $location.path('/login');
                        Utils.showSuccessOnLocationChange('Your Account was deleted. Have a nice day.');
                    } else {
                        Utils.showSuccess('Deleted hunter "' + hunter.email + '"');
                        HunterRestService.setAll($scope);
                    }
                }, RestErrorHandlers.makeDeleteErrorHandler("hunter")
            );
        };


        $scope.resetForm = function () {
            $scope.email = null;
            HunterRestService.setAll($scope);
        };

        $scope.findHunterByEmail = function (email) {
            HunterRestService.setFindByEmail($scope, email);
        };
    }
);

portalControllers.controller('CreateMushroomsCountCtrl',
    function ($scope, $routeParams, $rootScope, $location, isYourCatch, RestErrorHandlers, Utils, MushroomRestService, MushroomCountRestService) {
        $scope.mushroomCount = {
            visitId: $routeParams.visitId
        };

        MushroomRestService.setAll($scope);

        $scope.createMushroomCount = function (mushroomCount) {
            var visitId = mushroomCount.visitId;
            mushroomCount = angular.copy(mushroomCount);
            mushroomCount.mushroomId = mushroomCount.mushroom.id;

            MushroomCountRestService.getCreatePromise(mushroomCount).then(function success() {
                    $location.path((isYourCatch ? "/yourvisits/update/" : "/visits/update/") + visitId);
                    Utils.showSuccessOnLocationChange('Catch created!');
                },
                RestErrorHandlers.makeCreateErrorHandler("Catch")
            )
        };
    }
);

portalControllers.controller('UpdateMushroomsCountCtrl',
    function ($scope, $routeParams, $rootScope, $location, isYourCatch, RestErrorHandlers, Utils, CombinedRestService, MushroomCountRestService) {
        var mushroomsCountId = $routeParams.mushroomsCountId;

        var updateMCCallback = function () {
            $scope.mushroomCount.mushroom = $scope.mushrooms.filter(function (mushroom) { // select
                return mushroom.id == $scope.mushroomCount.mushroomId;
            }).shift();
        };

        CombinedRestService.setUpdateMushroomCount($scope, mushroomsCountId, updateMCCallback);

        $scope.updateMushroomCount = function (mushroomCount) {
            var visitId = mushroomCount.visitId;
            mushroomCount.mushroomId = mushroomCount.mushroom.id;

            MushroomCountRestService.getUpdatePromise(mushroomCount).then(function success() {
                    $location.path((isYourCatch ? "/yourvisits/update/" : "/visits/update/") + visitId);
                    Utils.showSuccessOnLocationChange('Catch updated!');
                },
                RestErrorHandlers.makeUpdateErrorHandler("Catch")
            )
        };
    }
);

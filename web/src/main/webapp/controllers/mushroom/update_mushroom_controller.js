portalControllers.controller('UpdateMushroomCtrl',
    function ($scope, $routeParams, $location, RestErrorHandlers, RestUtils, Utils, MushroomRestService) {
        var mushroomId = $routeParams.mushroomId;
        $scope.types = types;
        if (mushroomId == null) {
            Utils.showError('No visit id found in the url');
            return;
        }

        var updateCallback = function () {
            RestUtils.setFromToDateAsMoment($scope.mushroom);
        };

        MushroomRestService.setById($scope, mushroomId, updateCallback);

        $scope.updateMushroom = function (mushroom) {
            mushroom = angular.copy(mushroom);
            RestUtils.setFromToDateToRest(mushroom);

            MushroomRestService.getUpdatePromise(mushroom).then(function success() {
                    $location.path("/mushrooms");
                    Utils.showSuccessOnLocationChange('Mushroom updated!');
                }, RestErrorHandlers.makeUpdateErrorHandler("Visit", 'A Mushroom with this name already exists.')
            )
        };
    }
);

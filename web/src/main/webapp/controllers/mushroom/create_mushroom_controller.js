portalControllers.controller('CreateMushroomCtrl',
    function ($scope, $rootScope, $location, RestUtils, Utils, RestErrorHandlers, MushroomRestService) {

        $scope.types = types;
        $scope.mushroom = {
            'id': null,
            'name': '',
            'description': '',
            'fromDate': '',
            'toDate': '',
            'type': ''
        };

        $scope.createMushroom = function (mushroom) {
            mushroom = angular.copy(mushroom);
            RestUtils.setFromToDateToRest(mushroom);

            MushroomRestService.getCreatePromise(mushroom).then(function success() {
                    $location.path("/mushrooms");
                    Utils.showSuccessOnLocationChange('Mushroom created!');
                },
                RestErrorHandlers.makeCreateErrorHandler("mushroom", 'A Mushroom with this name already exists.')
            )
        };

    }
);

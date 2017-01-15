portalControllers.controller('CreateForestCtrl',
    function ($scope, $rootScope, $location, RestErrorHandlers, Utils, ForestRestService) {

        $scope.forest = {
            'id': null,
            'name': '',
            'localityDescription': ''
        };

        $scope.createForest = function (forest) {
            ForestRestService.getCreatePromise(forest).then(function success() {
                    $location.path("/forests");
                    Utils.showSuccessOnLocationChange('Created "' + forest.name + '"');
                }, RestErrorHandlers.makeCreateErrorHandler("forest", 'Forest with this name already exists')
            )
        }

    }
);

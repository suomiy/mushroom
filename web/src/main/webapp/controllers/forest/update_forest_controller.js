portalControllers.controller('UpdateForestCtrl',
    function ($scope, $routeParams, $rootScope, $location, RestErrorHandlers, Utils, ForestRestService) {
        var forestId = $routeParams.forestId;

        ForestRestService.setById($scope, forestId);

        $scope.updateForest = function (forest) {
            ForestRestService.getUpdatePromise(forest).then(function success() {
                    $location.path("/forests");
                    Utils.showSuccessOnLocationChange('Updated "' + forest.name + '"');

                }, RestErrorHandlers.makeUpdateErrorHandler("Hunter", 'Forest with this name already exists')
            )
        }
    }
);

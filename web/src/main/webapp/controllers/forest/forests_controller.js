portalControllers.controller('ForestsCtrl',
    function ($scope, $rootScope, Utils, RestErrorHandlers, ForestRestService) {
        $scope.resetForm = function () {
            $scope.name = null;
            ForestRestService.setAll($scope);
        };

        $scope.resetForm();

        $scope.deleteForest = function (forest) {
            ForestRestService.getDeletePromise(forest.id).then(
                function success() {
                    Utils.showSuccess('Deleted forest "' + forest.name + '"');
                    $scope.resetForm();
                }, RestErrorHandlers.makeDeleteErrorHandler("forest", 'You can not delete this forest because visit have reference to forest')
            );
        };

        $scope.findForestByName = function (name) {
            ForestRestService.setByName($scope, name);
        };

    }
);



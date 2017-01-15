portalControllers.controller('MushroomsCtrl',
    function ($scope, $rootScope, RestErrorHandlers, Utils, MushroomRestService) {

        $scope.resetForm = function () {
            $scope.name = null;
            $scope.type = null;
            $scope.date = null;
            $scope.fromDate = null;
            $scope.toDate = null;

            MushroomRestService.setAll($scope);
        };

        $scope.resetForm();
        $scope.types = types;

        $scope.deleteMushroom = function (mushroom) {
            MushroomRestService.getDeletePromise(mushroom.id).then(
                function success() {
                    Utils.showSuccess('Deleted mushroom "' + mushroom.name + '"');
                    $scope.resetForm();
                }, RestErrorHandlers.makeDeleteErrorHandler("mushroom", 'You can not delete this mushroom because some visit has a reference to it.')
            );
        };

        $scope.findMushroomByName = function (name) {
            MushroomRestService.setByName($scope, name);
        };

        $scope.findMushroomByType = function (type) {
            MushroomRestService.setByType($scope, type);
        };
        $scope.findMushroomByDate = function (date) {
            MushroomRestService.setByDate($scope, date);
        };

        $scope.findMushroomByDateInterval = function (fromDate, toDate) {
            MushroomRestService.setByDateInterval($scope, fromDate, toDate);
        };

    }
);


portalControllers.controller('VisitCtrl',
    function ($scope, $routeParams, $rootScope, $location, isYourVisit, RestErrorHandlers, RestUtils, Utils,
              CombinedRestService, VisitRestService) {

        var userId = isYourVisit ? $rootScope.user.id : undefined;
        $scope.resetForm = function () {
            $scope.date = null;
            $scope.hunterId = null;
            $scope.forestId = null;
            $scope.mushroomId = null;

            CombinedRestService.setAllCompleteVisits($scope, userId);
        };

        $scope.resetForm(); // load

        $scope.deleteVisit = function (id) {
            VisitRestService.getDeletePromise(id).then(
                function success() {
                    Utils.showSuccess('Visit deleted!');
                    $scope.resetForm();
                },
                RestErrorHandlers.makeDeleteErrorHandler("Visit")
            );
        };

        $scope.findVisitByMushroom = function (mushroomId) {
            CombinedRestService.setFindByMushroomsVisits($scope, userId, mushroomId);
        };

        $scope.findVisitByDate = function (date) {
            CombinedRestService.setFindByDateVisits($scope, userId, date);
        };

        $scope.findVisitByForest = function (forestId) {
            CombinedRestService.setFindByForestVisits($scope, userId, forestId);
        };

        $scope.findVisitByHunter = function (hunterId) {
            CombinedRestService.setFindByHunterVisits($scope, userId, hunterId);
        };
    }
);

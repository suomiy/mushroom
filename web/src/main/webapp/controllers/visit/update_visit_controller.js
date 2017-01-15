portalControllers.controller('UpdateVisitCtrl',
    function ($scope, $routeParams, $rootScope, $location, isYourVisit, RestErrorHandlers,
              CombinedRestService, VisitRestService, MushroomCountRestService, RestUtils, Utils) {
        var visitId = $routeParams.visitId;
        if (visitId == null) {
            Utils.showError('No visit id found in the url');
            return;
        }

        var updateVisitCallback = function () {
            $scope.visit.forest = $scope.forests.filter(function (forest) { // select
                return forest.id == $scope.visit.forestId;
            }).shift();
            RestUtils.setFromToDateAsMoment($scope.visit)
        };

        CombinedRestService.setUpdateVisit($scope, visitId, !isYourVisit, updateVisitCallback);

        // functions
        $scope.updateVisit = function (visit) {
            // prepare and check data
            visit = angular.copy(visit);
            RestUtils.setFromToDateToRest(visit);
            if (!Utils.assertFromToDates(visit)) {
                Utils.showError('From date is larger than to date!');
                return;
            }
            visit.forestId = visit.forest.id;

            VisitRestService.getUpdatePromise(visit).then(function success(response) {
                    $location.path(isYourVisit ? "/yourvisits" : '/visits');
                    Utils.showSuccessOnLocationChange('Visit updated!');
                },
                RestErrorHandlers.makeUpdateErrorHandler("Visit")
            )
        };

        $scope.deleteMushroomsCount = function (id) {
            MushroomCountRestService.getDeletePromise(id).then(
                function success() {
                    Utils.showSuccess('Catch deleted!');
                    CombinedRestService.setUpdateVisit($scope, visitId, !isYourVisit, updateVisitCallback);
                },
                RestErrorHandlers.makeDeleteErrorHandler("Catch")
            );
        };

    }
);



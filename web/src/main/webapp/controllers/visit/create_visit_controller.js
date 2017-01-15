portalControllers.controller('CreateVisitCtrl',
    function ($scope, $routeParams, $rootScope, $location, isYourVisit, RestErrorHandlers, RestUtils, Utils,
              CombinedRestService, VisitRestService) {
        $scope.visit = {
            'id': null,
            'note': null,
            'fromDate': null,
            'toDate': null,
            'hunter': null,
            'forest': null
        };

        CombinedRestService.seCreateVisit($scope, !isYourVisit);

        $scope.createVisit = function (visit) {
            visit = angular.copy(visit);
            RestUtils.setFromToDateToRest(visit);
            if (!Utils.assertFromToDates(visit)) {
                Utils.showError('From date is larger than to date!');
                return;
            }
            visit.forestId = visit.forest.id;
            visit.hunterId = isYourVisit ? $rootScope.user.id : visit.hunter.id;

            VisitRestService.getCreatePromise(visit).then(function success() {
                    $location.path(isYourVisit ? "/yourvisits" : "/visits");
                    Utils.showSuccessOnLocationChange('Visit created!');
                },
                RestErrorHandlers.makeCreateErrorHandler("visit")
            )
        };
    }
);

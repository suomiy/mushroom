/**
 * Created by kisty on 18.12.16.
 */


portalControllers.controller('YourVisitsCtrl', function ($rootScope,$scope, $http) {
    loadVisitsByHunter($http, $scope, $rootScope);

    $scope.deleteVisit = function (visit) {
        console.log("deleting visit with id=" + visit.id );
        $http.delete('rest/visit/' + visit.id ).then(
            function success(response) {
                console.log('deleted visit ' + visit.id + '  on server');
                $rootScope.successAlert = "Visit " + visit.id + " deleted.";
                loadVisitsByHunter($http, $scope,$rootScope);
            },
            function error(response) {
                console.log("error when deleting visit");
                console.log(response);
                switch (response.data.code) {
                    case 409:
                        $rootScope.errorAlert = 'You can not delete this visit.';
                        break;
                    default:
                        angular.forEach(response.data.errors, function(value) {
                            $rootScope.errorAlert = 'Cannot delete visit - ' + value;
                        });
                        break;
                }
            }
        );
    };

    $scope.findAllVisits = function () {
        loadVisitsByHunter($http, $scope, $rootScope);
    };

    $scope.findVisitByMushroom = function (mushroom) {
        findVisitByMushroom(mushroom, $scope, $http,true , $rootScope);
    };

    $scope.findVisitByDate = function(date){
        findVisitByDate(date, $scope, $http ,true , $rootScope);
    };

    $scope.findVisitByForest = function(forest) {
        findVisitByForest(forest, $scope,$http ,true , $rootScope);
    }
});


portalControllers.controller('CreateYourVisitCtrl',
    function ($scope, $http, $rootScope, $location, sharedData) {

        $scope.visit = {
            'id': null,
            'note': '',
            'fromDate': '',
            'toDate': '',
            'hunterId': $rootScope.user.id,
            'forest_id': ''
        };

        loadVisits($http, $scope, false, sharedData);

        $scope.createVisit = function (visit) {
            console.log("Creating visit." );
            console.log(visit);

            $http({
                method: 'POST',
                url: 'rest/visit/create',
                data: visit
            }).then(function success(response) {
                    console.log('Created visit on server');
                    $rootScope.successAlert = 'Visit created.';
                    $location.path("/yourvisits");
                },
                function error(response) {
                    console.log("error when creating visit");
                    console.log(response);
                    switch (response.data.code) {
                        case 409:
                            $rootScope.errorAlert = 'Cannot create visit.';
                            break;
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot create visit - ' + value;
                            });
                            break;
                    }
                }
            )
        }

    }
);


portalControllers.controller('UpdateYourVisitCtrl',
    function ($scope , $routeParams, $http, $rootScope, $location, sharedData) {

        var visitId = $routeParams.visitId;

        sharedData.setVisitId(visitId);
        loadVisits($http, $scope, visitId, sharedData);

        $scope.updateVisit = function (visit) {
            console.log("Updating visit with id=" + visit.id);
            $http({
                method: 'POST',
                url: 'rest/visit/update',
                data: visit
            }).then(function success(response) {
                    console.log('Updated visit ' + visit.id + '  on server');
                    $rootScope.successAlert = 'Visit ' + visit.id + ' updated';
                    $location.path("/yourvisits");
                },
                function error(response) {
                    console.log("error when updating visit");
                    console.log(response);
                    switch (response.data.code) {
                        case 409:
                            $rootScope.errorAlert = 'Cannot update Visit.';
                            break;
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot update visit - ' + value;
                            });
                            break;
                    }
                }
            )
        }


        $scope.deleteMushroomsCount = function (mushroomscount) {
            console.log("deleting mushroomcount with id=" + mushroomscount.id );
            $http.delete('rest/mushroomcount/' + mushroomscount.id ).then(
                function success(response) {
                    console.log('deleted mushroomscount ' + mushroomscount.id + '  on server');
                    $rootScope.successAlert = "Catch " + mushroomscount.id + " deleted.";
                    findVisitById(mushroomscount.visitId, $scope, $http);
                },
                function error(response) {
                    console.log("error when deleting visit");
                    console.log(response);
                    switch (response.data.code) {
                        case 409:
                            $rootScope.errorAlert = 'You can not delete this catch.';
                            break;
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot delete mushroomscount - ' + value;
                            });
                            break;
                    }
                }
            );
        };

    }
);



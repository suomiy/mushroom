/**
 * @author Michal Kysilko 436339
 */

portalControllers.controller('VisitCtrl',
    function ($scope, $rootScope, $http ) {

        loadVisits($http, $scope);
        loadHunters($http,$scope);
        loadMushrooms($http,$scope);
        loadForests($http,$scope);

        $scope.deleteVisit = function (visit) {
            console.log("deleting visit with id=" + visit.id );
            $http.delete('rest/visit/' + visit.id ).then(
                function success(response) {
                    console.log('deleted visit ' + visit.id + '  on server');
                    $rootScope.successAlert = "Visit " + visit.id + " deleted.";
                    loadVisits($http, $scope);
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

        $scope.resetForm = function(){
            $scope.date = null;
            $scope.hunter = null;
            $scope.forest = null;
            $scope.mushroom = null;

            loadVisits($http,$scope);
        };

        $scope.findVisitByMushroom = function (mushroomId) {
            findVisitByMushroom(mushroomId, $scope, $http);
        };

        $scope.findVisitByDate = function(date){
            findVisitByDate(date, $scope, $http, $rootScope);
        };

        $scope.findVisitByForest = function(forestId){
            findVisitByForest(forestId, $scope, $http);
        };

        $scope.findVisitByHunter = function(hunterId){
            findVisitByHunter(hunterId, $scope, $http);
        };

    }
);

portalControllers.controller('CreateVisitCtrl',
    function ($scope, $http, $rootScope, $location, sharedData) {

        $scope.visit = {
            'id': null,
            'note': '',
            'fromDate': '',
            'toDate': '',
            'hunter_id': '',
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
                    $location.path("/visits");
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

portalControllers.controller('UpdateVisitCtrl',
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


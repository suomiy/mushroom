/**
 * @author Michal Kysilko 436339
 */


portalControllers.controller('CreateMushroomsCountCtrl',
    function ($scope, $http, $rootScope, $location, sharedData) {

        $scope.visitId = sharedData.getVisitId();
        $scope.mushrooms = sharedData.getMushrooms();
        $scope.mushroomscount = {
            'id': null,
            'count': '',
            'mushroomId': '',
            'visitId': $scope.visitId,
        };

        $scope.createMushroomsCount = function (mushroomscount) {
            console.log("Creating mushroomsCount." );
            console.log(mushroomscount);
            $http({
                method: 'POST',
                url: 'rest/mushroomcount/create',
                data: mushroomscount
            }).then(function success(response) {
                    console.log('Created mushroomsCount on server');
                    $rootScope.successAlert = 'Catches created.';
                    $location.path("/visits/update/"+$scope.visitId);
                },
                function error(response) {
                    console.log("error when creating mushroomsCount");
                    console.log(response);
                    switch (response.data.code) {
                        case 409:
                            $rootScope.errorAlert = 'Cannot create Catches.';
                            break;
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot create Catches - ' + value;
                            });
                            break;
                    }
                }
            )
        }

    }
);

portalControllers.controller('UpdateMushroomsCountCtrl',
    function ($scope , $routeParams, $http, $rootScope, $location, sharedData) {

        var mushroomsCountId = $routeParams.mushroomsCountId;
        $scope.visitId = sharedData.getVisitId();
        $scope.mushrooms = sharedData.getMushrooms();
        //loadVisits($http, $scope, visitId);
        findMushroomsCountById(mushroomsCountId, $scope, $http);

        $scope.updateMushroomsCount = function (mushroomsCounts) {
            console.log("Updating mushroomsCount with id=" + mushroomsCountId);
            console.log(mushroomsCounts);
            var visitId = mushroomsCounts.visitId;
            $http({
                method: 'POST',
                url: 'rest/mushroomcount/update',
                data: mushroomsCounts
            }).then(function success(response) {
                    console.log('Updated mushroomsCount ' + mushroomsCounts.id + '  on server');
                    $rootScope.successAlert = 'Catches with id ' + mushroomsCounts.id + ' updated';
                    $location.path("/visits/update/"+visitId);
                    loadVisits($http, $scope, mushroomsCounts.visitId);
                    //findVisitById(mushroomsCounts.visitId, $scope, $http);
                },
                function error(response) {
                    console.log("error when updating mushroomsCount");
                    console.log(response);
                    switch (response.data.code) {
                        case 409:
                            $rootScope.errorAlert = 'Cannot update Catches.';
                            break;
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot update mushroomsCount - ' + value;
                            });
                            break;
                    }
                }
            )
        }

    }
);
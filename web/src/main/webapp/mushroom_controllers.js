/**
 * @author Michal Kysilko 436339
 */

portalControllers.controller('MushroomsCtrl',
    function ($scope, $rootScope, $http ) {

        loadMushrooms($http, $scope);

        $scope.deleteMushroom = function (mushroom) {
            console.log("deleting mushroom with id=" + mushroom.id + ' (' + mushroom.name + ')');
            $http.delete('rest/mushroom/' + mushroom.id ).then(
                function success(response) {
                    console.log('deleted mushroom ' + mushroom.id + ' (' + mushroom.name + ')  on server');
                    $rootScope.successAlert = 'Deleted mushroom "' + mushroom.name + '"';
                    loadMushrooms($http, $scope);
                },
                function error(response) {
                    console.log("error when deleting mushroom");

                    if(!response) return;
                    switch (response.data.code) {
                        case 409:
                            $rootScope.errorAlert = 'You can not delete this mushroom because some visit has a reference to it.';
                            break;
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot delete mushroom - ' + value;
                            });
                            break;
                    }
                }
            );
        };

        $scope.resetForm = function(){
            $scope.name = null;
            $scope.type = null;
            $scope.date = null;
            $scope.fromDate = null;
            $scope.toDate = null;

            loadMushrooms($http,$scope);
        };

        $scope.findMushroomByName = function (name) {
            findMushroomByName(name, $scope, $http);
        };

        $scope.findMushroomByDate = function(date){
            findMushroomByDate(date, $scope, $http, $rootScope);
        };

        $scope.findMushroomByType = function(type){
            findMushroomByType(type, $scope, $http);
        };

        $scope.findMushroomByDateInterval = function(fromDate, toDate){
            findMushroomByDateInterval(fromDate, toDate, $scope, $http, $rootScope);
        };

    }
);

portalControllers.controller('CreateMushroomCtrl',
    function ($scope, $http, $rootScope, $location) {

        $scope.types = types;

        $scope.mushroom = {
            'id': null,
            'name': '',
            'description': '',
            'fromDate': '',
            'toDate': '',
            'type': ''
        };

        $scope.createMushroom = function (mushroom) {
            console.log("Creating mushroom with name=" + mushroom.name);
            $http({
                method: 'POST',
                url: 'rest/mushroom/create',
                data: mushroom
            }).then(function success(response) {
                    console.log('Created mushroom ' + mushroom.name + '  on server');
                    $rootScope.successAlert = 'Created "' + mushroom.name + '"';
                    $location.path("/mushrooms");
                },
                function error(response) {
                    console.log("error when creating mushroom");
                    console.log(response);
                    switch (response.data.code) {
                        case 409:
                            $rootScope.errorAlert = 'A Mushroom with this name already exists.';
                            break;
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot create mushroom - ' + value;
                            });
                            break;
                    }
                }
            )
        }

    }
);

portalControllers.controller('UpdateMushroomCtrl',
    function ($scope , $routeParams, $http, $rootScope, $location) {

        var mushroomId = $routeParams.mushroomId;

        $scope.types = types;

        findMushroomById(mushroomId, $scope, $http);

        $scope.updateMushroom = function (mushroom) {
            console.log("Updating mushroom with id=" + mushroom.id);
            $http({
                method: 'POST',
                url: 'rest/mushroom/update',
                data: mushroom
            }).then(function success(response) {
                    console.log('Updated mushroom ' + mushroom.id + '  on server');
                    $rootScope.successAlert = 'Updated "' + mushroom.name + '"';
                    $location.path("/mushrooms");
                },
                function error(response) {
                    console.log("error when updating mushroom");
                    console.log(response);
                    switch (response.data.code) {
                        case 409:
                            $rootScope.errorAlert = 'A Mushroom with this name already exists.';
                            break;
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot update mushroom - ' + value;
                            });
                            break;
                    }
                }
            )
        }

    }
);

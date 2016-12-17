/**
 * @author Michal Kysilko 436339
 */


portalControllers.controller('MushroomsCtrl',
    function ($scope, $rootScope, $http ) {

        loadMushrooms($http, $scope);

        $scope.findAllMushrooms = function () {
            loadMushrooms($http,$scope);
        };

        $scope.findMushroomByName = function (email) {
            findMushroomByName(email, $scope, $http);
        };

        $scope.findMushroomByDate = function(date){
            findMushroomByDate(date, $scope, $http);
        };

        $scope.findMushroomByType = function(type){
            findMushroomByType(type, $scope, $http);
        };

        $scope.findMushroomByDateInterval = function(type){
            findMushroomByDateInterval(fromDate, toDate, $scope, $http);
        };

    }
);
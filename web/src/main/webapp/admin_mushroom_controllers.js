/**
 * @author Michal Kysilko 436339
 */


function parseDates($scope) {

    var date;
    for (var i = 0, len = $scope.mushrooms.length; i < len; i++) {
        console.log($scope.mushrooms[i].fromDate);
        console.log($scope.mushrooms[i].toDate)
        if(!$scope.mushrooms[i].fromDate || !$scope.mushrooms[i].toDate) continue;
        //$scope.mushroomslist[i].fromDate = $scope.mushroomslist[i].fromDate
        date = $scope.mushrooms[i].fromDate;
        $scope.mushrooms[i].fromDate = date.replace("-","/").substring(5,10);

        date = $scope.mushrooms[i].toDate;
        $scope.mushrooms[i].toDate = date.replace("-","/").substring(5,10);
    }
}

function loadAdminMushrooms($http, $scope) {
    $http.get('/pa165/rest/mushroom/findall').then(function (response) {
        $scope.mushrooms = response.data;
        parseDates($scope);
        console.log("Getting all mushrooms");
        //console.log($scope.mushrooms);
    });
}

function findMushroomById($mushroomId, $scope, $http) {
    $http.get('/pa165/rest/mushroom/' + $mushroomId).then(function (response) {
        $scope.mushroom = response.data;
        console.log($scope.mushroom);
        console.log('mushroom with name' + $scope.mushroom.name + 'loaded');
    })

};

function findMushroomByName($name, $scope, $http) {
    var m;

    $http.get('/pa165/rest/mushroom/find?name=' + $name).then(function (response) {
        m = response.data;

        if(response.data) {
            $scope.mushrooms = [m];
            //console.log('Hunter with email' + hunter.email + 'loaded');
        }else{
            $scope.mushrooms = [];
            //console.log('Hunter with email doesn t exists');
        }
        parseDates($scope);

    });
}

function findMushroomByType($type, $scope, $http) {
    var m;

    $http.get('/pa165/rest/mushroom/findbytype?type=' + $type).then(function (response) {
        m = response.data;

        if(response.data) {
            $scope.mushrooms = m;
            parseDates($scope);
            //console.log('Hunter with email' + hunter.email + 'loaded');
        }else{
            $scope.mushrooms = null;
            //console.log('Hunter with email doesn t exists');
        }

    });
}

function findMushroomByDate($date, $scope, $http) {
    var m;

    var data = {};
    data.date = $date;

    $http({
        method: 'POST',
        url: '/pa165/rest/mushroom/findbydate',
        data: data
    }).then(function (response){
        m = response.data;

        if(response.data) {
            $scope.mushrooms = m;
            parseDates($scope);
            //console.log('Hunter with email' + hunter.email + 'loaded');
        }else{
            $scope.mushrooms = [];
            //console.log('Hunter with email doesn t exists');
        }


    })
}

function findMushroomByDateInterval($fromDate, $toDate, $scope, $http) {
    var m;


    var data = {};
    data.from = $fromDate.value;
    data.to = $toDate.value;

    $http({
        method: 'POST',
        url: '/pa165/rest/mushroom/findbydateinterval',
        data: data
    }).then(function (response){
        m = response.data;

        if(response.data) {
            $scope.mushrooms = m;
            parseDates($scope);
            //console.log('Hunter with email' + hunter.email + 'loaded');
        }else{
            $scope.mushrooms = [];
            //console.log('Hunter with email doesn t exists');
        }


    })
}

portalControllers.controller('AdminMushroomsCtrl',
    function ($scope, $rootScope, $http ) {

        loadAdminMushrooms($http, $scope);

        $scope.deleteMushroom = function (mushroom) {
            //console.log("deleting mushroom with id=" + mushroom.id + ' (' + mushroom.name + ')');
            $http.delete('/pa165/rest/mushroom/' + mushroom.id ).then(
                function success(response) {
                    //console.log('deleted mushroom ' + mushroom.id + ' (' + mushroom.name + ')  on server');
                    $rootScope.successAlert = 'Deleted mushroom "' + mushroom.name + '"';
                    loadAdminMushrooms($http, $scope);
                },
                function error(response) {
                    //console.log("error when deleting mushroom");
                    //console.log(response);
                    switch (response.data.code) {
                        default:
                            angular.forEach(response.data.errors, function(value) {
                                $rootScope.errorAlert = 'Cannot delete mushroom - ' + value;
                            });
                            break;
                    }
                }
            );
        };

        $scope.findAllMushrooms = function () {
            loadAdminMushrooms($http,$scope);
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

portalControllers.controller('AdminCreateMushroomCtrl',
    function ($scope, $http, $rootScope, $location) {

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
                url: '/pa165/rest/mushroom/create',
                data: mushroom
            }).then(function success(response) {
                    console.log('Created mushroom ' + mushroom.name + '  on server');
                    $rootScope.successAlert = 'Created "' + mushroom.name + '"';
                    $location.path("/admin/mushrooms");
                },
                function error(response) {
                    console.log("error when creating mushroom");
                    console.log(response);
                    switch (response.data.code) {
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

portalControllers.controller('AdminUpdateMushroomCtrl',
    function ($scope , $routeParams, $http, $rootScope, $location) {

        var mushroomId = $routeParams.mushroomId;

        $scope.types = types;

        findMushroomById(mushroomId, $scope, $http);

        $scope.updateMushroom = function (mushroom) {
            console.log("Updating mushroom with id=" + mushroom.id);
            $http({
                method: 'POST',
                url: '/pa165/rest/mushroom/update',
                data: mushroom
            }).then(function success(response) {
                    console.log('Updated mushroom ' + mushroom.id + '  on server');
                    $rootScope.successAlert = 'Updated "' + mushroom.name + '"';
                    $location.path("/admin/mushrooms");
                },
                function error(response) {
                    console.log("error when updating mushroom");
                    console.log(response);
                    switch (response.data.code) {
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
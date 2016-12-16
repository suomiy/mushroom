'use strict';

/**
 * Created by Erik Macej on 14.12.16.
 */

var mushroomHunterApp = angular.module('mushroomHunterApp', ['ngRoute','portalControllers']);
var portalControllers = angular.module('portalControllers', []);
var ranks = [ 'BEGINNER', 'SKILLED', 'EXPERT', 'GURU'];
var roles = [ 'ANONYMOUS', 'ADMIN', 'USER' ];

mushroomHunterApp.run(function ($rootScope) {
        $rootScope.user = {
            role: roles[0],
            id: null
        }
    });

mushroomHunterApp.config(['$routeProvider',
    function ($routeProvider) {

        adminHunterConfig($routeProvider);
        adminForestConfig($routeProvider);
        forestConfig($routeProvider);
        hunterConfig($routeProvider);

        $routeProvider.
        when('/yourvisits', { templateUrl: 'resources/partials/your_visits.html', controller: 'YourVisitsCtrl' }).
        when('/yourcatches', { templateUrl: 'resources/partials/your_catches.html', controller: 'YourCatchesCtrl'}).
        when('/catches', { templateUrl: 'resources/partials/catches.html', controller: 'CatchesCtrl'}).
        when('/visits', { templateUrl: 'resources/partials/visits.html', controller: 'VisitsCtrl'}).
        when('/mushrooms', { templateUrl: 'resources/partials/mushrooms.html', controller: 'MushroomsCtrl'}).
        when('/admin/visits', { templateUrl: 'resources/partials/admin/admin_visits.html', controller: 'AdminVisitsCtrl'}).
        when('/admin/mushroomcounts', { templateUrl: 'resources/partials/admin/admin_mushroomcounts.html', controller: 'AdminMushroomCountsCtrl'}).
        when('/admin/mushrooms', { templateUrl: 'resources/partials/admin/admin_mushrooms.html', controller: 'AdminMushroomsCtrl'}).
        when('/login', { templateUrl: 'resources/partials/login.html', controller: 'LoginCtrl'}).
        otherwise({redirectTo: '/yourprofile'});

    }]);

function hunterConfig($routeProvider) {
    $routeProvider.when('/yourprofile', { templateUrl: 'resources/partials/your_profile.html', controller: 'YourProfileCtrl' });
    $routeProvider.when('/yourprofile/update', { templateUrl: 'resources/partials/forms/update_hunter.html', controller: 'YourProfileUpdateCtrl' });
}

function adminHunterConfig($routeProvider) {
    $routeProvider.when('/admin/hunters', { templateUrl: 'resources/partials/admin/admin_hunters.html', controller: 'AdminHuntersCtrl'});
    $routeProvider.when('/admin/hunters/update/:hunterId', { templateUrl: 'resources/partials/forms/update_hunter.html', controller: 'AdminHunterUpdateCtrl'});
}

function adminForestConfig($routeProvider) {
    $routeProvider.when('/admin/forests', { templateUrl: 'resources/partials/admin/admin_forests.html', controller: 'AdminForestsCtrl'});
    $routeProvider.when('/admin/forests/newforest', { templateUrl: 'resources/partials/forms/create_forest.html', controller: 'AdminCreateForestCtrl'});
    $routeProvider.when('/admin/forests/update/:forestId', { templateUrl: 'resources/partials/forms/update_forest.html', controller: 'AdminUpdateForestCtrl'});

}

function forestConfig($routeProvider) {
    $routeProvider.when('/forests', { templateUrl: 'resources/partials/forests.html', controller: 'ForestsCtrl'});

}

mushroomHunterApp.run(function ($rootScope) {
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
});

portalControllers.controller('YourVisitsCtrl', function ($scope, $http) {

});

portalControllers.controller('YourCatchesCtrl', function ($scope, $http) {

});

portalControllers.controller('CatchesCtrl', function ($scope, $http) {

});

portalControllers.controller('VisitsCtrl', function ($scope, $http) {

});

portalControllers.controller('MushroomsCtrl', function ($scope, $http) {

});

portalControllers.controller('AdminMushroomCountsCtrl', function ($scope, $http) {

});

portalControllers.controller('AdminVisitsCtrl', function ($scope, $http) {

});

portalControllers.controller('AdminMushroomsCtrl', function ($scope, $http) {

});

portalControllers.controller('LoginCtrl', function ($scope, $http) {

});

function findHunterById($hunterId, $scope, $http) {
    $http.get('/pa165/rest/hunter/' + $hunterId).then(function (response) {
        $scope.hunter = response.data;
        console.log('loaded hunter ' + $scope.hunter.id + ' (' + $scope.hunter.email + ')');
    })

};

function loadAdminHunters($http, $scope) {
    $http.get('/pa165/rest/hunter/findall').then(function (response) {
        $scope.hunters = response.data;
        console.log('All hunters loaded');
    });
}

function findHunterByEmail($email, $scope, $http) {
    var hunter;

    $http.get('/pa165/rest/hunter/findbyemail?email=' + $email).then(function (response) {
        hunter = response.data;

        if(response.data) {
            $scope.hunters = [hunter];
            console.log('Hunter with email' + hunter.email + 'loaded');
        }else{
            $scope.hunters = [];
            console.log('Hunter with email doesn t exists');
        }

    });
}

function loadForests($http, $scope) {
    $http.get('/pa165/rest/forest/findall').then(function (response) {
        $scope.forests = response.data;
        console.log('All forests loaded');
    });
};

function findForestById($forestId, $scope, $http) {
    $http.get('/pa165/rest/forest/' + $forestId).then(function (response) {
        $scope.forest = response.data;
        console.log('Forest with name' + $scope.forest.name + 'loaded');
    })

};

function findForestByName($name, $scope, $http) {
    var forest;

    $http.get('/pa165/rest/forest/find?name=' + $name).then(function (response) {
        forest = response.data;

        if(response.data) {
            $scope.forests = [forest];
            console.log('Forest with name' + forest.name + 'loaded');
        }else{
            $scope.forests = [];
            console.log('Forest with name doesn t exists');
        }

    });
};




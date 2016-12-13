'use strict';

//created by Erik Macej, 433744

var mushroomHunterApp = angular.module('mushroomHunterApp', ['ngRoute','portalControllers']);
var portalControllers = angular.module('portalControllers', []);

mushroomHunterApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
        when('/yourprofile', { templateUrl: 'resources/partials/your_profile.html', controller: 'YourProfileCtrl' }).
        when('/yourvisits', { templateUrl: 'resources/partials/your_visits.html', controller: 'YourVisitsCtrl' }).
        when('/yourcatches', { templateUrl: 'resources/partials/your_catches.html', controller: 'YourCatchesCtrl'}).
        when('/catches', { templateUrl: 'resources/partials/catches.html', controller: 'CatchesCtrl'}).
        when('/visits', { templateUrl: 'resources/partials/visits.html', controller: 'VisitsCtrl'}).
        when('/mushrooms', { templateUrl: 'resources/partials/mushrooms.html', controller: 'MushroomsCtrl'}).
        when('/forests', { templateUrl: 'resources/partials/forests.html', controller: 'ForestsCtrl'}).
        when('/admin/hunters', { templateUrl: 'resources/partials/admin/admin_hunters.html', controller: 'AdminHuntersCtrl'}).
        when('/admin/visits', { templateUrl: 'resources/partials/admin/admin_visits.html', controller: 'AdminVisitsCtrl'}).
        when('/admin/forests', { templateUrl: 'resources/partials/admin/admin_forests.html', controller: 'AdminForestsCtrl'}).
        when('/admin/mushroomcounts', { templateUrl: 'resources/partials/admin/admin_mushroomcounts.html', controller: 'AdminMushroomCountsCtrl'}).
        when('/admin/mushrooms', { templateUrl: 'resources/partials/admin/admin_mushrooms.html', controller: 'AdminMushroomsCtrl'}).
        when('/login', { templateUrl: 'resources/partials/login.html', controller: 'LoginCtrl'}).
        otherwise({redirectTo: '/yourprofile'});

    }]);

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

portalControllers.controller('YourProfileCtrl', function ($scope, $http) {

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

portalControllers.controller('ForestsCtrl', function ($scope, $http) {

});

function loadAdminHunters($http, $scope) {
    $http.get('/pa165/rest/hunter/findall').then(function (response) {
        $scope.hunters = response.data;
        console.log('All hunters loaded');
    });
}

portalControllers.controller('AdminHuntersCtrl',
    function ($scope, $rootScope, $routeParams, $http) {

        loadAdminHunters($http,$scope);

        $scope.deleteHunter = function (hunter) {
            console.log("deleting hunter with id=" + hunter.id + ' (' + hunter.email + ')');
            $http.delete('/pa165/rest/hunter/' + hunter.id ).then(
                function success(response) {
                    console.log('deleted hunter ' + hunter.id + ' (' + hunter.email + ')  on server');
                    $rootScope.successAlert = 'Deleted hunter "' + hunter.email + '"';
                    loadAdminHunters($http, $scope);
                },
                function error(response) {
                    console.log("error when deleting hunter");
                    console.log(response);
                    switch (response.data.code) {
                        default:
                            $rootScope.errorAlert = 'Cannot delete hunter '+response.data.message;
                            break;
                    }
                }
            );
        };

});

portalControllers.controller('AdminForestsCtrl', function ($scope, $http) {

});

portalControllers.controller('AdminMushroomCountsCtrl', function ($scope, $http) {

});

portalControllers.controller('AdminVisitsCtrl', function ($scope, $http) {

});

portalControllers.controller('AdminMushroomsCtrl', function ($scope, $http) {

});

portalControllers.controller('LoginCtrl', function ($scope, $http) {

});




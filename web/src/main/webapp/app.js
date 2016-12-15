'use strict';

/**
 * Created by kisty on 14.12.16.
 */

var mushroomHunterApp = angular.module('mushroomHunterApp', ['ngRoute','portalControllers']);
var portalControllers = angular.module('portalControllers', []);
var ranks = [ 'BEGINNER', 'SKILLED', 'EXPERT', 'GURU'];
var roles = [ 'ADMIN', 'USER'];

mushroomHunterApp.config(['$routeProvider',
    function ($routeProvider) {

        adminHunterConfig($routeProvider);

        $routeProvider.
        when('/yourprofile', { templateUrl: 'resources/partials/your_profile.html', controller: 'YourProfileCtrl' }).
        when('/yourvisits', { templateUrl: 'resources/partials/your_visits.html', controller: 'YourVisitsCtrl' }).
        when('/yourcatches', { templateUrl: 'resources/partials/your_catches.html', controller: 'YourCatchesCtrl'}).
        when('/catches', { templateUrl: 'resources/partials/catches.html', controller: 'CatchesCtrl'}).
        when('/visits', { templateUrl: 'resources/partials/visits.html', controller: 'VisitsCtrl'}).
        when('/mushrooms', { templateUrl: 'resources/partials/mushrooms.html', controller: 'MushroomsCtrl'}).
        when('/forests', { templateUrl: 'resources/partials/forests.html', controller: 'ForestsCtrl'}).
        when('/admin/visits', { templateUrl: 'resources/partials/admin/admin_visits.html', controller: 'AdminVisitsCtrl'}).
        when('/admin/forests', { templateUrl: 'resources/partials/admin/admin_forests.html', controller: 'AdminForestsCtrl'}).
        when('/admin/mushroomcounts', { templateUrl: 'resources/partials/admin/admin_mushroomcounts.html', controller: 'AdminMushroomCountsCtrl'}).
        when('/admin/mushrooms', { templateUrl: 'resources/partials/admin/admin_mushrooms.html', controller: 'AdminMushroomsCtrl'}).
        when('/login', { templateUrl: 'resources/partials/login.html', controller: 'LoginCtrl'}).
        otherwise({redirectTo: '/yourprofile'});

    }]);


function adminHunterConfig($routeProvider) {
    $routeProvider.when('/admin/hunters', { templateUrl: 'resources/partials/admin/admin_hunters.html', controller: 'AdminHuntersCtrl'});
    $routeProvider.when('/admin/hunters/update/:hunterId', { templateUrl: 'resources/partials/forms/update_hunter.html', controller: 'AdminHunterUpdateCtrl'});
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




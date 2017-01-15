'use strict';

/**
 * Created by Erik Macej on 14.12.16.
 */

var mushroomHunterApp = angular.module('mushroomHunterApp', ['ngRoute', 'portalControllers', 'moment-picker']);
var portalControllers = angular.module('portalControllers', []);
var ranks = ['BEGINNER', 'SKILLED', 'EXPERT', 'GURU'];
var roles = ['ANONYMOUS', 'ADMIN', 'USER'];
var createRoles = ['ADMIN', 'USER'];
var types = ['EDIBLE', 'POISONOUS', 'NONEDIBLE', 'PSYCHEDELIC'];
var resourceUrl = 'resources/';

mushroomHunterApp.config(['$httpProvider', function ($httpProvider, LoginService) {
    initializeHttpInterceptors($httpProvider, LoginService);
}]);

mushroomHunterApp.run(function (LoginService, Utils) {
    LoginService.initialize();
    Utils.initalizeMessageHandlers();
});

mushroomHunterApp.config(['momentPickerProvider', function (momentPickerProvider) {
    momentPickerProvider.options({
        format: "YYYY-MM-DD",
        "min-view": "year",
        "max-view": "month",
        autoclose: true
    });
}]);

mushroomHunterApp.config(['$routeProvider',
    function ($routeProvider) {

        loginConfig($routeProvider);
        registerConfig($routeProvider);
        hunterConfig($routeProvider);
        forestConfig($routeProvider);
        mushroomConfig($routeProvider);
        visitConfig($routeProvider);
        mushroomsCountConfig($routeProvider);
        yourVisitsConfig($routeProvider);

        $routeProvider.otherwise({redirectTo: '/visits'});

    }
]);


function loginConfig($routeProvider) {
    $routeProvider.when('/login', {templateUrl: resourceUrl + 'partials/login.html', controller: 'LoginCtrl'})
}

function registerConfig($routeProvider) {
    $routeProvider.when('/signup', {templateUrl: resourceUrl + 'partials/register.html', controller: 'RegisterCtrl'})
}

function hunterConfig($routeProvider) {
    $routeProvider.when('/yourprofile', {
        templateUrl: 'resources/partials/your_profile.html',
        controller: 'YourProfileCtrl'
    });
    $routeProvider.when('/yourprofile/update', {
        templateUrl: 'resources/partials/forms/hunter/update_your_profile.html',
        controller: 'YourProfileUpdateCtrl'
    });
    $routeProvider.when('/hunters', {templateUrl: 'resources/partials/hunters.html', controller: 'HuntersCtrl'});
    $routeProvider.when('/hunters/update/:hunterId', {
        templateUrl: 'resources/partials/forms/hunter/update_hunter.html',
        controller: 'HunterUpdateCtrl'
    });
}

function forestConfig($routeProvider) {
    $routeProvider.when('/forests', {templateUrl: 'resources/partials/forests.html', controller: 'ForestsCtrl'});
    $routeProvider.when('/forests/newforest', {
        templateUrl: 'resources/partials/forms/forest/create_forest.html',
        controller: 'CreateForestCtrl'
    });
    $routeProvider.when('/forests/update/:forestId', {
        templateUrl: 'resources/partials/forms/forest/update_forest.html',
        controller: 'UpdateForestCtrl'
    });

}

function mushroomConfig($routeProvider) {
    $routeProvider.when('/mushrooms', {
        templateUrl: 'resources/partials/mushrooms.html',
        controller: 'MushroomsCtrl'
    }).when('/mushrooms/newmushroom', {
        templateUrl: 'resources/partials/forms/mushroom/create_mushroom.html',
        controller: 'CreateMushroomCtrl'
    }).when('/mushrooms/update/:mushroomId', {
        templateUrl: 'resources/partials/forms/mushroom/update_mushroom.html',
        controller: 'UpdateMushroomCtrl'
    });
}

function visitConfig($routeProvider) {
    $routeProvider.when('/visits', {
        templateUrl: 'resources/partials/visits.html',
        controller: 'VisitCtrl',
        resolve: {
            isYourVisit: falseFn
        }
    }).when('/visits/newvisit', {
        templateUrl: 'resources/partials/forms/visit/create_visit.html',
        controller: 'CreateVisitCtrl',
        resolve: {
            isYourVisit: falseFn
        }
    }).when('/visits/update/:visitId', {
        templateUrl: 'resources/partials/forms/visit/update_visit.html',
        controller: 'UpdateVisitCtrl',
        resolve: {
            isYourVisit: falseFn
        }
    });
}

function mushroomsCountConfig($routeProvider) {
    $routeProvider.when('/visits/update/:visitId/newmushroomscount', {
        templateUrl: 'resources/partials/forms/mushroom_count/create_mushroomscount.html',
        controller: 'CreateMushroomsCountCtrl',
        resolve: {
            isYourCatch: falseFn
        }
    }).when('/mushroomscount/update/:mushroomsCountId', {
        templateUrl: 'resources/partials/forms/mushroom_count/update_mushroomscount.html',
        controller: 'UpdateMushroomsCountCtrl',
        resolve: {
            isYourCatch: falseFn
        }
    });
}

function yourVisitsConfig($routeProvider) {
    $routeProvider.when('/yourvisits', {
        templateUrl: 'resources/partials/your_visits.html',
        controller: 'VisitCtrl',
        resolve: {
            isYourVisit: trueFn
        }
    });
    $routeProvider.when('/visits/yournewvisit', {
        templateUrl: 'resources/partials/forms/visit/create_your_visit.html',
        controller: 'CreateVisitCtrl',
        resolve: {
            isYourVisit: trueFn
        }
    });
    $routeProvider.when('/yourvisits/update/:visitId', {
        templateUrl: 'resources/partials/forms/visit/update_your_visit.html',
        controller: 'UpdateVisitCtrl',
        resolve: {
            isYourVisit: trueFn
        }
    });
    $routeProvider.when('/yourvisits/update/:visitId/newmushroomscount', {
        templateUrl: 'resources/partials/forms/mushroom_count/create_your_mushroomscount.html',
        controller: 'CreateMushroomsCountCtrl',
        resolve: {
            isYourCatch: trueFn
        }
    });
    $routeProvider.when('/yourmushroomscount/update/:mushroomsCountId', {
        templateUrl: 'resources/partials/forms/mushroom_count/update_your_mushroomscount.html',
        controller: 'UpdateMushroomsCountCtrl',
        resolve: {
            isYourCatch: trueFn
        }
    });
}

var falseFn = function () {
    return false;
};

var trueFn = function () {
    return true;
};

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
        templateUrl: 'resources/partials/forms/update_your_profile.html',
        controller: 'YourProfileUpdateCtrl'
    });
    $routeProvider.when('/hunters', {templateUrl: 'resources/partials/hunters.html', controller: 'HuntersCtrl'});
    $routeProvider.when('/hunters/update/:hunterId', {
        templateUrl: 'resources/partials/forms/update_hunter.html',
        controller: 'HunterUpdateCtrl'
    });
}

function forestConfig($routeProvider) {
    $routeProvider.when('/forests', {templateUrl: 'resources/partials/forests.html', controller: 'ForestsCtrl'});
    $routeProvider.when('/forests/newforest', {
        templateUrl: 'resources/partials/forms/create_forest.html',
        controller: 'CreateForestCtrl'
    });
    $routeProvider.when('/forests/update/:forestId', {
        templateUrl: 'resources/partials/forms/update_forest.html',
        controller: 'UpdateForestCtrl'
    });

}

function mushroomConfig($routeProvider) {
    $routeProvider.when('/mushrooms', {
        templateUrl: 'resources/partials/mushrooms.html',
        controller: 'MushroomsCtrl'
    }).when('/mushrooms/newmushroom', {
        templateUrl: 'resources/partials/forms/create_mushroom.html',
        controller: 'CreateMushroomCtrl'
    }).when('/mushrooms/update/:mushroomId', {
        templateUrl: 'resources/partials/forms/update_mushroom.html',
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
        templateUrl: 'resources/partials/forms/create_visit.html',
        controller: 'CreateVisitCtrl',
        resolve: {
            isYourVisit: falseFn
        }
    }).when('/visits/update/:visitId', {
        templateUrl: 'resources/partials/forms/update_visit.html',
        controller: 'UpdateVisitCtrl',
        resolve: {
            isYourVisit: falseFn
        }
    });
}

function mushroomsCountConfig($routeProvider) {
    $routeProvider.when('/visits/update/:visitId/newmushroomscount', {
        templateUrl: 'resources/partials/forms/create_mushroomscount.html',
        controller: 'CreateMushroomsCountCtrl',
        resolve: {
            isYourCatch: falseFn
        }
    }).when('/mushroomscount/update/:mushroomsCountId', {
        templateUrl: 'resources/partials/forms/update_mushroomscount.html',
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
        templateUrl: 'resources/partials/forms/my_create_visit.html',
        controller: 'CreateVisitCtrl',
        resolve: {
            isYourVisit: trueFn
        }
    });
    $routeProvider.when('/yourvisits/update/:visitId', {
        templateUrl: 'resources/partials/forms/update_your_visit.html',
        controller: 'UpdateVisitCtrl',
        resolve: {
            isYourVisit: trueFn
        }
    });
    $routeProvider.when('/yourvisits/update/:visitId/newmushroomscount', {
        templateUrl: 'resources/partials/forms/create_your_mushroomscount.html',
        controller: 'CreateMushroomsCountCtrl',
        resolve: {
            isYourCatch: trueFn
        }
    });
    $routeProvider.when('/yourmushroomscount/update/:mushroomsCountId', {
        templateUrl: 'resources/partials/forms/update_your_mushroomscount.html',
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

mushroomHunterApp.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.interceptors.push(function ($q, $rootScope) {
        return {
            'request': function (config) {
                if (config.url.indexOf(resourceUrl) != 0) { // not a resource
                    var token = $rootScope.user.accessToken;
                    if (token != null) {
                        config.headers['Authorization'] = 'Bearer ' + token;
                    }
                }
                // if interceptors caused the request to be fired again
                if (typeof config.alreadySetContextPath == 'undefined') {
                    config.alreadySetContextPath = true;
                    config.url = contextPath + "/" + config.url;
                }

                return config;
            }
        };
    });

    // intercept for oauth tokens
    $httpProvider.interceptors.push(['$rootScope', '$q', '$injector', '$location',
            function ($rootScope, $q, $injector, $location) {
                return {
                    response: function (response) {
                        return response; // no action, was successful
                    }, responseError: function (response) {
                        // error - was it 401 or something else?
                        if (response.status === 401 && response.data.error && response.data.error === "invalid_token") {
                            var deferred = $q.defer(); // defer until we can re-request a new token
                            $rootScope.user.accessToken = null;
                            // Get a new token... (cannot inject $http directly as will cause a circular ref)
                            $rootScope.login($injector.get("$http"), function (loginResponse) {
                                if (loginResponse.data) {
                                    $rootScope.oauth = loginResponse.data.oauth; // we have a new oauth token - set at $rootScope
                                    // now let's retry the original request - transformRequest in .run() below will add the new OAuth token
                                    $injector.get("$http")(response.config).then(function (response) {
                                        // we have a successful response - resolve it using deferred
                                        deferred.resolve(response);
                                    }, function (response) {
                                        deferred.reject(); // something went wrong
                                    });
                                } else {
                                    deferred.reject(); // login.json didn't give us data
                                }
                            }, function (response) {
                                deferred.reject(); // token retry failed, redirect so user can login again
                                $location.path('/login');
                            });
                            return deferred.promise; // return the deferred promise
                        }
                        return $q.reject(response); // not a recoverable error
                    }
                }
            }
        ]
    );
}]);

mushroomHunterApp.run(function ($rootScope, RestUtils) {
    $rootScope.clientCredentials = btoa('web-client:53ac618c-c8d2-44a1-b257-a2bd3816e829');

    $rootScope.user = {
        role: roles[0],
        id: null,
        email: null,
        password: null,
        accessToken: null,
        isLogged: function () {
            return this.id != null
        },
        logout: function () {
            this.role = roles[0];
            this.email = null;
            this.password = null;
            this.accessToken = null;
            this.id = null;
        },
        isEmailOfThisUserAltered: function (other) {
            return other.id == this.id && other.email != this.email;
        },
        isDeleted: function (other) {
            return other.id == this.id;
        }
    };

    $rootScope.login = function (http, successHandler, errorHandler) {
        http({
            method: 'POST',
            url: 'oauth/token',
            params: {
                grant_type: 'password',
                username: $rootScope.user.email,
                password: $rootScope.user.password
            },
            headers: {
                'Authorization': 'Basic ' + $rootScope.clientCredentials
            }
        }).then(function success(response) {
                if (response.data) {
                    $rootScope.user.id = response.data.userId;
                    $rootScope.user.role = response.data.role;
                    $rootScope.user.accessToken = response.data.access_token;
                }
                if (typeof errorHandler === "function") {
                    successHandler(response);
                }
            },
            function error(response) {
                if (typeof errorHandler === "function") {
                    errorHandler(response);
                }
            }
        )
    };

    $rootScope.prepareDate = function (date) {
        return RestUtils.convertDateToRest(date);
    };

    $rootScope.$on('$locationChangeStart', function () {
        $rootScope.hideSuccessAlert();
        $rootScope.hideWarningAlert();
        $rootScope.hideErrorAlert();
    });

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

function findHunterById($hunterId, $scope, $http) {
    $http.get('rest/hunter/' + $hunterId).then(function (response) {
        $scope.hunter = response.data;
    })
}

function loadForests($http, $scope) {
    $http.get('rest/forest/findall').then(function (response) {
        $scope.forests = response.data;
    });
}

function findForestById($forestId, $scope, $http) {
    $http.get('rest/forest/' + $forestId).then(function (response) {
        $scope.forest = response.data;
    })

}

function findForestByName($name, $scope, $http) {
    var forest;

    $http.get('rest/forest/find?name=' + $name).then(function (response) {
        forest = response.data;

        if (response.data) {
            $scope.forests = [forest];
        } else {
            $scope.forests = [];
        }

    });
}

/**************
 *  MUSHROOMS  *
 ***************/


function parseDates(mushroom_list) {
    var date;

    for (var i = 0, len = mushroom_list.length; i < len; i++) {

        if (!mushroom_list[i].fromDate) continue;

        date = mushroom_list[i].fromDate;
        mushroom_list[i].fromDate = date.replace("-", "/").substring(5, 10);

        if (!mushroom_list[i].toDate) continue;

        date = mushroom_list[i].toDate;
        mushroom_list[i].toDate = date.replace("-", "/").substring(5, 10);
    }

    return mushroom_list;
}

function loadMushrooms($http, $scope) {
    var mushroom_list;

    $http.get('rest/mushroom/findall').then(function (response) {
        mushroom_list = parseDates(response.data);
        if (response.data) {
            $scope.mushrooms = mushroom_list;

        } else {
            $scope.mushrooms = [];
        }
    });
}

function findMushroomById($mushroomId, $scope, $http) {
    $http.get('rest/mushroom/' + $mushroomId).then(function (response) {
        $scope.mushroom = response.data;
    })

};

function findMushroomByName($name, $scope, $http) {
    var mushroom_list;

    $http.get('rest/mushroom/find?name=' + $name).then(function (response) {
        mushroom_list = [response.data];
        mushroom_list = parseDates(mushroom_list);
        if (response.data) {
            $scope.mushrooms = mushroom_list;

        } else {
            $scope.mushrooms = [];
        }
    });
}

function findMushroomByType($type, $scope, $http) {
    var mushroom_list;

    $http.get('rest/mushroom/findbytype?type=' + $type).then(function (response) {
        mushroom_list = parseDates(response.data);
        if (response.data) {
            $scope.mushrooms = mushroom_list;

        } else {
            $scope.mushrooms = null;
        }
    });
}

function findMushroomByDate($date, $scope, $http, $rootScope) {
    var mushroom_list;
    var data = {};

    data.date = $rootScope.prepareDate($date);
    $http({
        method: 'POST',
        url: 'rest/mushroom/findbydate',
        data: data
    }).then(function (response) {
        mushroom_list = parseDates(response.data);
        if (response.data) {
            $scope.mushrooms = mushroom_list;
        } else {
            $scope.mushrooms = [];
        }
    })
}

function findMushroomByDateInterval($fromDate, $toDate, $scope, $http, $rootScope) {
    var mushroom_list;
    var data = {};

    data.from = $rootScope.prepareDate($fromDate);
    data.to = $rootScope.prepareDate($toDate);
    $http({
        method: 'POST',
        url: 'rest/mushroom/findbydateinterval',
        data: data
    }).then(function (response) {
        mushroom_list = parseDates(response.data);
        if (response.data) {
            $scope.mushrooms = mushroom_list;
        } else {
            $scope.mushrooms = [];
        }
    })
}

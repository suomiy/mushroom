'use strict';

/**
 * Created by Erik Macej on 14.12.16.
 */

var mushroomHunterApp = angular.module('mushroomHunterApp', ['ngRoute', 'portalControllers']);
var portalControllers = angular.module('portalControllers', []);
var ranks = ['BEGINNER', 'SKILLED', 'EXPERT', 'GURU'];
var roles = ['ANONYMOUS', 'ADMIN', 'USER'];
var resourceUrl = 'resources/';

mushroomHunterApp.config(['$routeProvider',
    function ($routeProvider) {

        loginConfig($routeProvider);
        forestConfig($routeProvider);
        hunterConfig($routeProvider);
        registerConfig($routeProvider);

        $routeProvider.when('/yourvisits', {
            templateUrl: 'resources/partials/your_visits.html',
            controller: 'YourVisitsCtrl'
        }).when('/yourcatches', {
            templateUrl: 'resources/partials/your_catches.html',
            controller: 'YourCatchesCtrl'
        }).when('/catches', {
            templateUrl: 'resources/partials/catches.html',
            controller: 'CatchesCtrl'
        }).when('/visits', {
            templateUrl: 'resources/partials/visits.html',
            controller: 'VisitsCtrl'
        }).when('/mushrooms', {
            templateUrl: 'resources/partials/mushrooms.html',
            controller: 'MushroomsCtrl'
        }).when('/admin/visits', {
            templateUrl: 'resources/partials/admin/admin_visits.html',
            controller: 'AdminVisitsCtrl'
        }).when('/admin/mushroomcounts', {
            templateUrl: 'resources/partials/admin/admin_mushroomcounts.html',
            controller: 'AdminMushroomCountsCtrl'
        }).when('/admin/mushrooms', {
            templateUrl: 'resources/partials/admin/admin_mushrooms.html',
            controller: 'AdminMushroomsCtrl'
        }).otherwise({redirectTo: '/visits'});

    }]);

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

function loginConfig($routeProvider) {
    $routeProvider.when('/login', {templateUrl: resourceUrl + 'partials/login.html', controller: 'LoginCtrl'})
}

function registerConfig($routeProvider) {
    $routeProvider.when('/signup', {templateUrl: resourceUrl + 'partials/register.html', controller: 'RegisterCtrl'})
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

mushroomHunterApp.run(function ($rootScope) {
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

function findHunterById($hunterId, $scope, $http) {
    $http.get('rest/hunter/' + $hunterId).then(function (response) {
        $scope.hunter = response.data;
        console.log('loaded hunter ' + $scope.hunter.id + ' (' + $scope.hunter.email + ')');
    })

}

function loadHunters($http, $scope) {
    $http.get('rest/hunter/findall').then(function (response) {
        $scope.hunters = response.data;
        console.log('All hunters loaded');
    });
}

function findHunterByEmail($email, $scope, $http) {
    var hunter;

    $http.get('rest/hunter/findbyemail?email=' + $email).then(function (response) {
        hunter = response.data;

        if (response.data) {
            $scope.hunters = [hunter];
            console.log('Hunter with email' + hunter.email + 'loaded');
        } else {
            $scope.hunters = [];
            console.log('Hunter with email doesn t exists');
        }

    });
}

function loadForests($http, $scope) {
    $http.get('rest/forest/findall').then(function (response) {
        $scope.forests = response.data;
        console.log('All forests loaded');
    });
}

function findForestById($forestId, $scope, $http) {
    $http.get('rest/forest/' + $forestId).then(function (response) {
        $scope.forest = response.data;
        console.log('Forest with name' + $scope.forest.name + 'loaded');
    })

}

function findForestByName($name, $scope, $http) {
    var forest;

    $http.get('rest/forest/find?name=' + $name).then(function (response) {
        forest = response.data;

        if (response.data) {
            $scope.forests = [forest];
            console.log('Forest with name' + forest.name + 'loaded');
        } else {
            $scope.forests = [];
            console.log('Forest with name doesn t exists');
        }

    });
}




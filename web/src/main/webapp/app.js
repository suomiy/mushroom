'use strict';

/**
 * Created by Erik Macej on 14.12.16.
 */

var mushroomHunterApp = angular.module('mushroomHunterApp', ['ngRoute', 'portalControllers']);
var portalControllers = angular.module('portalControllers', []);
var ranks = [ 'BEGINNER', 'SKILLED', 'EXPERT', 'GURU'];
var roles = [ 'ANONYMOUS', 'ADMIN', 'USER'];
var createRoles = ['ADMIN', 'USER'];
var types = [ 'EDIBLE', 'POISONOUS', 'NONEDIBLE', 'PSYCHEDELIC' ];
var resourceUrl = 'resources/';

mushroomHunterApp.service('sharedData', function() {
    var visitId;
    var hunters = [];
    var mushrooms = [];
    var forests = [];

    var setForests = function(o) {
        forests = o;
    }

    var getForests = function(o) {
        return forests;
    }

    var setHunters = function(o) {
        hunters = o;
    }

    var getHunters = function(o) {
        return hunters;
    }

    var setMushrooms = function(o) {
        mushrooms = o;
    }

    var getMushrooms = function(o) {
        return mushrooms;
    }
    var setVisitId = function(o) {
        visitId = o;
    };

    var getVisitId = function(){
        return visitId;
    };

    return {
        setForests: setForests,
        getForests: getForests,
        setHunters: setHunters,
        getHunters: getHunters,
        setMushrooms: setMushrooms,
        getMushrooms: getMushrooms,
        setVisitId: setVisitId,
        getVisitId: getVisitId
    };

});

mushroomHunterApp.config(['$routeProvider',
    function ($routeProvider) {

        loginConfig($routeProvider);
        forestConfig($routeProvider);
        hunterConfig($routeProvider);
        mushroomConfig($routeProvider);
        visitConfig($routeProvider);
        mushroomsCountConfig($routeProvider);
        yourVisitsConfig($routeProvider);
        registerConfig($routeProvider);

        $routeProvider.otherwise({redirectTo: '/visits'});

    }
 ]);

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

function yourVisitsConfig($routeProvider) {
    $routeProvider.when('/yourvisits', { templateUrl: 'resources/partials/your_visits.html', controller: 'YourVisitsCtrl' });
    $routeProvider.when('/visits/yournewvisit', { templateUrl: 'resources/partials/forms/my_create_visit.html', controller: 'CreateYourVisitCtrl' });
    $routeProvider.when('/yourvisits/update/:visitId', { templateUrl: 'resources/partials/forms/update_your_visit.html', controller: 'UpdateYourVisitCtrl'});
    $routeProvider.when('/yourmushroomscount/newmushroomscount', { templateUrl: 'resources/partials/forms/create_your_mushroomscount.html', controller: 'CreateMushroomsCountCtrl'});
    $routeProvider.when('/yourmushroomscount/update/:mushroomsCountId', { templateUrl: 'resources/partials/forms/update_your_mushroomscount.html', controller: 'UpdateMushroomsCountCtrl'});

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

/**************
*  MUSHROOMS  *
***************/

function mushroomConfig($routeProvider) {
    console.log("provider routes to MushroomsCtrl");
    $routeProvider.when('/mushrooms', { templateUrl: 'resources/partials/mushrooms.html', controller: 'MushroomsCtrl'}).
    when('/mushrooms/newmushroom', { templateUrl: 'resources/partials/forms/create_mushroom.html', controller: 'CreateMushroomCtrl'}).
    when('/mushrooms/update/:mushroomId', { templateUrl: 'resources/partials/forms/update_mushroom.html', controller: 'UpdateMushroomCtrl'});
}

function parseDates(mushroom_list) {
    var date;

    for (var i = 0, len = mushroom_list.length; i < len; i++) {

        if(!mushroom_list[i].fromDate) continue;

        date = mushroom_list[i].fromDate;
        mushroom_list[i].fromDate = date.replace("-","/").substring(5,10);

        if(!mushroom_list[i].toDate) continue;

        date = mushroom_list[i].toDate;
        mushroom_list[i].toDate = date.replace("-","/").substring(5,10);
    }

    return mushroom_list;
}

function loadMushrooms($http, $scope) {
    var mushroom_list;

    $http.get('rest/mushroom/findall').then(function (response) {
        mushroom_list = parseDates(response.data);
        if(response.data) {
            $scope.mushrooms = mushroom_list;

        }else{
            $scope.mushrooms = [];
        }
    });
}

function findMushroomById($mushroomId, $scope, $http) {
    $http.get('rest/mushroom/' + $mushroomId).then(function (response) {
        $scope.mushroom = response.data;
        console.log($scope.mushroom);
        console.log('mushroom with name' + $scope.mushroom.name + 'loaded');
    })

};

function findMushroomByName($name, $scope, $http) {
    var mushroom_list;

    $http.get('rest/mushroom/find?name=' + $name).then(function (response) {
        mushroom_list = [response.data];
        mushroom_list = parseDates(mushroom_list);
        if(response.data) {
            $scope.mushrooms = mushroom_list;

        }else{
            $scope.mushrooms = [];
        }
    });
}

function findMushroomByType($type, $scope, $http) {
    var mushroom_list;

    $http.get('rest/mushroom/findbytype?type=' + $type).then(function (response) {
        mushroom_list = response.data;
        mushroom_list = parseDates(response.data);
        if(response.data) {
            $scope.mushrooms = mushroom_list;

        }else{
            $scope.mushrooms = null;
        }
    });
}

function findMushroomByDate($date, $scope, $http) {
    var mushroom_list;
    var data = {};

    data.date = $date;
    $http({
        method: 'POST',
        url: 'rest/mushroom/findbydate',
        data: data
    }).then(function (response){
        mushroom_list = parseDates(response.data);
        if(response.data) {
            $scope.mushrooms = mushroom_list;
        }else{
            $scope.mushrooms = [];
        }
    })
}

function findMushroomByDateInterval($fromDate, $toDate, $scope, $http) {
    var mushroom_list;
    var data = {};

    data.from = $fromDate.value;
    data.to = $toDate.value;
    $http({
        method: 'POST',
        url: 'rest/mushroom/findbydateinterval',
        data: data
    }).then(function (response){
        mushroom_list = parseDates(response.data);
        if(response.data) {
            $scope.mushrooms = mushroom_list;
        }else{
            $scope.mushrooms = [];
        }
    })
}

/*************
*   VISITS   *
**************/


function visitConfig($routeProvider) {
    console.log("provider routes to VisitCtrl");
    $routeProvider.when('/visits', { templateUrl: 'resources/partials/visits.html', controller: 'VisitCtrl'}).
    when('/visits/newvisit', { templateUrl: 'resources/partials/forms/create_visit.html', controller: 'CreateVisitCtrl'}).
    when('/visits/update/:visitId', { templateUrl: 'resources/partials/forms/update_visit.html', controller: 'UpdateVisitCtrl'});
}

function loadVisits($http, $scope, updateVisitId = false, sharedData) {

    var completed_requests = 0;
    $http.get('rest/visit/findall').then(function (response) {
        if(response.data) {
            $scope.visits = response.data;
        }else{
            $scope.visits = [];
        }
        completed_requests++;
        if(completed_requests == 4){
            if(updateVisitId)  findVisitById(updateVisitId, $scope, $http);
            else completeVisits($scope);
        }
    });

    $http.get('rest/hunter/findall').then(function (response) {
        if(response.data) {
            $scope.hunters = response.data;
        }else{
            $scope.hunters = [];
        }
        completed_requests++;
        if(completed_requests == 4){
            if(updateVisitId)  findVisitById(updateVisitId, $scope, $http);
            else completeVisits($scope);
        }
    });

    $http.get('rest/forest/findall').then(function (response) {
        if(response.data) {
            $scope.forests = response.data;

        }else{
            $scope.forests = [];
        }
        completed_requests++;
        if(completed_requests == 4){
            if(updateVisitId)  findVisitById(updateVisitId, $scope, $http);
            else completeVisits($scope);
        }
    });

    $http.get('rest/mushroom/findall').then(function (response) {
        if(response.data) {
            $scope.mushrooms = parseDates(response.data);
        }else{
            $scope.mushrooms = [];
        }
        completed_requests++;
        if(completed_requests == 4){
            if(updateVisitId)  {
                if(sharedData) sharedData.setMushrooms($scope.mushrooms);
                findVisitById(updateVisitId, $scope, $http);
            } else completeVisits($scope);
        }
    });

}

function completeVisits($scope){

    for(var i=0, len = $scope.visits.length; i < len; i++){
        for(var j=0, leng= $scope.hunters.length; j < leng; j++){
            if($scope.visits[i].hunterId == $scope.hunters[j].id){
                $scope.visits[i].hunter = $scope.hunters[j];
            }
        }

        for(var j=0, leng= $scope.forests.length; j < leng; j++){
            if($scope.visits[i].forestId == $scope.forests[j].id){
                $scope.visits[i].forest = $scope.forests[j];
            }
        }

        for(var j=0, leng= $scope.visits[i].mushroomsCount.length; j < leng; j++){
            for(var k=0, lengt= $scope.mushrooms.length; k < lengt; k++){
                if($scope.visits[i].mushroomsCount[j].mushroomId == $scope.mushrooms[k].id){
                    $scope.visits[i].mushroomsCount[j].mushroom = $scope.mushrooms[k];
                }
            }
        }
    }
    console.log("Visit information complete:", $scope.visits);
}

function findVisitById(visitId, $scope, $http) {
    $http.get('rest/visit/' + visitId).then(function (response) {
        if(response.data) {
            $scope.visits = [response.data];
            $scope.visit = response.data
            completeVisits($scope);
            console.log('visit with id ' + visitId + ' loaded');
        }else{
            $scope.visit = [];
        }
    })
};

function findVisitByHunter($hunter, $scope, $http) {
    $http.get('rest/visit/findbyhunter?id=' + $hunter).then(function (response) {
        if(response.data) {
            $scope.visits = response.data;
            completeVisits($scope);
            console.log("Visits of hunter with id: "+$hunter+" loaded.");
        }else{
            $scope.visits = [];
        }
    })
};

function findVisitByForest($forest, $scope, $http, callFilterVisits = false, $rootScope) {
    $http.get('rest/visit/findbyforest?id=' + $forest).then(function (response) {
        if(response.data) {
            $scope.visits = response.data;
            completeVisits($scope);

            if(callFilterVisits) filterVisits($scope, $rootScope);

            console.log("Visits of forest with id: "+$forest+" loaded.");
        }else{
            $scope.visits = [];
        }
    })
};

function findVisitByMushroom($mushroom, $scope, $http,callFilterVisits = false, $rootScope) {
    $http.get('rest/visit/findbymushroom?id=' + $mushroom).then(function (response) {
        if(response.data) {
            $scope.visits = response.data;
            completeVisits($scope);

            if(callFilterVisits) filterVisits($scope, $rootScope);

            console.log("Visits where mushroom with id: "+$mushroom+" was found loaded.");
        }else{
            $scope.visits = [];
        }
    })
};

function findVisitByDate($date, $scope, $http,callFilterVisits = false, $rootScope) {
    var data = {};

    data.date = $date;
    $http({
        method: 'POST',
        url: 'rest/visit/findbydate',
        data: data
    }).then(function (response){
        if(response.data) {
            $scope.visits = response.data;
            completeVisits($scope);

            if(callFilterVisits) filterVisits($scope, $rootScope);

            console.log("Visits from date: "+$date+" loaded.");
        }else{
            $scope.visits = [];
        }
    })
}


/*********************
*   MUSHROOMSCOUNT   *
**********************/

function mushroomsCountConfig($routeProvider) {
    console.log("provider routes to MushroomsCountCtrl");
    $routeProvider.when('/mushroomscount/newmushroomscount', { templateUrl: 'resources/partials/forms/create_mushroomscount.html', controller: 'CreateMushroomsCountCtrl'}).
    when('/mushroomscount/update/:mushroomsCountId', { templateUrl: 'resources/partials/forms/update_mushroomscount.html', controller: 'UpdateMushroomsCountCtrl'});
}

function findMushroomsCountById(mushroomscountId, $scope, $http) {
    $http.get('rest/mushroomcount/' + mushroomscountId).then(function (response) {
        if(response.data) {
            $scope.mushroomsCounts = [response.data];
            $scope.mushroomscount = response.data;
            //completeVisits($scope);
            console.log('mushroomsCount with id ' + mushroomscountId + ' loaded');
        }else{
            $scope.mushroomsCounts = [];
        }
    })
};

/*******************
 *   YOUR_VISITS   *
 *******************/

function loadVisitsByHunter($http, $scope, $rootScope, updateVisitId = false, sharedData) {

    var completed_requests = 0;
    $http.get('rest/visit//findbyhunter?id=' + $rootScope.user.id).then(function (response) {
        if (response.data) {
            $scope.visits = response.data;
        } else {
            $scope.visits = [];
        }
        completed_requests++;
        if (completed_requests == 4) {
            if (updateVisitId)  findVisitById(updateVisitId, $scope, $http);
            else completeVisits($scope);
        }
    });

    $http.get('rest/hunter/findall').then(function (response) {
        if (response.data) {
            $scope.hunters = response.data;
        } else {
            $scope.hunters = [];
        }
        completed_requests++;
        if (completed_requests == 4) {
            if (updateVisitId)  findVisitById(updateVisitId, $scope, $http);
            else completeVisits($scope);
        }
    });

    $http.get('rest/forest/findall').then(function (response) {
        if (response.data) {
            $scope.forests = response.data;

        } else {
            $scope.forests = [];
        }
        completed_requests++;
        if (completed_requests == 4) {
            if (updateVisitId)  findVisitById(updateVisitId, $scope, $http);
            else completeVisits($scope);
        }
    });

    $http.get('rest/mushroom/findall').then(function (response) {
        if (response.data) {
            $scope.mushrooms = parseDates(response.data);
        } else {
            $scope.mushrooms = [];
        }
        completed_requests++;
        if (completed_requests == 4) {
            if (updateVisitId) {
                if (sharedData) sharedData.setMushrooms($scope.mushrooms);
                findVisitById(updateVisitId, $scope, $http);
            } else completeVisits($scope);
        }
    });

}

function filterVisits($scope, $rootScope) {
    var result = [];

    angular.forEach($scope.visits, function (value) {
        if(value.hunterId == $rootScope.user.id){
            result.push(value);
            console.log(result);
        }
    });
    $scope.visits = result;

}


function initializeHttpInterceptors($httpProvider) {
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
                            $rootScope.loginInternal($injector.get("$http"), function (loginResponse) {
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
}

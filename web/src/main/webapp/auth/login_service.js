mushroomHunterApp.service('LoginService', function ($http, $rootScope) {
    this.initialize = function () {
        $rootScope.clientCredentials = btoa('web-client:53ac618c-c8d2-44a1-b257-a2bd3816e829');
        $rootScope.loginInternal = this.loginInternal;

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
    };

    this.login = function (successHandler, errorHandler) {
        this.loginInternal($http, successHandler, errorHandler)
    };

    this.loginInternal = function (httpObject, successHandler, errorHandler) {
        httpObject({
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
});

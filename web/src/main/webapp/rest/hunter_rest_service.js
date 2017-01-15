mushroomHunterApp.service('HunterRestService', function ($http, RestUtils, RestErrorHandlers) {

    this.getRegisterPromise = function (hunter) {
        return $http({
            method: 'POST',
            url: 'rest/hunter/register',
            data: hunter
        });
    };

    this.getDeletePromise = function (id) {
        return $http.delete('rest/hunter/' + id);
    };

    this.getUpdatePromise = function (hunter) {
        return $http({
            method: 'POST',
            url: 'rest/hunter/update',
            data: hunter
        });
    };


    this.getFindAllPromise = function () {
        return $http.get('rest/hunter/findall');
    };

    this.getFindByIdPromise = function (id) {
        return $http.get('rest/hunter/' + id);
    };

    this.getFindByEmailPromise = function (email) {
        return $http({
            method: 'GET',
            url: 'rest/hunter/findbyemail',
            params: {
                email: email
            }
        });
    };

    this.setAll = function ($scope) {
        this.getFindAllPromise().then(function (values) {
                $scope.hunters = RestUtils.getData(values);
            }, RestErrorHandlers.makeRetrieveErrorHandler('hunters')
        );
    };

    this.setFindByEmail = function ($scope, email) {
        this.getFindByEmailPromise(email).then(function (values) {
                $scope.hunters = [RestUtils.getData(values)];
            }, RestErrorHandlers.makeRetrieveErrorHandler('hunters')
        );
    };

    this.setFindById = function ($scope, id) {
        this.getFindByIdPromise(id).then(function (values) {
                $scope.hunter = RestUtils.getData(values);
            }, RestErrorHandlers.makeRetrieveErrorHandler('hunter')
        );
    };
});

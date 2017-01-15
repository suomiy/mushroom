mushroomHunterApp.service('ForestRestService', function ($http, $rootScope, RestUtils, Utils, RestErrorHandlers) {

    this.getCreatePromise = function (forest) {
        return $http({
            method: 'POST',
            url: 'rest/forest/create',
            data: forest
        });
    };

    this.getDeletePromise = function (id) {
        return $http.delete('rest/forest/' + id);
    };

    this.getUpdatePromise = function (forest) {
        return $http({
            method: 'POST',
            url: 'rest/forest/update',
            data: forest
        });
    };

    this.getFindByIdPromise = function (id) {
        return $http.get('rest/forest/' + id);
    };

    this.getFindAllPromise = function () {
        return $http.get('rest/forest/findall');
    };

    this.getFindByNamePromise = function (name) {
        return $http({
            method: 'GET',
            url: 'rest/forest/find',
            params: {
                name: name
            }
        });
    };
    this.setById = function ($scope, id) {
        this.getFindByIdPromise(id).then(function (values) {
                $scope.forest = RestUtils.getData(values);
            }, RestErrorHandlers.makeRetrieveErrorHandler('forest')
        );
    };

    this.setAll = function ($scope) {
        this.getFindAllPromise().then(function (values) {
                $scope.forests = RestUtils.getData(values);
            }, RestErrorHandlers.makeRetrieveErrorHandler('forests')
        );
    };

    this.setByName = function ($scope, name) {
        this.getFindByNamePromise(name).then(function (values) {
                $scope.forests = [RestUtils.getData(values)];
            }, RestErrorHandlers.makeRetrieveErrorHandler('forests')
        );
    };
});

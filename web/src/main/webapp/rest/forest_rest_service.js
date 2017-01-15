mushroomHunterApp.service('ForestRestService', function ($http, $rootScope, RestUtils, Utils, RestErrorHandlers) {

    this.getFindAllPromise = function () {
        return $http.get('rest/forest/findall');
    };

    this.setAll = function ($scope) {
        this.getFindAllPromise().then(function (values) {
                $scope.forests = RestUtils.getData(values);
            }, RestErrorHandlers.makeRetrieveErrorHandler('forests')
        );
    };
});

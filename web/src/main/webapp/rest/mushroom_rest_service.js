mushroomHunterApp.service('MushroomRestService', function ($http, $rootScope, RestUtils, Utils, RestErrorHandlers) {

    this.getCreatePromise = function (mushroom) {
        return $http({
            method: 'POST',
            url: 'rest/mushroom/create',
            data: mushroom
        });
    };

    this.getDeletePromise = function (id) {
        return $http.delete('rest/mushroom/' + id);
    };

    this.getUpdatePromise = function (mushroom) {
        return $http({
            method: 'POST',
            url: 'rest/mushroom/update',
            data: mushroom
        });
    };

    this.getFindByIdPromise = function (id) {
        return $http.get('rest/mushroom/' + id);
    };

    this.getFindAllPromise = function () {
        return $http.get('rest/mushroom/findall');
    };

    this.getFindByNamePromise = function (name) {
        return $http({
            method: 'GET',
            url: 'rest/mushroom/find',
            params: {
                name: name
            }
        });
    };

    this.getFindByTypePromise = function (type) {
        return $http({
            method: 'GET',
            url: 'rest/mushroom/findbytype',
            params: {
                type: type
            }
        });
    };

    this.getFindByDatePromise = function (date) {
        return $http({
            method: 'POST',
            url: 'rest/mushroom/findbydate',
            data: {
                date: RestUtils.convertDateToRest(date)
            }
        });
    };

    this.getFindByDateIntervalPromise = function (from, to) {
        return $http({
            method: 'POST',
            url: 'rest/mushroom/findbydateinterval',
            data: {
                from: RestUtils.convertDateToRest(from),
                to: RestUtils.convertDateToRest(to)
            }
        });
    };

    this.setById = function ($scope, id, callback) {
        this.getFindByIdPromise(id).then(function (values) {
                $scope.mushroom = RestUtils.getData(values)
                RestUtils.setFromToDateFromRest($scope.mushroom);
                Utils.runCallback(callback);
            }, RestErrorHandlers.makeRetrieveErrorHandler('mushroom')
        );
    };

    this.setAll = function ($scope) {
        this.getFindAllPromise().then(function (values) {
                $scope.mushrooms = RestUtils.setMushroomDatesFromRest(RestUtils.getData(values));
            }, RestErrorHandlers.makeRetrieveErrorHandler('mushrooms')
        );
    };

    this.setByName = function ($scope, name) {
        this.getFindByNamePromise(name).then(function (values) {
                $scope.mushrooms = RestUtils.setMushroomDatesFromRest([RestUtils.getData(values)]);
            }, RestErrorHandlers.makeRetrieveErrorHandler('mushrooms')
        );
    };

    this.setByType = function ($scope, type) {
        this.getFindByTypePromise(type).then(function (values) {
                $scope.mushrooms = RestUtils.setMushroomDatesFromRest(RestUtils.getData(values));
            }, RestErrorHandlers.makeRetrieveErrorHandler('mushrooms')
        );
    };

    this.setByDate = function ($scope, date) {
        this.getFindByDatePromise(date).then(function (values) {
                $scope.mushrooms = RestUtils.setMushroomDatesFromRest(RestUtils.getData(values));
            }, RestErrorHandlers.makeRetrieveErrorHandler('mushrooms')
        );
    };

    this.setByDateInterval = function ($scope, from, to) {
        this.getFindByDateIntervalPromise(from, to).then(function (values) {
                $scope.mushrooms = RestUtils.setMushroomDatesFromRest(RestUtils.getData(values));
            }, RestErrorHandlers.makeRetrieveErrorHandler('mushrooms')
        );
    };
});

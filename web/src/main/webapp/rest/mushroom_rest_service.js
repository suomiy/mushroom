mushroomHunterApp.service('MushroomRestService', function ($http, $rootScope, RestUtils, Utils, RestErrorHandlers) {

    this.getFindAllPromise = function () {
        return $http.get('rest/mushroom/findall');
    };

    this.setAll = function ($scope) {
        this.getFindAllPromise().then(function (values) {
                var mushrooms = RestUtils.getData(values);
                mushrooms.forEach(function (shroom) {
                    RestUtils.setFromToDateFromRest(shroom);
                    Utils.setShorterFromToDate(shroom);
                });

                $scope.mushrooms = mushrooms;
            }, RestErrorHandlers.makeRetrieveErrorHandler('mushrooms')
        );
    };
});

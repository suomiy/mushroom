mushroomHunterApp.service('CombinedRestService', function ($q, $http, $rootScope, Utils, RestUtils, RestErrorHandlers, VisitRestService,
                                                           HunterRestService, ForestRestService, MushroomRestService, MushroomCountRestService) {
    /**
     * Visits Data
     */
    this.setAllCompleteVisits = function ($scope, id) {
        var visitsPromise = id == null ? VisitRestService.getFindAllPromise() : VisitRestService.getFindByHunterIdPromise(id);
        this.setVisitsCommon($scope, visitsPromise, id);

    };

    this.setFindByForestVisits = function ($scope, id, forestId) {
        this.setVisitsCommon($scope, VisitRestService.getFindByForestIdPromise(forestId), id);
    };

    this.setFindByMushroomsVisits = function ($scope, id, mushroomId) {
        this.setVisitsCommon($scope, VisitRestService.getFindByMushroomIdPromise(mushroomId), id);
    };

    this.setFindByHunterVisits = function ($scope, id, hunterId) {
        this.setVisitsCommon($scope, VisitRestService.getFindByHunterIdPromise(hunterId), id);
    };

    this.setFindByDateVisits = function ($scope, id, date) {
        this.setVisitsCommon($scope, VisitRestService.getFindByDatePromise(RestUtils.convertDateToRest(date)), id);
    };

    this.setVisitsCommon = function ($scope, visitsPromise, id) {
        var findById = id != null;
        var hunterPromise = findById ? HunterRestService.getFindByIdPromise(id) : HunterRestService.getFindAllPromise();
        var forestPromise = ForestRestService.getFindAllPromise();
        var mushroomPromise = MushroomRestService.getFindAllPromise();

        $q.all([visitsPromise, hunterPromise, forestPromise, mushroomPromise]).then(function (values) {
                // initialize
                var visits = RestUtils.getData(values, 0);
                var hunterData = RestUtils.getData(values, 1);
                var hunterMap;
                if (!findById) {
                    $scope.hunters = hunterData;
                    hunterMap = RestUtils.getIdMap(hunterData);
                }
                $scope.forests = RestUtils.getData(values, 2);
                $scope.mushrooms = RestUtils.getData(values, 3);
                var forestMap = RestUtils.getIdMap($scope.forests);
                var mushroomMap = RestUtils.getIdMap($scope.mushrooms);

                // filter
                if (findById) {
                    visits = visits.filter(function (visit) {
                        return visit.hunterId == id
                    })
                }

                // format data
                visits.forEach(function (visit) {
                    RestUtils.setFromToDateFromRest(visit);

                    visit.hunter = findById ? hunterData : hunterMap[visit.hunterId];
                    visit.forest = forestMap[visit.forestId];
                    visit.totalMushroomCount = visit.mushroomsCount.map(function (c) {
                        return c.count;
                    }).reduce(Utils.add, 0);

                    visit.mushroomsCount.forEach(function (mc) {
                        mc.mushroom = mushroomMap[mc.mushroomId];
                    })
                });

                $scope.visits = visits;
            }, RestErrorHandlers.makeRetrieveErrorHandler('visits')
        );
    };

    /**
     * Create Visit Data
     */
    this.seCreateVisit = function ($scope, showHunters) {
        var forestPromise = ForestRestService.getFindAllPromise();
        var hunterPromise = HunterRestService.getFindAllPromise();
        var promises = showHunters ? [forestPromise, hunterPromise] : [forestPromise];

        $q.all(promises).then(function (values) {
            $scope.forests = RestUtils.getData(values, 0);

            if (showHunters) {
                $scope.hunters = RestUtils.getData(values, 1);
            }
        }, RestErrorHandlers.makeRetrieveErrorHandler('forests'));

    };

    /**
     * Update Visit Data
     */
    this.setUpdateVisit = function ($scope, id, showHunter, callback) {
        var visitPromise = VisitRestService.getFindByIdPromise(id);
        var forestPromise = ForestRestService.getFindAllPromise();
        var mushroomPromise = MushroomRestService.getFindAllPromise();

        visitPromise.then(function (result) {
            var visit = RestUtils.getData(result);
            var hunterPromise = HunterRestService.getFindByIdPromise(visit.hunterId);
            var promises = showHunter ?
                [forestPromise, mushroomPromise, hunterPromise] : [forestPromise, mushroomPromise];

            $q.all(promises).then(function (values) {
                    var forests = RestUtils.getData(values, 0);
                    var mushroomMap = RestUtils.getIdMap(RestUtils.getData(values, 1));
                    RestUtils.setFromToDateFromRest(visit);

                    if (showHunter) {
                        visit.hunter = RestUtils.getData(values, 2);
                    }

                    visit.mushroomsCount.forEach(function (mc) {
                        mc.mushroom = mushroomMap[mc.mushroomId];
                    });

                    $scope.visit = visit;
                    $scope.forests = forests;

                    Utils.runCallback(callback);
                }, RestErrorHandlers.makeRetrieveErrorHandler('visit')
            );
        }, RestErrorHandlers.makeRetrieveErrorHandler('visit'));
    };

    /**
     * Update Mushroom Count Data
     */
    this.setUpdateMushroomCount = function ($scope, id, callback) {
        var mushroomCountPromise = MushroomCountRestService.getFindByIdPromise(id);
        var mushroomsPromise = MushroomRestService.getFindAllPromise();
        $q.all([mushroomCountPromise, mushroomsPromise]).then(function (values) {
            $scope.mushroomCount = RestUtils.getData(values, 0);
            $scope.mushrooms = RestUtils.getData(values, 1);

            Utils.runCallback(callback);
        }, RestErrorHandlers.makeRetrieveErrorHandler('catch'));
    };
});

mushroomHunterApp.service('VisitRestService', function ($http) {

    this.getCreatePromise = function (visit) {
        return $http({
            method: 'POST',
            url: 'rest/visit/create',
            data: visit
        });
    };

    this.getDeletePromise = function (id) {
        return $http.delete('rest/visit/' + id);
    };

    this.getUpdatePromise = function (visit) {
        return $http({
            method: 'POST',
            url: 'rest/visit/update',
            data: visit
        });
    };

    this.getFindByIdPromise = function (id) {
        return $http.get('rest/visit/' + id);
    };

    this.getFindAllPromise = function () {
        return $http.get('rest/visit/findall');
    };

    this.getFindByForestIdPromise = function (id) {
        return $http({
            method: 'GET',
            url: 'rest/visit/findbyforest',
            params: {
                id: id
            }
        });
    };

    this.getFindByMushroomIdPromise = function (id) {
        return $http({
            method: 'GET',
            url: 'rest/visit/findbymushroom',
            params: {
                id: id
            }
        });
    };

    this.getFindByHunterIdPromise = function (id) {
        return $http({
            method: 'GET',
            url: 'rest/visit/findbyhunter',
            params: {
                id: id
            }
        });
    };

    this.getFindByDatePromise = function (date) {
        return $http({
            method: 'POST',
            url: 'rest/visit/findbydate',
            data: {
                date: date
            }
        });
    };

});

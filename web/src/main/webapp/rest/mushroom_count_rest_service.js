mushroomHunterApp.service('MushroomCountRestService', function ($http) {

    this.getCreatePromise = function (mushroomCount) {
        return $http({
            method: 'POST',
            url: 'rest/mushroomcount/create',
            data: mushroomCount
        });
    };

    this.getDeletePromise = function (id) {
        return $http.delete('rest/mushroomcount/' + id);
    };

    this.getFindByIdPromise = function (id) {
        return $http.get('rest/mushroomcount/' + id);
    };

    this.getUpdatePromise = function (mushroomCount) {
        return $http({
            method: 'POST',
            url: 'rest/mushroomcount/update',
            data: mushroomCount
        });
    };


});

mushroomHunterApp.service('RestUtils', function ($rootScope) {

    this.getData = function (result, index) {
        return index == undefined ? result.data : result[index].data;
    };

    this.getIdMap = function (data) {
        var result = {};
        data.forEach(function (object) {
            result[object.id] = object;
        });
        return result;
    };

    this.convertDateToRest = function (date) {
        return date == null ? null : date._i + " 00:00";
    };

    this.convertDateFromRestString = function (date) {
        return date == null ? null : date.substr(0, date.length - 6);
    };

    this.convertDateToMoment = function (date) {
        return moment(date);
    };

    this.setFromToDateToRest = function (object) {
        object.fromDate = this.convertDateToRest(object.fromDate);
        object.toDate = this.convertDateToRest(object.toDate);
    };

    this.setFromToDateFromRest = function (object) {
        object.fromDate = this.convertDateFromRestString(object.fromDate);
        object.toDate = this.convertDateFromRestString(object.toDate);
    };

    this.setFromToDateFromRest = function (object) {
        object.fromDate = this.convertDateFromRestString(object.fromDate);
        object.toDate = this.convertDateFromRestString(object.toDate);
    };

    this.setFromToDateAsMoment = function (object) {
        object.fromDate = this.convertDateToMoment(object.fromDate);
        object.toDate = this.convertDateToMoment(object.toDate);
    };
});

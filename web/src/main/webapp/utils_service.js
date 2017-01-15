mushroomHunterApp.service('Utils', function ($timeout, $rootScope) {
    this.add = function (a, b) {
        return a + b;
    };

    this.setShorterDate = function (date) {
        return date == null ? null : date.substring(5, 10).replace("-", "/");
    };

    this.setShorterFromToDate = function (object) {
        object.fromDate = this.setShorterDate(object.fromDate);
        object.toDate = this.setShorterDate(object.toDate);
    };

    this.assertFromToDates = function (object) {
        return object != null && new Date(object.fromDate).getTime() <= new Date(object.toDate).getTime();
    };

    this.runCallback = function (callback) {
        if (typeof callback === "function") {
            callback();
        }
    };

    var locationChangeTimeout = 200;

    this.showSuccessOnLocationChange = function (message) {
        $timeout(function () {
            $rootScope.successAlert = message;
        }, locationChangeTimeout);
    };

    this.showErrorOnLocationChange = function (message) {
        $timeout(function () {
            $rootScope.errorAlert = message;
        }, locationChangeTimeout);
    };

    this.showWarnOnLocationChange = function (message) {
        $timeout(function () {
            $rootScope.warningAlert = message;
        }, locationChangeTimeout);
    };

    this.showSuccess = function (message) {
        $rootScope.successAlert = message;
    };

    this.showError = function (message) {
        $rootScope.errorAlert = message;
    };

    this.showWarn = function (message) {
        $rootScope.warningAlert = message;
    };

});

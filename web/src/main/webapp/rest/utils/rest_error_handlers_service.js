mushroomHunterApp.service('RestErrorHandlers', function (Utils) {

    this.makeUpdateErrorHandler = function (objectName) {
        return this.makeGenericErrorHandler(objectName, 'Cannot update');
    };

    this.makeCreateErrorHandler = function (objectName) {
        return this.makeGenericErrorHandler(objectName, 'Cannot create');
    };

    this.makeDeleteErrorHandler = function (objectName) {
        return this.makeGenericErrorHandler(objectName, 'Cannot delete');
    };

    this.makeRetrieveErrorHandler = function (objectName) {
        return this.makeGenericErrorHandler(objectName, 'Error: retrieving');
    };

    this.makeGenericErrorHandler = function (objectName, operation) {
        return function error(response) {
            if (response.status == 401) {
                Utils.showError( 'You are not authorized!');
                return;
            }

            switch (response.data.code) {
                case 409:
                    Utils.showError(operation + ' ' + objectName);
                    break;
                default:
                    if (response.data.errors) {
                        angular.forEach(response.data.errors, function (value) {
                            Utils.showError(operation + ' ' + objectName + ': ' + value);
                        });
                    } else {
                        Utils.showError(operation + ' ' + objectName);
                    }
                    break;
            }
        }
    }
});

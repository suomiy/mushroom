mushroomHunterApp.service('RestErrorHandlers', function (Utils) {

    this.makeUpdateErrorHandler = function (objectName, conflictMessage) {
        return this.makeGenericErrorHandler(objectName, 'Cannot update', conflictMessage);
    };

    this.makeCreateErrorHandler = function (objectName, conflictMessage) {
        return this.makeGenericErrorHandler(objectName, 'Cannot create', conflictMessage);
    };

    this.makeDeleteErrorHandler = function (objectName, conflictMessage) {
        return this.makeGenericErrorHandler(objectName, 'Cannot delete', conflictMessage);
    };

    this.makeRetrieveErrorHandler = function (objectName, conflictMessage) {
        return this.makeGenericErrorHandler(objectName, 'Retrieving', conflictMessage);
    };

    this.makeGenericErrorHandler = function (objectName, operation, conflictMessage) {
        return function error(response) {
            if (response.status == 401) {
                Utils.showError('You are not authorized!');
                return;
            }

            if (response.data && response.data.code) {
                switch (response.data.code) {
                    case 409:
                        if (conflictMessage) {
                            Utils.showError(conflictMessage);
                        }
                        return;
                    default:
                        if (response.data.errors) {
                            angular.forEach(response.data.errors, function (value) {
                                Utils.showError(operation + ' ' + objectName + ': ' + value);
                            });
                        }
                        return;
                }
            }

            Utils.showError(operation + ' ' + objectName);
        }
    }
});

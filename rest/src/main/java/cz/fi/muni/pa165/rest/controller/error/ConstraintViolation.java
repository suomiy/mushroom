package cz.fi.muni.pa165.rest.controller.error;

import java.util.HashMap;
import java.util.Map;

/**
 * Source: http://www.ibm.com/support/knowledgecenter/SSEPEK_12.0.0/codes/src/tpc/db2z_sqlstatevalues.html
 *
 * @author Filip Krepinsky (410022) on 12/10/16
 */

public enum ConstraintViolation {
    SQL_STATE_23502(23502, "An insert or update value is null, but the column cannot contain null values."),
    SQL_STATE_23503(23503, "The insert or update value of a foreign key is invalid."),
    SQL_STATE_23504(23504, "The update or delete of a parent key is prevented by a NO ACTION update or delete rule."),
    SQL_STATE_23505(23505, "A violation of the constraint imposed by a unique index or a unique constraint occurred."),
    SQL_STATE_23506(23506, "A violation of a constraint imposed by an edit or validation procedure occurred."),
    SQL_STATE_23507(23507, "A violation of a constraint imposed by a field procedure occurred."),
    SQL_STATE_23508(23508, "A violation of a constraint imposed by the DDL Registration Facility occurred."),
    SQL_STATE_23509(23509, "The owner of the package has constrained its use to environments which do not include that of the application process."),
    SQL_STATE_23510(23510, "A violation of a constraint on the use of the command imposed by the RLST table occurred."),
    SQL_STATE_23511(23511, "A parent row cannot be deleted, because the check constraint restricts the deletion."),
    SQL_STATE_23512(23512, "The check constraint cannot be added, because the table contains rows that do not satisfy the constraint definition."),
    SQL_STATE_23513(23513, "The resulting row of the INSERT or UPDATE does not conform to the check constraint definition."),
    SQL_STATE_23515(23515, "The unique index could not be created or unique constraint added, because the table contains duplicate values of the specified key."),
    SQL_STATE_23522(23522, "The range of values for the identity column or sequence is exhausted."),
    SQL_STATE_23523(23523, "An invalid value has been provided for the SECURITY LABEL column."),
    SQL_STATE_23525(23525, "A violation of a constraint imposed by an XML values index occurred."),
    SQL_STATE_23526(23526, "An XML values index could not be created because the table data contains values that violate a constraint imposed by the index.");

    static Map<Integer, ConstraintViolation> map = new HashMap<>(values().length);

    static {
        for (ConstraintViolation violation : values()) {
            map.put(violation.errorCode, violation);
        }
    }

    int errorCode;
    String message;

    ConstraintViolation(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public static ConstraintViolation fromErrorCode(String errorCode) {
        Integer result;
        try {
            result = Integer.parseInt(errorCode);
        } catch (Exception x) {
            return null;
        }

        return map.get(result);
    }
}



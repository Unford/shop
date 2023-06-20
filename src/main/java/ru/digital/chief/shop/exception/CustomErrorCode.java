package ru.digital.chief.shop.exception;

import org.springframework.http.HttpStatus;

public enum CustomErrorCode {
    RESOURCE_NOT_FOUND(40401, "exception.resource.not.found.message", HttpStatus.NOT_FOUND),
    HANDLER_NOT_FOUND(40402, "exception.handler.not.found.message", HttpStatus.NOT_FOUND),
    CONSTRAINT_VIOLATION(40000, HttpStatus.BAD_REQUEST),
    TYPE_MISMATCH(40001, "exception.type.mismatch.message", HttpStatus.BAD_REQUEST),
    METHOD_NOT_SUPPORTED(40505, "exception.method.not.allowed.message", HttpStatus.METHOD_NOT_ALLOWED),
    CONFLICT_DELETE(40300, "exception.conflict.delete.operation.message", HttpStatus.CONFLICT),
    RESOURCE_ALREADY_EXIST(40909, "exception.resource.already.exist.message", HttpStatus.CONFLICT),
    INTERNAL_EXCEPTION(50000, "exception.internal.server.message", HttpStatus.INTERNAL_SERVER_ERROR),
    MESSAGE_NOT_READABLE(40001, "exception.message.not.readable.message", HttpStatus.BAD_REQUEST);
    //todo
    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    CustomErrorCode(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = "%s";
    }

    CustomErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    /**
     * This function returns the code of the current object
     *
     * @return The code of the error.
     */
    public int getCode() {
        return code;
    }

    /**
     * This function returns the HTTP status code of the response
     *
     * @return The HttpStatus object.
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * This function returns the message.
     *
     * @return The message variable is being returned.
     */
    public String getMessage() {
        return message;
    }
}

package sample.cafekiosk.spring.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record ApiResponse<T> (
        int code,
        HttpStatus status,
        String message,
        T data
) {
    public ApiResponse(HttpStatus status, String message, T data) {
        this(status.value(), status, message, data);
    }

    /**
     * General Perpose
     * @param httpStatus
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ApiResponse<T> of(HttpStatus httpStatus, T data) {
        return new ApiResponse<>(httpStatus, httpStatus.name(), data);
    }

    /**
     * Use For Error
     * @param httpStatus
     * @param message
     * @return
     * @param <T>
     */
    public static <T> ApiResponse<T> of(HttpStatus httpStatus, String message) {
        return new ApiResponse<>(httpStatus, message, null);
    }

    /**
     * Use For Custom
     * @param status
     * @param message
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }

    /**
     * Status OK
     * @param data
     * @return
     * @param <T>
     */
    public static <T> ApiResponse<T> ok(T data) {
        return of(HttpStatus.OK, data);
    }

}

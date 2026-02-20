package edu.eci.arsw.blueprints.model;

/**
 * Respuesta uniforme para todos los endpoints de la API.
 */
public record ApiResponse<T>(int code, String message, T data) {
    
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(200, "execute ok", data);
    }
    
    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(201, "created successfully", data);
    }
    
    public static <T> ApiResponse<T> accepted(T data) {
        return new ApiResponse<>(202, "accepted", data);
    }
    
    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<>(404, message, null);
    }
    
    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(400, message, null);
    }
}

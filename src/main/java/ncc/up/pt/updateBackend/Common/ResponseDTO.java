package ncc.up.pt.updateBackend.Common;

public class ResponseDTO {
    private String code;
    private String message;
    private String data;

    // Constructor
    public ResponseDTO(String code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // toString method (optional, for logging or debugging purposes)
    @Override
    public String toString() {
        return "ResponseDTO{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}

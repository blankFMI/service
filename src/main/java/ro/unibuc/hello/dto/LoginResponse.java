package ro.unibuc.hello.dto;

public class LoginResponse {
    private String token; // In a real application, this could be a JWT

    public LoginResponse() {}

    public LoginResponse(String token) {
        this.token = token;
    }

    // Getter and setter
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}

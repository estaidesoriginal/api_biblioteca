package biblioteca_G_v2.demo.dto;

import biblioteca_G_v2.demo.model.User;

public class AuthDTOs {
    
    public static class LoginRequest {
        public String email;
        public String password;
    }

    public static class RegisterRequest {
        public String name;
        public String email;
        public String password;
    }

    public static class AuthResponse {
        public User user;
        public String token;

        public AuthResponse(User user, String token) {
            this.user = user;
            this.token = token;
        }
    }
}
package org.meropasal.merogrocery.model;

public class UserModel {

    private String status, token, phone_number, password, otp_code, message, pin;
    private User user;

    public UserModel() {
    }

    public UserModel(String status, String token, String phone_number, String password, String otp_code, String message, String pin, User user) {
        this.status = status;
        this.token = token;
        this.phone_number = phone_number;
        this.password = password;
        this.otp_code = otp_code;
        this.message = message;
        this.pin = pin;
        this.user = user;
    }
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp_code() {
        return otp_code;
    }

    public void setOtp_code(String otp_code) {
        this.otp_code = otp_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    //    public String getOtp_code() {
//        return otp_code;
//    }
//
//    public void setOtp_code(String otp_code) {
//        this.otp_code = otp_code;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public String getPhone_number() {
//        return phone_number;
//    }
//
//    public void setPhone_number(String phone_number) {
//        this.phone_number = phone_number;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public static class User{
        private String id, name, email, role, phone_number;

        public User() {
        }

        public User(String id, String name, String email, String role, String phone_number) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
            this.phone_number = phone_number;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }
    }

}

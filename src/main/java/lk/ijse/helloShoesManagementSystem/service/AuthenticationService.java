package lk.ijse.helloShoesManagementSystem.service;

import lk.ijse.helloShoesManagementSystem.reqAndresp.response.JwtAuthResponse;
import lk.ijse.helloShoesManagementSystem.reqAndresp.secure.SignIn;
import lk.ijse.helloShoesManagementSystem.reqAndresp.secure.SignUp;

public interface AuthenticationService {

    JwtAuthResponse signIn(SignIn signIn);

    JwtAuthResponse signUp(SignUp signUp);

    JwtAuthResponse refreshToken(String accessToken);

}

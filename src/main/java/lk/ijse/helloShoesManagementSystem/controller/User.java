package lk.ijse.helloShoesManagementSystem.controller;

import lk.ijse.helloShoesManagementSystem.reqAndresp.response.JwtAuthResponse;
import lk.ijse.helloShoesManagementSystem.reqAndresp.secure.SignIn;
import lk.ijse.helloShoesManagementSystem.reqAndresp.secure.SignUp;
import lk.ijse.helloShoesManagementSystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class User {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthResponse> signUp(@RequestBody SignUp signUpReq) {
        return ResponseEntity.ok(authenticationService.signUp(signUpReq));
    }
    //signIn
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SignIn signInReq) {
        return ResponseEntity.ok(authenticationService.signIn(signInReq));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }

}

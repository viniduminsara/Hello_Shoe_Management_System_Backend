package lk.ijse.helloShoesManagementSystem.service.impl;

import lk.ijse.helloShoesManagementSystem.dto.UserDTO;
import lk.ijse.helloShoesManagementSystem.entity.EmployeeEntity;
import lk.ijse.helloShoesManagementSystem.entity.UserEntity;
import lk.ijse.helloShoesManagementSystem.entity.enums.Role;
import lk.ijse.helloShoesManagementSystem.exception.NotFoundException;
import lk.ijse.helloShoesManagementSystem.repository.EmployeeRepo;
import lk.ijse.helloShoesManagementSystem.repository.UserRepo;
import lk.ijse.helloShoesManagementSystem.reqAndresp.response.JwtAuthResponse;
import lk.ijse.helloShoesManagementSystem.reqAndresp.secure.SignIn;
import lk.ijse.helloShoesManagementSystem.reqAndresp.secure.SignUp;
import lk.ijse.helloShoesManagementSystem.service.AuthenticationService;
import lk.ijse.helloShoesManagementSystem.service.JWTService;
import lk.ijse.helloShoesManagementSystem.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo userRepo;
    private EmployeeRepo employeeRepo;
    private final JWTService jwtService;
    private Mapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        var userByEmail = userRepo.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var generatedToken = jwtService.generateToken(userByEmail);
        return JwtAuthResponse.builder().token(generatedToken).build();
    }

    @Override
    public JwtAuthResponse signUp(SignUp signUp) {
        EmployeeEntity employeeEntity = employeeRepo.findByEmail(signUp.getEmail())
                .orElseThrow(() -> new NotFoundException("Employee does not exists"));
        var buildUser = UserDTO.builder()
                .userId(UUID.randomUUID().toString())
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .role(Role.valueOf(signUp.getRole()))
                .build();
        UserEntity userEntity = mapper.toUserEntity(buildUser);
        userEntity.setEmployeeEntity(employeeEntity);
        var savedUser = userRepo.save(userEntity);
        var genToken = jwtService.generateToken(savedUser);
        return JwtAuthResponse.builder().token(genToken).build();
    }

    @Override
    public JwtAuthResponse refreshToken(String accessToken) {
        var userName = jwtService.extractUsername(accessToken);
        var userEntity = userRepo.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var refreshToken = jwtService.generateToken(userEntity);
        return JwtAuthResponse.builder().token(refreshToken).build();
    }
}

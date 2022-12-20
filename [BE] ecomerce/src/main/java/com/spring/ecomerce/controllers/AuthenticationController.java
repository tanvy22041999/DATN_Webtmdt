package com.spring.ecomerce.controllers;

import com.spring.ecomerce.arch.BaseResponseEntity;
import com.spring.ecomerce.dtos.clone.LoginUserDTO;
import com.spring.ecomerce.dtos.TokenDetails;
import com.spring.ecomerce.entities.clone.UserEntity;
import com.spring.ecomerce.exception.SystemException;
import com.spring.ecomerce.repositories.UserRepository;
import com.spring.ecomerce.securities.AccountDetailsService;
import com.spring.ecomerce.securities.JwtTokenUtils;
import com.spring.ecomerce.securities.JwtUserDetails;
import com.spring.ecomerce.securities.provider.AccountAuthenticationToken;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountDetailsService accountDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private BaseResponseEntity baseResponse;

    @ApiOperation("User login form (phone_number, password)")
    @PostMapping("/signin")
    public String loginUser(@Valid @RequestBody LoginUserDTO dto) throws SystemException {
        AccountAuthenticationToken authenticationToken = new AccountAuthenticationToken(
                dto.getPhonenumber(),
                dto.getPassword(),
                true
        );
        try{
            authenticationManager.authenticate(authenticationToken);
            final JwtUserDetails userDetails = accountDetailsService
                    .loadUserByUsername(dto.getPhonenumber());

            final TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails, null);

            UserEntity userLogin = result.getUserLogin();
            userLogin.setToken("Bearer " + result.getToken());
            userRepository.save(userLogin);

            baseResponse.retrieved();
            Map<String, Object> dataResponse = new HashMap<>();
            dataResponse.put("user", userLogin);
            return baseResponse.getResponseBody(dataResponse);
        }catch (Exception ex){
            baseResponse.failed(401, ex.getMessage());
        }
        return baseResponse.getResponseBody();
    }
}

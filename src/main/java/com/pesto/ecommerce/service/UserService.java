package com.pesto.ecommerce.service;


import com.pesto.ecommerce.models.request.LoginRequest;
import com.pesto.ecommerce.models.response.UserInfoResponse;

public interface UserService {

    String generateOtp(LoginRequest loginRequest);

    UserInfoResponse validateOtp(LoginRequest loginRequest);

}

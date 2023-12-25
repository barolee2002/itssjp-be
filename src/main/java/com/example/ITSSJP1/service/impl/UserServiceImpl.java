package com.example.ITSSJP1.service.impl;

import com.example.ITSSJP1.config.security.JwtService;
import com.example.ITSSJP1.dto.*;
import com.example.ITSSJP1.entity.Income;
import com.example.ITSSJP1.entity.Spending;
import com.example.ITSSJP1.entity.User;
import com.example.ITSSJP1.exception.AppException;
import com.example.ITSSJP1.exception.Errors;
import com.example.ITSSJP1.repository.IncomeRepository;
import com.example.ITSSJP1.repository.SpendingRepository;
import com.example.ITSSJP1.repository.UserRepository;
import com.example.ITSSJP1.service.UserService;
import com.example.ITSSJP1.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final IncomeRepository incomeRepo;
    private final SpendingRepository spendingRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${expire.time}")
    private long expireTime;

    @Override
    public Statistic statistic(Integer userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if (userOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        long incomeTotal = incomeRepo.findByUserId(userId).stream().mapToLong(Income::getAmount).sum();
        long spendingTotal = spendingRepo.findByUserId(userId).stream().mapToLong(Spending::getAmount).sum();
        return Statistic.builder()
                .incomeTotal(incomeTotal)
                .spendingTotal(spendingTotal)
                .savings(incomeTotal - spendingTotal)
                .build();
    }

    @Override
    public UserDTOResponse addUser(UserDTOResponse userDTO) {
        if( Utils.isEmptyOrNull(userDTO.getUserName()) || Utils.isEmptyOrNull(userDTO.getPassword()))
            throw new AppException(Errors.INVALID_DATA);
        if(userRepo.findFirstByUserName( userDTO.getUserName()) != null){
            throw new AppException(Errors.EXIST_USER);
        }
        ModelMapper mapper = new ModelMapper();
        User user = User.builder()
                .userName(userDTO.getUserName())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        return mapper.map(userRepo.save(user), UserDTOResponse.class);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
        authenticationManager.authenticate(token);
        String jwt = jwtService.generateToken(loginRequest.getUserName());
        Optional<User> userOptional = userRepo.findByUserName(loginRequest.getUserName());
        return LoginResponse.builder()
                .username(loginRequest.getUserName())
                .token(jwt)
                .expireTime(System.currentTimeMillis() + expireTime)
                .id(userOptional.get().getId())
                .build();
    }

    @Override
    public UserDTOResponse update(UserDTORequest userDTO, Integer userId) {
        ModelMapper mapper = new ModelMapper();
        Optional<User> userOptional = userRepo.findById(userId);
        if ( userOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        User user = userOptional.get();
        user.setUserName(Utils.isEmptyOrNull(userDTO.getUserName()) ? userDTO.getUserName() : user.getUserName());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setFullName(userDTO.getFullName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (Objects.nonNull(userDTO.getAvatarUrl())) {
            try {
                user.setAvatarUrl( userDTO.getAvatarUrl().getBytes() );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return mapper.map(userRepo.save(user), UserDTOResponse.class);
    }

    @Override
    public UserDTOResponse getInfo(Integer userId) {
        Optional<User> userOptional = userRepo.findById(userId);
        if(userOptional.isEmpty())
            throw new AppException(Errors.INVALID_DATA);
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userOptional.get(), UserDTOResponse.class);
    }


}

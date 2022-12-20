package com.spring.ecomerce.securities;

import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.entities.clone.UserEntity;
import com.spring.ecomerce.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    private MessageManager messageManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    public JwtUserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByPhonenumber(phoneNumber);

        if(user == null){
            throw new UsernameNotFoundException(String.format(messageManager.getMessage("ERROR_AUTHENTICATE", null),phoneNumber));
        }

        List<String> roles = new ArrayList<>();
        if("0".equals(user.getRole())){
            roles.add("0");
        }
        else if("1".equals(user.getRole())){
            roles.add("1");
        }

        return new JwtUserDetails(user,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }
}

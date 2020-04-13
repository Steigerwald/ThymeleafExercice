package com.mkyong.services;


import com.mkyong.entity.User;
import com.mkyong.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    Logger logger = (Logger) LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByMailUser(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + userName + " not found"));

        logger.info(" on est passe par la avant getAuthorities de loadUserByUsername");

        return new org.springframework.security.core.userdetails.User(user.getMailUser(), user.getMotDePasseUser(),
                getAuthorities(user));
    }

   private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles().stream().map((roles) -> roles.getNomRole()).toArray(String[]::new);
       Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
       return authorities;
    }


}


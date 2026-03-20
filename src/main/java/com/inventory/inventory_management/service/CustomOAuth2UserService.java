package com.inventory.inventory_management.service;

import com.inventory.inventory_management.entity.Role;
import com.inventory.inventory_management.entity.User;
import com.inventory.inventory_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        String provider=oAuth2UserRequest.getClientRegistration().getRegistrationId();

        String name=oAuth2User.getAttribute("name");
        String email=oAuth2User.getAttribute("email");

        if(userRepository.findByEmail(email)==null){
            User user=new User();
            user.setEmail(email);
            user.setUsername(name);
            user.setPassword("");
            user.setProvider(provider);
            user.setRole(Role.USER);
            userRepository.save(user);
        }
        return oAuth2User;
    }

}

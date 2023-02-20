package com.reeinvent.backend.client.user.service;

import com.reeinvent.backend.GRPCUser;
import com.reeinvent.backend.UserServiceGrpc;
import com.reeinvent.backend.client.user.UserChannel;
import com.reeinvent.backend.client.user.mapper.UserMapper;
import com.reeinvent.backend.client.user.models.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service(value = "userService")
public class UserService implements InitializingBean, UserDetailsService {

    @Autowired
    private UserChannel userChannel;

    private UserServiceGrpc.UserServiceBlockingStub blockingStub;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GRPCUser user = blockingStub.getUser(GRPCUser.newBuilder().setEmail(username).build());
        if(user == null){
            throw new UsernameNotFoundException("Invalid email or password.");
        }
        Set<GrantedAuthority> grantedAuthorities = getAuthorities(user);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

    private Set<GrantedAuthority> getAuthorities(GRPCUser user) {
        return user.getRolesList().stream().map(role ->
                new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase())).collect(Collectors.toSet());
    }

    public User createUser(User request) {
        GRPCUser response = blockingStub.createUser(UserMapper.toGRPCUser(request));
        return UserMapper.toUser(response);
    }

    @Override
    public void afterPropertiesSet() {
        blockingStub = UserServiceGrpc.newBlockingStub(userChannel.getChannel());
    }
}

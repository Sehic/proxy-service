package com.reeinvent.backend.client.user.mapper;

import com.reeinvent.backend.GRPCRole;
import com.reeinvent.backend.GRPCUser;
import com.reeinvent.backend.client.user.models.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static GRPCUser toGRPCUser(User user) {
        return GRPCUser.newBuilder()
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .addAllRoles(user.getRole().stream().map(UserMapper::toGRPCRole).collect(Collectors.toList()))
                .build();
    }

    private static GRPCRole toGRPCRole(String role) {
        return GRPCRole.newBuilder().setName(role).build();
    }


    public static User toUser(GRPCUser grpcUser) {
        User user = new User();
        user.setEmail(grpcUser.getEmail());
        user.setPassword(grpcUser.getPassword());
        user.setRole(grpcUser.getRolesList().stream().map(GRPCRole::getName).collect(Collectors.toList()));
        return user;
    }
}

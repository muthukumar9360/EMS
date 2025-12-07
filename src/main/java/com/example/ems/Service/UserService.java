package com.example.ems.Service;

import com.example.ems.Model.User;
import com.example.ems.Model.Role;
import java.util.List;

public interface UserService {

    User registerUser(String fullName, String username, String password);

    User createUserWithRoles(User user, List<Role> roles);

    User findByUsername(String username);

    boolean existsByUsername(String username);
}

package yte.etkinlikyonetim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import yte.etkinlikyonetim.entity.User;
import yte.etkinlikyonetim.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsManager implements UserDetailsManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails userDetails) {
        User user = (User) userDetails;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        User oldUser = (User) loadUserByUsername(userDetails.getUsername());
        User newUser = (User) userDetails;
        newUser.setId(oldUser.getId());
        userRepository.save(newUser);

    }

    @Override
    public void deleteUser(String s) {
        userRepository.deleteByUsername(s);
    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String s) {
        return userRepository.existsByUsername(s);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }
}

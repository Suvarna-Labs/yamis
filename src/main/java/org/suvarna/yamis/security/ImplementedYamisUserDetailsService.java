package org.suvarna.yamis.security;

import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.suvarna.yamis.entity.Privilege;
import org.suvarna.yamis.entity.Role;
import org.suvarna.yamis.entity.User;
import org.suvarna.yamis.repository.UserRepository;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImplementedYamisUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public ImplementedYamisUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ImplementedYamisUserDetails userDetails = new ImplementedYamisUserDetails();
        userRepository.findWithFullAuthoritiesByUsername(username).ifPresentOrElse((Consumer<? super User>) user -> {
            userDetails.setUsername(user.getUsername());
            userDetails.setPassword(user.getPassword());
            userDetails.setAccountNonExpired(user.isActive());
            userDetails.setEnabled(user.isEnabled());
            userDetails.setAuthorities(getAuthorities(user));
        },() -> {throw new UsernameNotFoundException("User Not found");});
        return userDetails;
    }

    private Set<String> getAuthorities(User user) {
        return user.getGroups().stream()
                .flatMap(group -> Stream.concat(
                        Stream.of(group.getGroupName()),
                        Stream.concat(
                                group.getRoles().stream()
                                        .map(Role::getRoleName),
                                group.getRoles().stream()
                                        .flatMap(role -> role.getPrivileges().stream())
                                        .map(Privilege::getPrivilegeName)
                        )
                ))
                .collect(Collectors.toSet());
    }
}

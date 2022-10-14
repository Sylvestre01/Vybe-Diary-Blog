package sylvestre01.vybediaryblog.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.repository.UserRepository;
import sylvestre01.vybediaryblog.service.CustomUserDetailsService;

import javax.transaction.Transactional;
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with this username or email: %s", usernameOrEmail)));
        UserPrincipal userDetails = null;
        if (user != null) {
            userDetails = new UserPrincipal(user);
        }
        return userDetails;
    }

    @Override
    public UserDetails loadUserById(Long id) {
        return null;
    }
}

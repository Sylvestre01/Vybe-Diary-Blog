package sylvestre01.vybediaryblog.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.exception.ResourceNotFoundException;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.repository.UserRepository;

@Service
public class UserPrincipalService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserPrincipalService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("username could not be found"));
        UserPrincipal userPrincipal = null;
        if(user != null) {
            userPrincipal = new UserPrincipal(user);
        }

        return userPrincipal;
    }
}

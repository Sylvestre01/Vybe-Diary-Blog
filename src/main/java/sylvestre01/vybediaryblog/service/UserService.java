package sylvestre01.vybediaryblog.service;

import org.springframework.stereotype.Service;
import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.payload.*;

@Service
public interface UserService {

    UserSummary getCurrentUser(UserPrincipal currentUser);

    User addUser(User user);

    ApiResponse deleteUser(String username, UserPrincipal currentUser);

    User updateUser(User newUser, String username, UserPrincipal currentUser);

    UserIdentityAvailability checkUsernameAvailability(String username);

    UserIdentityAvailability checkEmailAvailability(String email);

    UserProfile getUserProfile(String username);

    ApiResponse giveAdmin(String username);

    ApiResponse removeAdmin(String username);

    UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest);



}

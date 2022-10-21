package sylvestre01.vybediaryblog.service;

import sylvestre01.vybediaryblog.Security.UserPrincipal;
import sylvestre01.vybediaryblog.model.user.User;
import sylvestre01.vybediaryblog.payload.*;
import sylvestre01.vybediaryblog.response.ApiResponse;

public interface UserService {

    UserSummaryPayload getCurrentUser(UserPrincipal currentUser);

    User addUser(UserPayload user);

    ApiResponse deleteUser(String username, UserPrincipal currentUser);

    User updateUser(User newUser, String username, UserPrincipal currentUser);

    UserIdentityAvailability checkUsernameAvailability(String username);

    UserIdentityAvailability checkEmailAvailability(String email);

    UserProfilePayload getUserProfile(String username);

    ApiResponse giveAdmin(String username);

    ApiResponse removeAdmin(String username);

    UserProfilePayload setOrUpdateInfo(UserPrincipal currentUser, InfoPayload infoRequest);

}

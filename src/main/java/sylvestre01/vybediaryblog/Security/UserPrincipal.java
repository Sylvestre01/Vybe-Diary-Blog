package sylvestre01.vybediaryblog.Security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sylvestre01.vybediaryblog.model.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserPrincipal implements UserDetails {

    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private List<GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String userAuthority : user.getRole().toString().split(",")) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userAuthority);
            grantedAuthorities.add(simpleGrantedAuthority);}
        this.authorities = grantedAuthorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        UserPrincipal that = (UserPrincipal) object;
        return Objects.equals(id, that.id);
    }

    public int hashCode() {
        return Objects.hash(id);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}


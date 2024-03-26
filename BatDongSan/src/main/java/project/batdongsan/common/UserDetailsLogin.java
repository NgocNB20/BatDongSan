package project.batdongsan.common;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import project.batdongsan.model.entity.UserEntity;

import java.util.Collection;

@Getter
public class UserDetailsLogin extends User {
    private static final long serialVersionUID = 1L;

    private UserEntity userEntity;

    public UserDetailsLogin(UserEntity userEntity) {

        super(userEntity.getUsername(), userEntity.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
        this.userEntity = userEntity;
    }
}

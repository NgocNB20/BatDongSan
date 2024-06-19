package project.batdongsan.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import java.util.Date;

@Entity
@Table(name = "persistent_logins")
@Data
public class PersistentLogin {


    @Id
    private String series;
    private String username;
    private String token;
    @Column(name = "last_used")
    private Date lastUse;

    public PersistentLogin() {

    }
    public PersistentLogin(PersistentRememberMeToken token) {
        this.series = token.getSeries();
        this.username = token.getUsername();
        this.token = token.getTokenValue();
        this.lastUse = token.getDate();
    }

}

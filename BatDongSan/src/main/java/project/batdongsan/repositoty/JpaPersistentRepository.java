package project.batdongsan.repositoty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import project.batdongsan.model.entity.PersistentLogin;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public class JpaPersistentRepository implements PersistentTokenRepository   {
     private final RememberMeTokenRepository rememberMeTokenRepository;

    public JpaPersistentRepository(RememberMeTokenRepository rememberMeTokenRepository) {
        this.rememberMeTokenRepository = rememberMeTokenRepository;
    }


    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistentLogin persistentLogin = new PersistentLogin(token);
        rememberMeTokenRepository.save(persistentLogin);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        Optional<PersistentLogin> persistentLoginOtp = rememberMeTokenRepository.findBySeries(series);
        if (persistentLoginOtp.isPresent()) {
            PersistentLogin persistentLogin = persistentLoginOtp.get();
            persistentLogin.setSeries(series);
            persistentLogin.setToken(tokenValue);
            persistentLogin.setLastUse(lastUsed);
            rememberMeTokenRepository.save(persistentLogin);

        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        Optional<PersistentLogin> persistentLoginOtp = rememberMeTokenRepository.findBySeries(seriesId);
        if (persistentLoginOtp.isPresent()) {
            PersistentLogin persistentLogin = persistentLoginOtp.get();
            return new PersistentRememberMeToken(
                    persistentLogin.getUsername(),
                    persistentLogin.getSeries(),
                    persistentLogin.getToken(),
                    persistentLogin.getLastUse()
            );
        }
        return null;
    }

    @Override
    public void removeUserTokens(String username) {
        List<PersistentLogin> persistentLogins = rememberMeTokenRepository.findByUsername(username);
        rememberMeTokenRepository.deleteAll(persistentLogins);
    }
}

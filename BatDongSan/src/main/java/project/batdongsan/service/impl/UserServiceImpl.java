package project.batdongsan.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import project.batdongsan.model.entity.User;
import project.batdongsan.repositoty.UserRepository;
import project.batdongsan.service.UserService;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void increaseFailedAttempt(User user) {
        int newFailedAttempt = user.getFailedAttempt() + 1;
        userRepository.updateFailedAttempts(newFailedAttempt, user.getEmail());
    }

    @Override
    public void resetFailedAttempt(User user) {
        userRepository.updateFailedAttempts(0,user.getEmail());
    }

    @Override
    public void lockUser(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());

        userRepository.save(user);
    }

    @Override
    public boolean unlockWhenTimeExpired(User user) {
        long lockTimeMillis = user.getLockTime().getTime();
        long currentTimeMillis = System.currentTimeMillis();

        if (lockTimeMillis + LOCK_TIME_DURATION < currentTimeMillis) {
        user.setAccountNonLocked(true);
        user.setLockTime(null);
        user.setFailedAttempt(0);

        userRepository.save(user);

        return true;
        }

        return false;

    }

    @Override
    public User getByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.orElse(null);
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> userOpt = userRepository.findByEmail(username);
        return userOpt.orElse(null);
    }

    @Override
    public User findByResetToken(String token) {
        return userRepository.findByResetPasswordToken(token).orElse(null);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}

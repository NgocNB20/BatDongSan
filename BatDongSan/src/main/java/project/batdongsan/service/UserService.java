package project.batdongsan.service;

import project.batdongsan.model.entity.User;

public interface UserService {
    int MAX_FAILED_ATTEMPTS = 20;
    long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000;
    void increaseFailedAttempt(User user);
    void resetFailedAttempt(User user);
    void lockUser(User user);
    boolean unlockWhenTimeExpired(User user);
    User getByEmail(String email);
    User getByUsername(String username);
    User findByResetToken(String token);
    User updateUser(User user);
}

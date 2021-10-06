package com.project.service;

import com.project.core.encryption.CustomPasswordEncoder;
import com.project.core.exception.ApplicationExceptions;
import com.project.core.service.impl.GenericService;
import com.project.dao.UserRepository;
import com.project.model.User;
import com.project.model.security.JwtChangePassRequest;
import com.project.utility.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Service
public class UserService extends GenericService<User, Integer> {

    @Autowired
    private UserRepository userDao;

    public void changePassword(JwtChangePassRequest request) {
        this.userCacheEvict();
        User user = this.findByLoginName(request.getUsername());
        if (user != null) {
            if (!new CustomPasswordEncoder().encode(request.getPasswordOld()).equals(user.getPassword())) {
                throw new ApplicationExceptions("auth.password.wrong");
            }
            if (request.getPasswordNew().equals(request.getPasswordOld())) {
                throw new ApplicationExceptions("auth.change.password.same");
            }
            user.setPassword(new CustomPasswordEncoder().encode(request.getPasswordNew()));
            userDao.save(user);
        } else {
            throw new ApplicationExceptions("auth.wrong");
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "user", allEntries = true)
    })
    public boolean userCacheEvict() {
        return true;
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }

    @Override
    public Integer save(User entity) {
        return userDao.save(entity).getId();
    }

    public String saveAndGetPassword(User entity) {
        User old = userDao.findByLoginName(entity.getLoginName());
        if (old != null) {
            throw new ApplicationExceptions("management.user.add.exist");
        }

        entity.setCreatorUserId(SecurityUtil.getCurrentUser().getId());

        Integer password = getPassword();
        entity.setPassword(new CustomPasswordEncoder().encode(password.toString()));
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        entity.setPasswordExpiredDate(calendar.getTime());
        userDao.save(entity);
        return password.toString();
    }

    private Integer getPassword() {
        Random rand = new Random();
        return rand.nextInt(900000000) + 100000000;
    }

    public String resetPassword(User entity) {
        Integer password = getPassword();
        String hashedPassword = new CustomPasswordEncoder().encode(password.toString());
        entity.setPassword(hashedPassword);
        userDao.save(entity);
        return password.toString();
    }

    public String resetPassword(Integer userId) {
        User entity = this.get(userId);
        return this.resetPassword(entity);
    }

    @Override
    public void update(User entity) {
        User old = userDao.findByLoginName(entity.getLoginName());
        if (old == null) {
            throw new ApplicationExceptions();
        }
        entity.setLoginName(old.getLoginName());
        entity.setPassword(old.getPassword());
        entity.setActiveDate(old.getActiveDate());
        entity.setPasswordExpiredDate(old.getPasswordExpiredDate());
        userDao.save(entity);
    }

    @Override
    public User get(Integer id) {
        return userDao.findById(id).get();
    }

    public void updateLastLoginDate(User entity) {
        User u = this.get(entity.getId());
        u.setLastLoginDate(entity.getLastLoginDate());
        userDao.save(u);
    }

    public User findByLoginName(String userName) {
        return userDao.findByLoginName(userName);
    }

}

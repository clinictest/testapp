package com.vaadin.testapp.service;

import com.vaadin.testapp.models.Role;
import com.vaadin.testapp.models.Status;
import com.vaadin.testapp.models.User;
import com.vaadin.testapp.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;


    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.userRepository.findAll(pageable);
    }

    public boolean check(User user) {
        for (int i = 0; i < userRepository.findAll().size(); i++) {
            User user1 = userRepository.findAll().get(i);
            if (user1.getPassword().equals(user.getPassword()) &&
                    user1.getUsername().equals(user.getUsername()) &&
                    user1.getStatus() == Status.ACTIVE) {
                return true;
            }
        }

        return false;
    }


    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        } else {
            user.setUsername(user.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setCreatedAt(new Date());
            user.setStatus(Status.ACTIVE);
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
            System.out.println(user);
            userRepository.save(user);
        }
        return true;
    }

    public boolean update(User user, long id) {
        User existUser = userRepository.getOne(id);

        boolean checkParam = false;
        if (user.getUsername() != null) {
            existUser.setUsername(user.getUsername());
            checkParam = true;
        }
        if (user.getPassword() != null) {
            existUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            checkParam = true;
        }

        if (user.getFirstName() != null) {
            existUser.setFirstName(user.getFirstName());
            checkParam = true;
        }
        if (user.getLastName() != null) {
            existUser.setLastName(user.getLastName());
            checkParam = true;
        }
        if (user.getStatus() != null) {
            existUser.setStatus(user.getStatus());
            checkParam = true;
        }
        if (user.getRoles() != null) {
            existUser.setRoles(user.getRoles());
            checkParam = true;
        }

        if (checkParam) {
            existUser.setUpdatedAt(new Date());
            userRepository.save(existUser);
            return true;
        }
        return false;
    }

    public User show(long id) {
        return em.createQuery("SELECT u FROM User u WHERE u.id=:id", User.class)
                .setParameter("id", id).getSingleResult();
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }
}


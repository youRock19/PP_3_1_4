package ru.kata.spring.boot_security.demo.service;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService {
    
    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    //читает всех пользователей
    public List<User> allUsers() {
        return userRepository.findAll();
    }
    //читает пользователя
    public User readUser(int id) {        
        return userRepository.findById(id).get();
    }
    //удаляет пользователя 
    public void deleteUser(int id) {
        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }
//    сохраняет пользователя
    public void saveUser(User user) {
        userRepository.save(user);
    }

//ищет пользователя по имени
    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }
//возвращает список ролей
    public List<Role> getRoles() {
       return roleRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } 
            return user;              
    }

}

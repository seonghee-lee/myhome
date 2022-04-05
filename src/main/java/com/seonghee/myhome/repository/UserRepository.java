package com.seonghee.myhome.repository;

import com.seonghee.myhome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //username으로 user구하기
    User findByUsername(String username);
}

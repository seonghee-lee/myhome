package com.seonghee.myhome.service;

import com.seonghee.myhome.model.Role;
import com.seonghee.myhome.model.User;
import com.seonghee.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){
        //1. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());    //비밀번호 인코딩
        user.setPassword(encodedPassword);

        //2. user테이블의 enabled 값 세팅
        user.setEnabled(true);  //회원가입하면 활성화된 상태로 한다.

        //3. 권한을 user_role 테이블에 넣어준다.
        Role role = new Role();
        role.setId(1L);  //일단 하드코딩으로 유저권한을 준다.(제대로 구현하려면 레포지토리 만들어야 함)
        //User model에서 생성했던 arrayList에 add한다.
        user.getRoles().add(role);

        //user테이블에 회원 정보 저장(회원가입!)
        return userRepository.save(user);
    }
}

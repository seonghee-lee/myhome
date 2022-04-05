package com.seonghee.myhome.controller;

import com.seonghee.myhome.model.Board;
import com.seonghee.myhome.model.User;
import com.seonghee.myhome.repository.BoardRepository;
import com.seonghee.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserApiController {

    @Autowired
    private UserRepository userRepository;

//    @GetMapping("/boards")
//    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
//                    @RequestParam(required=false, defaultValue = "") String content){
//        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
//            return boardRepository.findAll();
//        }
//        else {
//            //param으로 title 또는 content 들어온 경우
//            return boardRepository.findByTitleOrContent(title, content);
//        }
//    }

    @GetMapping("/users")
    List<User> all(){
        return userRepository.findAll();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    //단건
    @GetMapping("/users/{id}")
    User one(@PathVariable Long id){
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id){
        return userRepository.findById(id)
                .map(user -> {
                    user.setBoards(newUser.getBoards());
                    //board에 user_id가 담기지 않았음 ... 그래서 이렇게해줌
                    for(Board board : newUser.getBoards()){
                        board.setUser(user);
                    }
                    return userRepository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }
}

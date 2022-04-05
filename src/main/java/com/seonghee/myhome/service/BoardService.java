package com.seonghee.myhome.service;

import com.seonghee.myhome.model.Board;
import com.seonghee.myhome.model.User;
import com.seonghee.myhome.repository.BoardRepository;
import com.seonghee.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;   //String으로 받아온 username으로 id를 받아오기 위해 select하려고.

    public Board save(String username, Board board){
        User user = userRepository.findByUsername(username);    //username으로 user정보 가져온다.
        board.setUser(user);
        return boardRepository.save(board);
    }
}

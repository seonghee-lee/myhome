package com.seonghee.myhome.controller;

import com.seonghee.myhome.model.Board;
import com.seonghee.myhome.repository.BoardRepository;
import com.seonghee.myhome.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired  //DI가 일어난다
    private BoardRepository boardRepository;    //이걸 이용해서 테이블 데이터를 갖고오자

    @Autowired  //사용하기 위해서는 BoardValidator 클래스에 @Component가 되어있어야한다.
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required=false, defaultValue = "") String searchText){     //parameter로 page설정하기
        //Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board>boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);  //최솟값 1
        int endPage = Math.min(boards.getPageable().getPageNumber() + 4, boards.getTotalPages());
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards); //model에 담긴 데이터를 thymeleaf에서 사용할 수 있게 된다.
        //boards라는 key값에 boards라는 데이터를 준다.
        return "board/list";
    }

    @GetMapping("/form")
    //2번째 파라미터: 수정 시 사용 -- 새 글을 작성할 때는 필요 없으므로 required=false(필수 아님을 의미)
    //수정한다면 Long id를 넘겨받는다.(request param)
    public String form(Model model, @RequestParam(required=false) Long id){
        if(id == null){
            //새로운 글 쓰기
            model.addAttribute("board", new Board());
        }else{
            //조회를 해온다.
            //optional 반환이라서 해주는 설정 .orElse(null) -- 조회된 값이 없을 경우 null을 준다
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    //public String formSubmit(@ModelAttribute Board board, Model model){
    public String formSubmit(@Validated Board board, BindingResult bindingResult){
        //유효성 검증
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        //저장
        boardRepository.save(board);
        return "redirect:/board/list";  //저장된 값을 뿌려주기 위해 redirect 키워드를 적어준다.
    }

}

package com.seonghee.myhome.validator;

import com.seonghee.myhome.model.Board;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidator implements Validator{


    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Board b = (Board) target;
        if((!(StringUtils.length(b.getTitle()) > 1)) || StringUtils.length(b.getTitle()) > 30) {
            errors.rejectValue("title","negativevalue","제목을 입력하세요.(2~30자)");
        }
        if(StringUtils.isEmpty(b.getContent())){
            errors.rejectValue("content","negativevalue","내용을 입력하세요.");
        }

    }
}

package com.example.coletter.controller;

import com.example.coletter.common.BaseException;
import com.example.coletter.common.BaseResponse;
import com.example.coletter.model.dto.LetterResponse;
import com.example.coletter.model.dto.UpdateMailboxRequest;
import com.example.coletter.model.entity.Letter;
import com.example.coletter.service.MailboxService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/mailbox")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://3.36.88.85:8080"})
public class MailboxController {

    private final MailboxService mailboxService;

    @Operation(summary = "메일 갯수")
    @GetMapping("/countmail")
    public BaseResponse<Long> countMail(@RequestHeader("Authorization")String accessToken){
        Long count = mailboxService.countMail(accessToken);
        return new BaseResponse<>(count);
    }


    @Operation(summary = "메일 제목변경")
    @PatchMapping("")
    public BaseResponse<Long> putMailbox(@RequestHeader("Authorization")String accessToken,@RequestBody @Valid UpdateMailboxRequest updateMailboxRequest) {

        Long id = mailboxService.updateMailboxTitle(accessToken,updateMailboxRequest.getTitle());
        log.info("제목변경");
        return new BaseResponse<>(id);
    }

    @Operation(summary = "메일함 전체 조회")
    @GetMapping("/getallmail/{mailboxId}")
    public BaseResponse<List<LetterResponse>> getAllLetter(@RequestHeader("Authorization") String accessToken, @PathVariable Long mailboxId) {
        try{
            List<LetterResponse> letters = mailboxService.getAllLetter(accessToken, mailboxId);
            return new BaseResponse<>(letters);
        } catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



}

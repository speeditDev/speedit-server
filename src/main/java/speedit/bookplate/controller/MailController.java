package speedit.bookplate.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedit.bookplate.config.CommonResponseDto;
import speedit.bookplate.dto.email.EmailRequestDto;
import speedit.bookplate.service.MailService;
import speedit.bookplate.utils.JwtService;

import javax.mail.MessagingException;

@RestController
@Api(tags = {"9. Mail"})
@RequiredArgsConstructor
@RequestMapping("")
public class MailController {

    private final MailService mailService;
    private final JwtService jwtService;

    @ApiOperation(value = "이메일 인증메일 전송",notes = "입력된 이메일로 인증 메일 전송")
    @ApiResponses({
            @ApiResponse(code=200,message = "요청에 성공하였습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다."),
            @ApiResponse(code = 415,message = "회사 이메일로만 인증이 가능합니다."),
            @ApiResponse(code = 416,message = "이메일 양식에 맞게 작성해 주세요.")
    })
    @RequestMapping(value = "/email",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> execMail(@RequestBody EmailRequestDto emailRequestDto) throws MessagingException {
        jwtService.isExpireAccessToken();

        mailService.mailSend(emailRequestDto);
        return ResponseEntity.ok(new CommonResponseDto());
    }

    @ApiOperation(value = "이메일 인증하기",notes = "이메일 인증 값 true로 변경하기")
    @ApiResponses({
            @ApiResponse(code=200,message = "요청에 성공하였습니다."),
            @ApiResponse(code=401,message = "JWT를 입력해주세요."),
            @ApiResponse(code=402,message = "유효하지 않은 JWT입니다."),
    })
    @RequestMapping(value = "/email/certify",method = RequestMethod.POST)
    public ResponseEntity<CommonResponseDto> certify() {
        jwtService.isExpireAccessToken();

        long userIdx=jwtService.getUserIdx();
        mailService.certifyEmail(userIdx);
        return ResponseEntity.ok(new CommonResponseDto());
    }

}

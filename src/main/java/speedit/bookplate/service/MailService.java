package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import speedit.bookplate.dto.email.EmailRequestDto;
import speedit.bookplate.exception.InvalidEmailException;
import speedit.bookplate.exception.InvalidEmailTypeException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void mailSend(EmailRequestDto emailRequestDto) throws MessagingException{

        if(checkEmailType(emailRequestDto.getEmail())==false){
            throw new InvalidEmailTypeException();
        }

        if(checkEmail(emailRequestDto.getEmail())==true){
            throw new InvalidEmailException();
        }

        MimeMessage message1 = javaMailSender.createMimeMessage();
        message1.addRecipient(MimeMessage.RecipientType.TO,new InternetAddress(emailRequestDto.getEmail()));
        message1.setText(setContext(),"utf-8","html");

        javaMailSender.send(message1);
    }

    public boolean checkEmail(String email){
        String[] portalEmail=new String[]{"naver.com","daum.net","gmail.com","yahoo.com","hotmail.com","nate.com","empas.com","dreamwiz.com","korea.com"};
        String checkEmail=email.split("@")[1];
        boolean check=false;
        for(int i=0; i< portalEmail.length; i++){
            if(portalEmail[i].equals(checkEmail)){
                check=true;
            }
        }
        return check;
    }

    public boolean checkEmailType(String email){
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    private String setContext(){
        Context context = new Context();
        return templateEngine.process("mail copy",context);
    }

    public void certifyEmail(long userIdx){
        //userRepository.modifyEmail(userIdx);
    }


}

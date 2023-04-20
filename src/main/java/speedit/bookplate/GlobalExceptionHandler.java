package speedit.bookplate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import speedit.bookplate.exception.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SameUserException.class)
    public ResponseEntity<String> handleSameUserException(){
        return new ResponseEntity<>("이미 등록된 유저입니다.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongIdOrPasswordException.class)
    public ResponseEntity<String> handleWrongIdOrPasswordException(){
        return new ResponseEntity<>("존재하지 않는 비밀번호 혹은 아이디입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongEmailOrBirthException.class)
    public ResponseEntity<String> handleWrongEmailOrBirthException(){
        return new ResponseEntity<>("존재하지 않는 이메일 혹은 생년월일입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpireTokenException.class)
    public ResponseEntity<String> handleExpireTokenException(){
        return new ResponseEntity<>("유효하지 않는 토큰입니다.",HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EmptyTokenException.class)
    public ResponseEntity<String> handleEmptyTokenException(){
        return new ResponseEntity<>("토큰값을 입력해주세요.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<String> handleDuplicateNicknameException() {
        return new ResponseEntity<>("중복된 닉네임입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicationEmailException.class)
    public ResponseEntity<String> handleDuplicateEmailException(){
        return new ResponseEntity<>("중복된 이메일입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidLikeMessageException.class)
    public ResponseEntity<String> handleInvalidLikeMessageException() {
        return new ResponseEntity<>("이미 좋아요된 피드입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundFeedException.class)
    public ResponseEntity<String> handleNotFoundFeedException() {
        return new ResponseEntity<>("존재하지 않는 피드입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundBookIdxException.class)
    public ResponseEntity<String> handleNotFoundBookIdxException() {
        return new ResponseEntity<>("존재하지 않는 bookIdx입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistCodeException.class)
    public ResponseEntity<String> handleNotExistCodeException() {
        return new ResponseEntity<>("존재하지 않는 Code입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateBookException.class)
    public ResponseEntity<String> handleDuplicationBookException() {
        return new ResponseEntity<>("이미 존재하는 책입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicationFavoriteJobException.class)
    public ResponseEntity<String> handleDuplicationFavoriteJobException() {
        return new ResponseEntity<>("이미 등록된 관심 직업입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotExistUserException.class)
    public ResponseEntity<String> handleNotExistUserException() {
        return new ResponseEntity<>("존재하지 않는 유저입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SelfFollowException.class)
    public ResponseEntity<String> handleSelfFollowException() {
        return new ResponseEntity<>("자기 자신을 팔로우할 수 없습니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyFollowingException.class)
    public ResponseEntity<String> handleAlreadyFollowingException() {
        return new ResponseEntity<>("이미 팔로잉한 유저입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFollowingException.class)
    public ResponseEntity<String> handleNotFollowingException() {
        return new ResponseEntity<>("팔로잉하지 않은 유저입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyNicknameException.class)
    public ResponseEntity<String> handleEmptyNicknameException() {
        return new ResponseEntity<>("닉네임을 입력해주세요.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleInvalidEmailException() {
        return new ResponseEntity<>("올바르지 않은 이메일입니다.",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEmailTypeException.class)
    public ResponseEntity<String> handleInvalidEmailTypeException() {
        return new ResponseEntity<>("이메일 형식을 확인해주세요.",HttpStatus.BAD_REQUEST);
    }

}

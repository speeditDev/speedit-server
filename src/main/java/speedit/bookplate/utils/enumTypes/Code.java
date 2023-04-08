package speedit.bookplate.utils.enumTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import speedit.bookplate.domain.Feed;
import speedit.bookplate.repository.FeedRepository;

import java.util.List;
import java.util.function.Supplier;


@Getter
@AllArgsConstructor
public enum Code {

    //입력 A : 전체, M: 해당 유저만, B: 해당 도서만, J: 직업별 조회, F: 팔로워별 조회
    A, M, B, J, F;

    public static boolean isExistCode(Code code){
        for(Code tmpCode: Code.values()){
            if(tmpCode.equals(code)){
                return true;
            }
        }
        return false;
    }

}

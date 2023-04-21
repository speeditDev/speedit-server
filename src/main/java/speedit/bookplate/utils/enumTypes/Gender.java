package speedit.bookplate.utils.enumTypes;

import lombok.Getter;

@Getter
public enum Gender {

    //M: 남자, W: 여자
    M("M"),W("W");

    private String gender;

    Gender(String gender) {
        this.gender = gender;
    }
}

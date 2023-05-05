package speedit.bookplate.domain;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import speedit.bookplate.config.BaseTimeEntity;
import speedit.bookplate.dto.user.UserCreateRequest;
import speedit.bookplate.utils.enumTypes.Gender;
import speedit.bookplate.utils.enumTypes.UserStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private String refreshToken;

    private String personalEmail;

    private String profileImg;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private String job;

    private String introduction;

    private String company;

    private String companyEmail;  //회사 이메일

    @Column(name = "follower_count",nullable = false)
    private int followerCount;

    @Column(nullable = false)
    private Boolean isEmailCertified;

    @OneToMany(mappedBy = "user")
    @BatchSize(size = 50)
    private List<Feed> feeds = new ArrayList<>(); //회원 등록 피드 정보

    public void increaseFollowerCnt(){
        this.followerCount+=1;
    }

    public void decreaseFollowerCnt(){
        this.followerCount-=1;
    }

    public void isDelete(){
        this.status = UserStatus.INACTIVE;
    }

    public void updatePassword(final String password) {this.password = password;}

    public void update(final User updateUser){
        updateNickname(updateUser.nickname);
        updateProfileImg(updateUser.profileImg);
        updateJob(updateUser.job);
        updateCompany(updateUser.company);
        updateIntroduction(updateUser.introduction);
        updateEmailCertified(updateUser.isEmailCertified);
    }

    private void updateNickname(final String nickname){
        if(nickname!=null){
            this.nickname=nickname;
        }
    }

    private void updateProfileImg(final String profileImg){
        if(profileImg!=null){
            this.profileImg=profileImg;
        }
    }

    private void updateJob(final String job){
        if(job!=null){
            this.job=job;
        }
    }

    private void updateCompany(final String company){
        if(company!=null){
            this.company=company;
        }
    }

    private void updateIntroduction(final String introduction){
        if(introduction!=null){
            this.introduction=introduction;
        }
    }

    private void updateEmailCertified(final boolean isEmailCertified){
        this.isEmailCertified = isEmailCertified;
    }

    public static User SignUpUser(UserCreateRequest userCreateRequest){
        return User.builder()
                .birth(userCreateRequest.getBirth())
                .profileImg(userCreateRequest.getProfileImg())
                .status(UserStatus.ACTIVE)
                .company(userCreateRequest.getCompany())
                .gender(userCreateRequest.getGender())
                .job(userCreateRequest.getJob())
                .nickname(userCreateRequest.getNickname())
                .personalEmail(userCreateRequest.getPersonalEmail())
                .followerCount(0)
                .password(userCreateRequest.getPassword())
                .isEmailCertified(false)
                .build();
    }

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

}

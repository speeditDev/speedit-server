package speedit.bookplate.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import speedit.bookplate.config.BaseTimeEntity;
import speedit.bookplate.dto.feed.FeedResponseDto;
import speedit.bookplate.dto.user.UserCreateRequestDto;
import speedit.bookplate.dto.user.UserProfileResponse;
import speedit.bookplate.utils.enumTypes.Gender;
import speedit.bookplate.utils.enumTypes.UserStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Feed> feeds = new ArrayList<>(); //회원 등록 피드 정보

    public void increaseFollowerCnt(){
        this.followerCount+=1;
    }

    public void decreaseFollowerCnt(){
        this.followerCount-=1;
    }

    public void deleteUser(){
        this.status = UserStatus.INACTIVE;
    }

    public void update(final User user){
        updateNickname(user.nickname);
        updateProfileImg(user.profileImg);
        updateJob(user.job);
        updateCompany(user.company);
        updateIntroduction(user.introduction);
        updateEmailCertified(user.isEmailCertified);
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

    public static User SignUpUser(UserCreateRequestDto userCreateRequestDto){

        return User.builder()
                .birth(userCreateRequestDto.getBirth())
                .profileImg(userCreateRequestDto.getProfileImg())
                .status(UserStatus.ACTIVE)
                .company(userCreateRequestDto.getCompany())
                .gender(userCreateRequestDto.getGender())
                .job(userCreateRequestDto.getJob())
                .nickname(userCreateRequestDto.getNickname())
                .personalEmail(userCreateRequestDto.getPersonalEmail())
                .followerCount(0)
                .password(userCreateRequestDto.getPassword())
                .isEmailCertified(false)
                .build();
    }

    public static UserProfileResponse converUserProfile(User user){
        return UserProfileResponse.builder()
                .profileImg(user.profileImg)
                .nickname(user.nickname)
                .birth(user.birth)
                .gender(String.valueOf(user.gender))
                .job(user.job)
                .company(user.company)
                .companyEmail(user.companyEmail)
                .isEmailCertified(false)
                .introduction(user.introduction)
                .feeds(convertToFeedRes(user.feeds))
                .build();
    }

    private static List<FeedResponseDto> convertToFeedRes(List<Feed> feed){
        return feed.stream()
                .map(it -> FeedResponseDto.SearchFeedResDtoToEntity(it))
                .collect(Collectors.toList());
    }

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

}

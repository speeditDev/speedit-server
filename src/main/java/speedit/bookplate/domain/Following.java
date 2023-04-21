package speedit.bookplate.domain;

import lombok.*;
import speedit.bookplate.config.BaseTimeEntity;
import speedit.bookplate.exception.SelfFollowException;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Following extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follower_id",nullable = false)
    private Long followerId;

    @Column(name = "following_id",nullable = false)
    private Long followingId;

    private void validateNotSelfFollow(final Long followerId, final Long followingId){
        if (validateBothNotNull(followerId, followingId) && followerId.equals(followingId)) {
            throw new SelfFollowException();
        }
    }

    private boolean validateBothNotNull(final Long followerId, final Long followingId){
        return followerId!=null && followingId!=null;
    }

    public boolean isFollowing(final Long memeberId){
        return followingId.equals(memeberId);
    }

}

package speedit.bookplate.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import speedit.bookplate.domain.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static speedit.bookplate.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> findByJobUsingQuerydsl(String job, Pageable pageable) {
        final List<Long> query = jpaQueryFactory.select(user.id)
                .from(user)
                .where(user.job.like(job))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        if(query.size()==0){
            return new ArrayList<>();
        }

        final List<User> userResult = jpaQueryFactory.selectFrom(user)
                .where(user.id.in(query))
                .fetch();

        return userResult;
    }

    @Override
    public List<Long> findIdByJobUsingQuerydsl(String job, Pageable pageable) {
        final List<Long> query = jpaQueryFactory.select(user.id)
                .from(user)
                .where(user.job.like(job))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        if(query.size()==0){
            return new ArrayList<>();
        }

        return query;
    }


}

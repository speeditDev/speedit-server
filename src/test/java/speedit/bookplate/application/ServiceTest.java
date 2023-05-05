package speedit.bookplate.application;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.repository.BookLikeRepository;
import speedit.bookplate.repository.BookRepository;
import speedit.bookplate.repository.FollowingRepository;
import speedit.bookplate.repository.UserRepository;
import speedit.bookplate.service.BookService;
import speedit.bookplate.service.FollowService;
import speedit.bookplate.service.UserService;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public abstract class ServiceTest {

    @Autowired
    protected UserService userService;

    @MockBean
    protected UserRepository userRepository;

    @Autowired
    protected BookService bookService;

    @MockBean
    protected BookRepository bookRepository;

    @Mock
    protected BookLikeRepository bookLikeRepository;

    @MockBean
    protected FollowingRepository followingRepository;

    @Autowired
    protected FollowService followService;


}

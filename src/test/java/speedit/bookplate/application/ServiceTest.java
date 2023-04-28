package speedit.bookplate.application;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.repository.BookLikeRepository;
import speedit.bookplate.repository.BookRepository;
import speedit.bookplate.repository.UserRepository;
import speedit.bookplate.service.BookService;
import speedit.bookplate.service.UserService;

@Transactional
public abstract class ServiceTest {

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserRepository userRepository;

    @InjectMocks
    protected BookService bookService;

    @Mock
    protected BookRepository bookRepository;

    @Mock
    protected BookLikeRepository bookLikeRepository;


}

package speedit.bookplate.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.service.UserService;

@Transactional
public abstract class ServiceTest {

    @Autowired
    protected UserService userService;


}

package speedit.bookplate.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import speedit.bookplate.repository.BookCategoryRepository;
import speedit.bookplate.service.BookCategoryService;
import speedit.bookplate.domain.BookCategory;
import speedit.bookplate.utils.JwtService;

import java.util.List;
import java.util.Set;

@Api(tags = {"5.BookCategory"})
@RequiredArgsConstructor
@RestController
@RequestMapping
public class BookCategoryController {

    private final BookCategoryService bookCategoryService;
    private final BookCategoryRepository bookCategoryRepository;
    private final JwtService jwtService;

    @ApiOperation(value = "북 카테고리 추가 및 조회", notes = "스피딧 북 카테고리를 추가한 후 조회한다.")
    @GetMapping(value = "/SetCategory")
    public List<BookCategory> setCategory() {
        jwtService.isExpireAccessToken();
        BookCategory a = bookCategoryService.addCategory();
        Set<String> set = a.getBookCategory().keySet();
        return bookCategoryRepository.findAll();
    }
}

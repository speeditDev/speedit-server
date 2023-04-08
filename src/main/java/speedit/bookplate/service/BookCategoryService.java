package speedit.bookplate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import speedit.bookplate.domain.BookCategory;
import speedit.bookplate.repository.BookCategoryRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;


    public BookCategory addCategory(){
        Map<String,Boolean> map1 = new HashMap<>();
        map1.put("소설/시/희곡", false);
        map1.put("역사", false);
        map1.put("경제경영", false);
        map1.put("자기계발", false);
        map1.put("예술/대중문화", false);
        map1.put("인문/사회", false);
        map1.put("사회과학", false);
        map1.put("과학", false);
        map1.put("종교/역학", false);
        map1.put("고전", false);
        map1.put("에세이", false);
        map1.put("인물/평전", false);
        BookCategory bookCategory = BookCategory.createBookCategory(map1);
        return bookCategoryRepository.save(bookCategory);
    }
}

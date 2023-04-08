package speedit.bookplate.utils.enumTypes;

public enum BookCategory {

    Literature("고전/문학"),Phychology("심리"),
    Philosophy("철학"),Business("경영"),
    Economy("경제"),Trend("트렌드/미래예측"),
    Marketing("마케팅 전략"),Investment("재테크/투자"),
    Success("성공/처세"),TimeManagement("시간관리"),
    PowerDevelopment("자기능력계발"),Relationship("인간관계"),
    Talk("대화/협상"),Essay("시/에세이"),Culture("역사/문화");

    private String title;

    BookCategory(String title) {
        this.title = title;
    }
}

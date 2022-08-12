package utils;

import models.Menu;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class URLRepositoryTest {
    @Test
    void url제육 (){
        URLRepository urlRepository = new URLRepository();

        Menu menu = new Menu("","제육볶음","","","","");

        assertEquals("https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/%EC%9D%BC%EB%" +
                        "B0%98%EB%AA%85/%EC%A0%9C%EC%9C%A1%EB%B3%B6%EC%9D%8C?portionid=4948521&portionamount=100.000",
                urlRepository.url(menu.mainMenu()));
    }

    @Test
    void url순살치킨 (){
        URLRepository urlRepository = new URLRepository();

        Menu menu = new Menu("","순살치킨","","","","");

        assertEquals("https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/%EC%9D%BC%" +
                        "EB%B0%98%EB%AA%85/%EC%88%9C%EC%82%B4%EC%B9%98%ED%82%A8",
                urlRepository.url(menu.mainMenu()));
    }

    @Test
    void url생선가스 (){
        URLRepository urlRepository = new URLRepository();

        Menu menu = new Menu("","생선가스","","","","");

        assertEquals("https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/%EC%9D%BC%EB%" +
                        "B0%98%EB%AA%85/%EC%83%9D%EC%84%A0%EA%B9%8C%EC%8A%A4?portionid=5364371&portionamount=100.000",
                urlRepository.url(menu.mainMenu()));
    }
}

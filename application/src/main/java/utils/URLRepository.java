package utils;

public class URLRepository {
    public String url(String mainMenu) {
        if (mainMenu.equals("제육볶음")){
            return "https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/%EC%9D%BC%EB%" +
                    "B0%98%EB%AA%85/%EC%A0%9C%EC%9C%A1%EB%B3%B6%EC%9D%8C?portionid=4948521&portionamount=100.000";
        }

        if (mainMenu.equals("순살치킨")){
            return "https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/%EC%9D%BC%EB" +
                    "%B0%98%EB%AA%85/%EC%88%9C%EC%82%B4%EC%B9%98%ED%82%A8";
        }

        if (mainMenu.equals("생선가스")){
            return "https://www.fatsecret.kr/%EC%B9%BC%EB%A1%9C%EB%A6%AC-%EC%98%81%EC%96%91%EC%86%8C/%EC%9D%BC%EB%B0%98%EB%AA%85/%EC%83%9D%EC%84%A0%EA%B9%8C%EC%8A%A4?portionid=5364371&portionamount=100.000";
        }

        return null;
    }
}

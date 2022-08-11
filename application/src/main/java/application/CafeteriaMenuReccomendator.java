package application;// 내가 원하는 것
// 1. 학생식당 메인메뉴 비교 (4 개)
// 2.
// 3.
// 4.
// 도메인 모델 : menu


import frame.DatePicker;
import models.Menu;
import models.Nutrition;
import models.Restaurant;
import models.SystemStatus;
import utils.Loader;
import utils.RecordLoader;
import utils.Sort;
import utils.URLRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CafeteriaMenuReccomendator {

    private static JFrame frame;
    private static JPanel contentPanel;
    private static JPanel buttonPanel;
    private static JPanel menuPanel;
    private  RecordLoader recordLoader;
    private SystemStatus systemStatus;

    private Loader loader;
    private URLRepository urlRepository;
    private Sort sort;

    private static List<Restaurant> restaurants;
    private List<Restaurant> todayRestaurants;
    private JFrame recordFrame;
    private JFrame alertFrame;
    private List<Menu> recordedMenus;
    private List<Nutrition> recordedNutritions;
    private JPanel displayPanel;
    private List<File> menuFiles;
    private List<File> nutritionFiles;
    private List<Restaurant> currentRestaurants;
    private String favoriteNutrition;

    public static void main(String[] args) throws FileNotFoundException {
        CafeteriaMenuReccomendator application = new CafeteriaMenuReccomendator();

        application.run();
    }

    public void run() throws FileNotFoundException {
        prepareObjects();

        initFiles();

        loadGeumjeong();

        loadStudentHall();

        loadStaffCafeteria();

        initMainFrame();

        initButtonPanel();

        initContentPanel();

//        initDisplayPanel();

        frame.setVisible(true);
    }

    private void loadGeumjeong() throws FileNotFoundException {
        List<Menu> menus = loader.loadMenus(menuFiles.get(0));

        List<Nutrition> nutritions = loader.loadNutritions(nutritionFiles.get(0));

        Restaurant geumjeong = new Restaurant("금정회관", menus, nutritions, 3500);

        restaurants.add(geumjeong);
    }

    private void loadStudentHall() throws FileNotFoundException {
        List<Menu> menus = loader.loadMenus(menuFiles.get(1));

        List<Nutrition> nutritions = loader.loadNutritions(nutritionFiles.get(1));

        Restaurant studentHall = new Restaurant("학생회관", menus, nutritions, 5500);

        restaurants.add(studentHall);
    }

    private void loadStaffCafeteria() throws FileNotFoundException {
        List<Menu> menus = loader.loadMenus(menuFiles.get(2));

        List<Nutrition> nutritions = loader.loadNutritions(nutritionFiles.get(2));

        Restaurant staffCafeteria = new Restaurant("교직원식당", menus, nutritions, 5500);

        restaurants.add(staffCafeteria); // TODO : 밑에서 더해준 restaurants add 지워줘야함
    }

    private void prepareObjects() throws FileNotFoundException {
        systemStatus = new SystemStatus();
        systemStatus.today();

        recordLoader = new RecordLoader();
        recordedMenus = recordLoader.loadMenus();
        systemStatus.isRecorded(recordedMenus);

        recordedNutritions = recordLoader.loadNutritions();
        systemStatus.initNutritionCounts(recordedNutritions);
        favoriteNutrition = systemStatus.compareNutritionCounts();

        urlRepository = new URLRepository();
        loader = new Loader();
        sort = new Sort();

        restaurants = new ArrayList<>();
        currentRestaurants = new ArrayList<>();
        todayRestaurants = new ArrayList<>();
        menuFiles = new ArrayList<>();
        nutritionFiles = new ArrayList<>();
    }

    private void initFiles() {
        menuFiles.add(new File("./src/main/resources/menus/금정회관.csv"));
        menuFiles.add(new File("./src/main/resources/menus/학생회관.csv"));
        menuFiles.add(new File("./src/main/resources/menus/교직원식당.csv"));

        nutritionFiles.add(new File("./src/main/resources/nutritions/금정회관영양성분.csv"));
        nutritionFiles.add(new File("./src/main/resources/nutritions/학생회관영양성분.csv"));
        nutritionFiles.add(new File("./src/main/resources/nutritions/교직원식당영양성분.csv"));
    }

    private void initMainFrame() {
        frame = new JFrame("학식메뉴 알리미");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
    }

//    public void initDisplayPanel() {
//        displayPanel = new JPanel();        //TODO : 컨텐츠 패널아래에 공간이 필요할 경우 들어가는 패널. 아직 frame에 add안해줌.
//
//        displayPanel.setPreferredSize(new Dimension(200,300));
//
//        displayPanel.setBackground(Color.YELLOW);
//    }

    public void initButtonPanel() {
        buttonPanel = new JPanel();

        buttonPanel.add(createTitleLabel());

        frame.add(buttonPanel, BorderLayout.NORTH);
    }

    public void initContentPanel() {
        contentPanel = new JPanel();

        contentPanel.setLayout(new BorderLayout());

        setBorder(contentPanel, 300, 400, 300, 400);

        contentPanel.add(createStartButton());

        frame.add(contentPanel);
    }

    public JLabel createTitleLabel() {
        JLabel label = new JLabel("학식메뉴 알리미");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    public JButton createStartButton() {
        JButton button = new JButton("시작하기!");
        button.addActionListener(e -> {
            setBorder(contentPanel, 0, 0, 0, 0);
            removeContainer(buttonPanel);
            removeContainer(contentPanel);

            setBorder(contentPanel,100,0,0,0);
            contentPanel.add(reccommendPanel());

//            displayPanel.add(new JLabel("__님이 선호하는 영양성분: " + favoriteNutrition));
//            frame.add(displayPanel,BorderLayout.SOUTH);

//            MenuPanel menuPanel = new MenuPanel(frame, buttonPanel, contentPanel);

            buttonPanel.add(createMenuPanel());

            updateDisplay();
        });
        return button;
    }

    private JPanel reccommendPanel() {
        JPanel panel = new JPanel();
        panel.add(greetingLabel());
        panel.add(confirmButton());
        return panel;
    }

    private JButton confirmButton() {
        JButton button = new JButton("추천메뉴 보러가기");
        button.addActionListener(e -> {
            JFrame recommendFrame = new JFrame("오늘의 추천메뉴");
            recommendFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            recommendFrame.setSize(600,600);

            recommendFrame.add(closeButton(recommendFrame),BorderLayout.NORTH);



            recommendFrame.setVisible(true);
        });
        return button;
    }

    private JLabel greetingLabel() {
        JLabel label = new JLabel("안녕하세요 __님, 오늘의 추천메뉴를 받아보시겠어요?");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    public void setBorder(JPanel panel, int top, int left, int bottom, int right) {
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    public void removeContainer(JPanel panel) {
        panel.removeAll();
    }

    public JPanel createMenuPanel() { // TODO : 홈 패널
        JPanel panel = new JPanel();

        panel.add(todayMenuButton());
        panel.add(recordMenuButton());
        panel.add(recommendationButton());

        return panel;
    }

    //
    //
    //

    //
    //


    public JButton todayMenuButton() {
        JButton button = new JButton("오늘의 메뉴 확인하기");
        button.addActionListener(e -> {
            try {
//                resetRestaurants();

                systemStatus.today();

                currentRestaurants = todayRestaurants;
                // 처음에 오늘메뉴
                // 날짜 선택 -> 식단보기 : 그날 메뉴

                removeContainer(buttonPanel);
                removeContainer(contentPanel);

                contentPanel.add(dateLabel(),BorderLayout.NORTH);

                contentPanel.add(menuPanel());

                buttonPanel.add(menuOptionPanel());

            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    private JLabel dateLabel() {
        JLabel label = new JLabel(systemStatus.date() + "일 학식메뉴");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    //
    //
    //
    //
    //

    public JPanel menuOptionPanel() {              //TODO : 정렬 버튼이 담긴 패널
        JPanel panel = new JPanel();
        panel.add(selectDateButton());
        panel.add(sortButton(Nutrition.PROTEIN));
        panel.add(sortButton(Nutrition.CALORIES));
        panel.add(backToMenuButton());
        return panel;
    }

    public JButton sortButton(String nutrition) {
        JButton button = new JButton(nutrition + " 정렬");
        button.addActionListener(e -> {
            String[] sortedCafeteriaNames = sort.sortByNutrition(currentRestaurants, nutrition);

            removeContainer(contentPanel);
            removeContainer(menuPanel);

            contentPanel.add(menuPanel);

            for (String sortedCafeteriaName : sortedCafeteriaNames) {
                if (sortedCafeteriaName.equals("금정회관")) {
                    try {
                        menuPanel.add(geumjeongPanel());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if (sortedCafeteriaName.equals("학생회관")) {
                    try {
                        menuPanel.add(studentHallMenuPanel());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if (sortedCafeteriaName.equals("교직원식당")) {
                    try {
                        menuPanel.add(staffCafeteriaMenuPanel());
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            updateDisplay();
        });
        return button;
    }

    //
    //
    //
    //
    //

    public JButton backToMenuButton() {
        JButton button = new JButton("돌아가기");
        button.addActionListener(e -> {
            removeContainer(menuPanel);
            backToHome();
        });
        return button;
    }

    private void backToHome() {
        removeContainer(buttonPanel);
        removeContainer(contentPanel);

        buttonPanel.add(createMenuPanel());

        updateDisplay();
    }

    public JPanel menuPanel() throws FileNotFoundException {           // TODO : 메뉴와 영양성분이 배치되는 패널
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 3));
        menuPanel.add(geumjeongPanel());
        menuPanel.add(studentHallMenuPanel());
        menuPanel.add(staffCafeteriaMenuPanel());

        return menuPanel;
    }

    // 금정회관, 교직원식당, 학생회관

    public JPanel geumjeongPanel() throws FileNotFoundException {

        return cafeteriaPanel(restaurants.get(0));
    }

    public JPanel studentHallMenuPanel() throws FileNotFoundException {
        return cafeteriaPanel(restaurants.get(1));
    }

    public JPanel staffCafeteriaMenuPanel() throws FileNotFoundException {
        return cafeteriaPanel(restaurants.get(2));
    }

    public JPanel cafeteriaPanel(Restaurant restaurant) throws FileNotFoundException {
        JPanel panel = new JPanel();

        Menu menu = restaurant.selectMenu();    // TODO : 메뉴를 처리하는 부분.
        //이때 인덱스가 2니깐 10일게 나오겠지?
        //왜 인덱스가 2일까
        // today가 문제같은데
        // 생성자로 계속 선언되고 초기화되니깐
        // 언제 선언되지

        Nutrition nutrition = restaurant.selectNutrition();

        if (systemStatus.todayMenuIndex() < 3) {
            systemStatus.initTodayMenuIndex();

            Restaurant todayRestaurant = new Restaurant(restaurant.name(),
                    List.of(menu),
                    List.of(nutrition),
                    restaurant.price());

            todayRestaurants.add(todayRestaurant);
        }

        panel.add(cafeteriaMenu(restaurant, menu));
        panel.add(menuNutritions(nutrition, menu));

        updateDisplay();
        return panel;
    }

    public JPanel cafeteriaMenu(Restaurant restaurant, Menu menu) throws FileNotFoundException {
        JPanel panel = new JPanel();
        panel.setBackground(Color.cyan);
        panel.setLayout(new GridLayout(8, 1, 0, 30));

        panel.add(new JLabel(restaurant.name()));
        panel.add(new JLabel(restaurant.price() + "원"));
        panel.add(new JLabel(menu.rice()));
        panel.add(new JLabel(menu.mainMenu()));
        panel.add(new JLabel(menu.sideMenu()));
        panel.add(new JLabel(menu.soup()));
        panel.add(new JLabel(menu.gimchi()));
        JButton button = new JButton();
        button.setBorderPainted(false);
        panel.add(button);

        return panel;
    }

    public JPanel menuNutritions(Nutrition nutrition, Menu menu) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1, 0, 30));

        panel.setBackground(Color.YELLOW);

        panel.add(new JLabel("메인 메뉴 영양성분 (100g당)"));
        panel.add(new JLabel("탄수화물: " + nutrition.carbonHydrate()));
        panel.add(new JLabel("당: " + nutrition.sugar()));
        panel.add(new JLabel("단백질: " + nutrition.protein()));
        panel.add(new JLabel("지방: " + nutrition.fat()));
        panel.add(new JLabel("포화지방: " + nutrition.saturatedFat()));
        panel.add(new JLabel("열량: " + nutrition.calories()));

        panel.add(informationUrlButton(menu));

        return panel;
    }

    public JButton informationUrlButton(Menu menu) {
        JButton button = new JButton("자세히");
        button.setForeground(Color.BLUE);
        button.addActionListener(e -> {
            String url = urlRepository.url(menu.mainMenu());

            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        button.setBorderPainted(false);

        return button;
    }

    public void updateDisplay() {
        buttonPanel.setVisible(false);
        buttonPanel.setVisible(true);
        contentPanel.setVisible(false);
        contentPanel.setVisible(true);
//        displayPanel.setVisible(false);
//        displayPanel.setVisible(true);
        frame.setVisible(true);
    }

    public JButton selectDateButton() {
        JButton button = new JButton("날짜 선택");
        button.addActionListener(e -> {
            JFrame calendarFrame = new JFrame("달력");

            JPanel panel = new JPanel();

            JLabel label = new JLabel("선택된 날짜");
            panel.add(label);

            JTextField text = new JTextField(20);
            panel.add(text);

            panel.add(createDateButton(text, calendarFrame));

            panel.add(showMenusButton(text, calendarFrame));

            calendarFrame.getContentPane().add(panel);

            calendarFrame.pack();
            calendarFrame.setLocationRelativeTo(null);
            calendarFrame.setVisible(true);
        });
        return button;
    }

    private JButton createDateButton(JTextField text, JFrame calendarFrame) {
        JButton button = new JButton("날짜보기");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
//                DatePicker datePicker = new DatePicker();
                text.setText(new DatePicker(calendarFrame).setPickedDate());
            }
        });
        return button;
    }

    private JButton showMenusButton(JTextField text, JFrame calendarFrame) {
        JButton button = new JButton("식단 보기");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String date = text.getText(); // 정상동작
                systemStatus.setDate(date);

                systemStatus.initCurrentRestaurant(restaurants,currentRestaurants);

                removeContainer(contentPanel);
                removeContainer(menuPanel);
//                resetRestaurants();

                try {
                    contentPanel.add(dateLabel(),BorderLayout.NORTH);
                    contentPanel.add(menuPanel());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                updateDisplay();

                calendarFrame.dispose();
            }
        });
        return button;
    }
    //
    //

    public JButton recordMenuButton() {
        JButton button = new JButton("오늘의 메뉴 기록하기");
        button.addActionListener(e -> {
            if (todayRestaurants.size() == 0) {
                initRecordFrame();

                JPanel panel = new JPanel();
                panel.add(new JLabel("오늘의 메뉴부터 확인해주세요!"));
                JButton disposeButton = new JButton("확인");
                disposeButton.addActionListener(dispose -> {
                    recordFrame.dispose();
                });
                panel.add(disposeButton);
                recordFrame.add(panel);

                recordFrame.pack();
                recordFrame.setVisible(true);
            }
            if (todayRestaurants.size() == 3) {
                if (!systemStatus.isRecorded()) {
                    initRecordFrame();

                    recordFrame.add(CheckPanel());

                    recordFrame.setVisible(true);
                }

                if (systemStatus.isRecorded()) {
                    removeContainer(contentPanel);

                    contentPanel.add(new JLabel("이미 기록하셨습니다!"));

                    updateDisplay();
                }
            }
        });
        return button;
    }

    private void initRecordFrame() {
        recordFrame = new JFrame("기록하기");

        recordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        recordFrame.setSize(400, 300);
        recordFrame.setLocationRelativeTo(null);
    }

    private JPanel CheckPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());

        panel.add(buttonPanel(), BorderLayout.NORTH);

        panel.add(checkBoxContainer());

        return panel;
    }

    private JPanel checkBoxContainer() {
        JPanel panel = new JPanel();
        panel.add(checkBoxPanel(Restaurant.GEUMJEONG));
        panel.add(checkBoxPanel(Restaurant.STUDENTHALL));
        panel.add(checkBoxPanel(Restaurant.STAFFCAFETERIA));
        return panel;
    }

    private JPanel buttonPanel() {
        JPanel panel = new JPanel();

        panel.add(closeButton(recordFrame));

        return panel;
    }

    //제출버튼 삭제!
//                try {
//                    recordLoader.saveMenu(.get(0));
//                    recordLoader.saveNutrition(nutritions.get(0));
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }


    private JButton closeButton(JFrame frame) {
        JButton button = new JButton("돌아가기");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.dispose();
            }
        });
        return button;
    }

    private JPanel checkBoxPanel(String cafeteria) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        panel.add(createCheckBox(cafeteria));
        panel.add(new JLabel(cafeteria));
        return panel;
    }

    private JCheckBox createCheckBox(String cafeteria) {
        JCheckBox checkBox = new JCheckBox();

        checkBox.addActionListener(e -> {
            alertFrame = new JFrame("확인");
            alertFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            alertFrame.setSize(300, 400);

            alertFrame.add(recordLabel(),BorderLayout.NORTH);
            alertFrame.add(confirmPanel(checkBox,cafeteria));

            alertFrame.pack();
            alertFrame.setLocationRelativeTo(null);
            alertFrame.setVisible(true);
        });

        return checkBox;
    }

    private JLabel recordLabel() {
        JLabel label = new JLabel("기록하시겠습니까?");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private JPanel confirmPanel(JCheckBox checkBox, String cafeteria) {
        JPanel panel = new JPanel();
        JButton confirmButton = new JButton("네");
        confirmButton.addActionListener(e -> {
            // 여기에서 저장해야됨, 단 오늘의 메뉴

            Menu recordedMenu = switch (cafeteria){
                case "금정회관" -> todayRestaurants.get(0).menus().get(0);
                case "학생회관" -> todayRestaurants.get(1).menus().get(0);
                case "교직원식당" -> todayRestaurants.get(2).menus().get(0);
                default -> null;
            };

            recordedMenus.add(recordedMenu);

            Nutrition recordedNutrition = switch (cafeteria){
                case "금정회관" -> todayRestaurants.get(0).nutritions().get(0);
                case "학생회관" -> todayRestaurants.get(1).nutritions().get(0);
                case "교직원식당" -> todayRestaurants.get(2).nutritions().get(0);
                default -> null;
            };

            recordedNutritions.add(recordedNutrition);

            try {
                recordLoader.saveMenu(recordedMenus);
                recordLoader.saveNutrition(recordedNutritions);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            systemStatus.initRecorded();
            recordFrame.dispose();
            alertFrame.dispose();

        });
        panel.add(confirmButton);

        JButton cancelButton = new JButton("아니오");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBox.setSelected(false);

                alertFrame.dispose();
            }
        });
        panel.add(cancelButton);

        return panel;
    }

    //
    //
    //
    //

    private JButton recommendationButton() {
        JButton button = new JButton("선호하는 영양성분 보기");
        button.addActionListener(e -> {
            removeContainer(buttonPanel);
            removeContainer(contentPanel);

            buttonPanel.add(homeButton());
            contentPanel.add(preferLabel());
            contentPanel.add(showPreferencePanel());

            updateDisplay();
        });
        return button;
    }

    private JButton homeButton() {
        JButton button = new JButton("돌아가기");
        button.addActionListener(e -> {
            backToHome();
        });
        return button;
    }

    private JLabel preferLabel() {
        JLabel label = new JLabel("선호하는 영양성분");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private JPanel showPreferencePanel() {
        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(6,1));
        panel.add(new JLabel(Nutrition.CARBONHYDRATE + ": " +systemStatus.carbonHydrateCount()));
        panel.add(new JLabel(Nutrition.SUGAR + ": " +systemStatus.sugarCount()));
        panel.add(new JLabel(Nutrition.PROTEIN + ": " +systemStatus.proteinCount()));
        panel.add(new JLabel(Nutrition.FAT + ": " +systemStatus.fatCount()));
        panel.add(new JLabel(Nutrition.SATURATEDFAT + ": " +systemStatus.saturatedFatCount()));
        panel.add(new JLabel(Nutrition.CALORIES + ": " +systemStatus.caloriesCount()));
        return panel;
    }

    //
    //
    //

}

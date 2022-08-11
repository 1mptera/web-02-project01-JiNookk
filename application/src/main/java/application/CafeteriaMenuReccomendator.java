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
import models.User;
import panels.ImagePanel;
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
    private RecordLoader recordLoader;
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
    private JFrame recommendFrame;
    private Restaurant recommendedRestaurant;
    private User user;
    private ImagePanel imagePanel;

    public static void main(String[] args) throws FileNotFoundException {
        CafeteriaMenuReccomendator application = new CafeteriaMenuReccomendator();

        application.run();
    }

    public void run() throws FileNotFoundException {
        prepareObjects();

        initFiles();

        initRestaurants();

        initMainFrame();

        initImagePanel();

        initButtonPanel();

        initContentPanel();

        initDisplayPanel();

        frame.setVisible(true);
    }

    private void initImagePanel() {
        imagePanel = new ImagePanel("./src/main/resources/images/foodImage1.png");

        imagePanel.setLayout(new BorderLayout());

        frame.add(imagePanel);
    }

    private void prepareObjects() throws FileNotFoundException {
        user = new User("진욱");
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

    private void initTodayRestaurants() {
        systemStatus.initIndex(restaurants.get(0).menus());

        todayRestaurants = List.of(
                new Restaurant(Restaurant.GEUMJEONG,
                        List.of(restaurants.get(0).menus().get(systemStatus.index())),       //TODO
                        List.of(restaurants.get(0).nutritions().get(systemStatus.index())),
                        Restaurant.GEUMJEONGPRICE
                ),
                new Restaurant(Restaurant.STUDENTHALL,
                        List.of(restaurants.get(1).menus().get(systemStatus.index())),       //TODO
                        List.of(restaurants.get(1).nutritions().get(systemStatus.index())),
                        Restaurant.STUDENTHALLPRICE
                ),
                new Restaurant(Restaurant.STAFFCAFETERIA,
                        List.of(restaurants.get(2).menus().get(systemStatus.index())),       //TODO
                        List.of(restaurants.get(2).nutritions().get(systemStatus.index())),
                        Restaurant.STAFFCAFETERIAPRICE
                )
        );
    }

    private void initRestaurants() throws FileNotFoundException {
        loadGeumjeong();

        loadStudentHall();

        loadStaffCafeteria();

        initTodayRestaurants();

        recommendedRestaurant = systemStatus.recommendRestaurant(todayRestaurants, favoriteNutrition);
    }

    private void initMainFrame() {
        frame = new JFrame("학식메뉴 알리미");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 700);
        frame.setLocationRelativeTo(null);
    }

    public void initDisplayPanel() {
        displayPanel = new JPanel();

        displayPanel.setPreferredSize(new Dimension(0, 30));

        displayPanel.setBackground(Color.YELLOW);

        displayPanel.setOpaque(false);
    }

    public void initButtonPanel() {
        buttonPanel = new JPanel();

        buttonPanel.add(createTitleLabel());

        imagePanel.add(buttonPanel, BorderLayout.NORTH);

        buttonPanel.setOpaque(false);
    }

    public void initContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BorderLayout());

        setBorder(contentPanel, 250, 400, 300, 400);

        contentPanel.add(createStartButton());

        imagePanel.add(contentPanel);
    }

    public JLabel createTitleLabel() {
        JLabel label = new JLabel("학식메뉴 알리미");
        label.setFont(new Font("",Font.CENTER_BASELINE,50));
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    public JButton createStartButton() {
        JButton button = new JButton("시작하기!");
        button.addActionListener(e -> {
            buttonPanel.setBackground(Color.LIGHT_GRAY);

            removeContainer(buttonPanel);
            removeContainer(contentPanel);

            setBorder(contentPanel, 250, 0, 0, 0);
            contentPanel.add(reccommendPanel());

            displayPanel.add(pageLabel("-홈 메뉴-"));
            imagePanel.add(displayPanel, BorderLayout.SOUTH);

            buttonPanel.add(createMenuPanel());

            updateDisplay();
        });
        return button;
    }

    private JPanel reccommendPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.add(greetingLabel());
        panel.add(confirmButton());
        return panel;
    }

    private JButton confirmButton() {
        JButton button = new JButton("추천메뉴 보러가기");
        button.addActionListener(e -> {
            recommendFrame = new JFrame("오늘의 추천메뉴");
            recommendFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            recommendFrame.setSize(400, 600);

            try {
                recommendFrame.add(reccomendPanel(), BorderLayout.NORTH);
                recommendFrame.add(reccomendMenuPanel());
                recommendFrame.add(preferencePanel(), BorderLayout.SOUTH);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            recommendFrame.pack();
            recommendFrame.setLocationRelativeTo(null);
            recommendFrame.setVisible(true);
        });
        return button;
    }

    private JPanel preferencePanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(0, 30));
        panel.add(preferenceLabel());
        return panel;
    }

    private JPanel reccomendMenuPanel() throws FileNotFoundException {
        JPanel panel = new JPanel();

        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        panel.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 30));

        Menu menu = recommendedRestaurant.menus().get(0);
        Nutrition nutrition = recommendedRestaurant.nutritions().get(0);

        panel.add(cafeteriaMenu(recommendedRestaurant, menu));
        panel.add(menuNutritions(nutrition, menu));

        return panel;
    }

    private JPanel reccomendPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 0, 15));
        panel.add(closeButton(recommendFrame));
        panel.add(suggestionLabel());
        return panel;
    }

    private JLabel preferenceLabel() {
        JLabel label = new JLabel(user.name() + "님이 선호하는 영양성분: " + favoriteNutrition);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private JLabel suggestionLabel() {
        JLabel label = new JLabel("오늘 이 메뉴는 어떠신가요?");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private JLabel greetingLabel() {
        JLabel label = new JLabel("안녕하세요 " + user.name() + "님, 오늘의 추천메뉴를 받아보시겠어요?");
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
        panel.setOpaque(false);
        panel.setBackground(Color.LIGHT_GRAY);
        panel.add(todayMenuButton());
        panel.add(recordMenuButton());
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
                setBorder(contentPanel, 20, 0, 0, 0);
                systemStatus.today();

                currentRestaurants = todayRestaurants;
                // 처음에 오늘메뉴
                // 날짜 선택 -> 식단보기 : 그날 메뉴

                removeContainer(buttonPanel);
                removeContainer(contentPanel);
                removeContainer(displayPanel);

                buttonPanel.add(menuOptionPanel());

                contentPanel.add(datePanel(), BorderLayout.NORTH);
                contentPanel.add(menuPanel());

                displayPanel.add(pageLabel("-오늘의 메뉴-"));

            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    private JLabel pageLabel(String pageContent) {
        JLabel label = new JLabel(pageContent);
        label.setForeground(Color.RED);
        return label;
    }

    private JPanel datePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.add(dateLabel());
        panel.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        return panel;
    }

    private JLabel dateLabel() {
        JLabel label = new JLabel(systemStatus.date() + "일 학식메뉴");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Courier",Font.BOLD,20));
        return label;
    }

    //
    //
    //
    //
    //

    public JPanel menuOptionPanel() {              //TODO : 정렬 버튼이 담긴 패널
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBackground(Color.LIGHT_GRAY);
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
            removeContainer(displayPanel);

            contentPanel.add(datePanel(), BorderLayout.NORTH);
            contentPanel.add(menuPanel);

            displayPanel.add(pageLabel("-" + nutrition + " 정렬-"));

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
        removeContainer(displayPanel);

        setBorder(contentPanel, 250, 0, 0, 0);

        buttonPanel.add(createMenuPanel());
        contentPanel.add(reccommendPanel());
        displayPanel.add(pageLabel("-홈 메뉴-"));

        updateDisplay();
    }

    public JPanel menuPanel() throws FileNotFoundException {           // TODO : 메뉴와 영양성분이 배치되는 패널
        menuPanel = new JPanel();
        menuPanel.setOpaque(false);
        menuPanel.setLayout(new GridLayout(1, 3));
        menuPanel.add(geumjeongPanel());
        menuPanel.add(studentHallMenuPanel());
        menuPanel.add(staffCafeteriaMenuPanel());

        return menuPanel;
    }

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
        panel.setOpaque(false);
        panel.setBackground(Color.LIGHT_GRAY);

        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));

        panel.setLayout(new FlowLayout(FlowLayout.LEADING, 50, 50));

        Menu menu = restaurant.selectMenu();

        Nutrition nutrition = restaurant.selectNutrition();

        panel.add(cafeteriaMenu(restaurant, menu));
        panel.add(menuNutritions(nutrition, menu));

        updateDisplay();
        return panel;
    }

    public JPanel cafeteriaMenu(Restaurant restaurant, Menu menu)  // TODO : 메뉴 생성하는 패널!
            throws FileNotFoundException {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridLayout(8, 1, 0, 30));

        panel.add(restaurantNameLabel(restaurant));
        panel.add(new JLabel(restaurant.price() + "원"));
        panel.add(new JLabel(menu.rice()));
        panel.add(mainMenuLabel(menu));
        panel.add(new JLabel(menu.sideMenu()));
        panel.add(new JLabel(menu.soup()));
        panel.add(new JLabel(menu.gimchi()));

        JButton button = new JButton();
        button.setBorderPainted(false);
        panel.add(button);

        return panel;
    }

    private JLabel restaurantNameLabel(Restaurant restaurant) {
        JLabel label = new JLabel(restaurant.name());
        label.setFont(new Font("Courier",Font.BOLD,17));
        return label;
    }

    private JLabel mainMenuLabel(Menu menu) {
        JLabel label = new JLabel(menu.mainMenu());
        label.setFont(new Font("Courier",Font.BOLD,14));
        label.setForeground(Color.ORANGE);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return label;
    }

    public JPanel menuNutritions(Nutrition nutrition, Menu menu) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new GridLayout(9, 1, 0, 21));

        panel.add(new JLabel());
        panel.add(new JLabel("메인 메뉴 영양성분 (100g당)"));
        panel.add(new JLabel(Nutrition.CARBONHYDRATE + ": " + nutrition.carbonHydrate()));
        panel.add(new JLabel(Nutrition.SUGAR + ": " + nutrition.sugar()));
        panel.add(new JLabel(Nutrition.PROTEIN + ": " + nutrition.protein()));
        panel.add(new JLabel(Nutrition.FAT + ": " + nutrition.fat()));
        panel.add(new JLabel(Nutrition.SATURATEDFAT + ": " + nutrition.saturatedFat()));
        panel.add(new JLabel(Nutrition.CALORIES + ": " + nutrition.calories()));

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
            text.setEditable(false);
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
                text.setText(new DatePicker(calendarFrame).setPickedDate());
            }
        });
        return button;
    }

    private JButton showMenusButton(JTextField text, JFrame calendarFrame) {
        JButton button = new JButton("식단 보기");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String date = text.getText();
                if (!date.equals("")) {
                    systemStatus.setDate(date);

                    systemStatus.initCurrentRestaurant(restaurants, currentRestaurants);

                    removeContainer(contentPanel);
                    removeContainer(menuPanel);
                    removeContainer(displayPanel);

                    try {
                        contentPanel.add(datePanel(), BorderLayout.NORTH);
                        contentPanel.add(menuPanel());
                        displayPanel.add(pageLabel("-오늘의 메뉴-"));
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                    updateDisplay();

                    calendarFrame.dispose();
                }
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
                    JFrame alert = new JFrame("warning");
                    alert.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    alert.add(alertPanel(alert));

                    alert.pack();
                    alert.setLocationRelativeTo(null);
                    alert.setVisible(true);
                }
            }
        });
        return button;
    }

    private JPanel alertPanel(JFrame alert) {
        JPanel panel = new JPanel();
        panel.add(new JLabel("오늘은 이미 입력하셨습니다!"));

        JButton button = new JButton("확인");
        button.addActionListener(e -> {
            alert.dispose();
        });
        panel.add(button);
        return panel;
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

            alertFrame.add(recordLabel(), BorderLayout.NORTH);
            alertFrame.add(confirmPanel(checkBox, cafeteria));

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

            Menu recordedMenu = switch (cafeteria) {
                case "금정회관" -> todayRestaurants.get(0).menus().get(0);
                case "학생회관" -> todayRestaurants.get(1).menus().get(0);
                case "교직원식당" -> todayRestaurants.get(2).menus().get(0);
                default -> null;
            };

            recordedMenus.add(recordedMenu);

            Nutrition recordedNutrition = switch (cafeteria) {
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
}

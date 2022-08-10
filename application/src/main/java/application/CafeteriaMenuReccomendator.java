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
    private final RecordLoader recordLoader = new RecordLoader();
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

    public static void main(String[] args) throws FileNotFoundException {
        CafeteriaMenuReccomendator application = new CafeteriaMenuReccomendator();

        application.run();
    }

    public void run() throws FileNotFoundException {
        prepareObjects();

        initMainFrame();

        initButtonPanel();

        initContentPanel();

        initMenuPanel();

        frame.setVisible(true);
    }

    private void prepareObjects() throws FileNotFoundException {
        urlRepository = new URLRepository();
        loader = new Loader();
        sort = new Sort();
        systemStatus = new SystemStatus();
        systemStatus.today();

        restaurants = new ArrayList<>();
        todayRestaurants = new ArrayList<>();

        recordedMenus = recordLoader.loadMenus();
        systemStatus.isRecorded(recordedMenus);

        recordedNutritions = recordLoader.loadNutritions();
    }

    private void initMainFrame() {
        frame = new JFrame("학식메뉴 알리미");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
    }

    public void initMenuPanel() {

    }

    public void initButtonPanel() {
        buttonPanel = new JPanel();

        buttonPanel.add(createTitleLabel());

        frame.add(buttonPanel, BorderLayout.NORTH);
    }

    public void initContentPanel() {
        contentPanel = new JPanel();

        contentPanel.setLayout(new BorderLayout());

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
        setBorder(contentPanel, 300, 400, 300, 400);
        button.addActionListener(e -> {
            removeContainer(buttonPanel);
            removeContainer(contentPanel);

//            MenuPanel menuPanel = new MenuPanel(frame, buttonPanel, contentPanel);

            buttonPanel.add(createMenuPanel());

            updateDisplay();
        });
        return button;
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

        return panel;
    }

    //
    //
    //
    //
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

        panel.add(closeButton());

        return panel;
    }

    //제출버튼 삭제!
//                try {
//                    recordLoader.saveMenu(.get(0));
//                    recordLoader.saveNutrition(nutritions.get(0));
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }


    private JButton closeButton() {
        JButton button = new JButton("돌아가기");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                recordFrame.dispose();
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


    public JButton todayMenuButton() {
        JButton button = new JButton("오늘의 메뉴 확인하기");
        button.addActionListener(e -> {
            try {
                resetRestaurants();

                removeContainer(buttonPanel);
                removeContainer(contentPanel);

                buttonPanel.add(menuOptionPanel());

                contentPanel.add(menuPanel());
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        return button;
    }

    public void resetRestaurants() {
        for (int i = 0; i < restaurants.size(); i += 1) {
            restaurants.remove(restaurants.get(0));
        }
    }

    public JPanel menuOptionPanel() {              //TODO : 정렬 버튼이 담긴 패널
        JPanel panel = new JPanel();

        panel.add(selectDateButton());
        panel.add(sortButton(Nutrition.PROTEIN));
        panel.add(sortButton(Nutrition.CALORIES));
        panel.add(sortButton(Nutrition.SATURATEDFAT));
        panel.add(backToMenuButton());
        return panel;
    }

    public JButton sortButton(String nutrition) {
        JButton button = new JButton(nutrition + " 정렬");
        button.addActionListener(e -> {
            String[] sortedCafeteriaNames = sort.sortByNutrition(restaurants, nutrition);

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

    public JButton backToMenuButton() {
        JButton button = new JButton("돌아가기");
        button.addActionListener(e -> {
            removeContainer(buttonPanel);
            removeContainer(menuPanel);
            removeContainer(contentPanel);

            buttonPanel.add(createMenuPanel());

            updateDisplay();
        });
        return button;
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
        File menuFile = new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/" +
                "application/src/main/resources/menus/금정회관.csv");

        File nutritionFile = new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk" +
                "/application/src/main/resources/nutritions/금정회관영양성분.csv");

        List<Menu> menus = loader.loadMenus(menuFile);

        List<Nutrition> nutritions = loader.loadNutritions(nutritionFile);

        Restaurant geumjeong = new Restaurant("금정회관", menus, nutritions, 3500);

        restaurants.add(geumjeong);

        return cafeteriaPanel(geumjeong);
    }

    public JPanel studentHallMenuPanel() throws FileNotFoundException {
        File menuFile = new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/" +
                "application/src/main/resources/menus/학생회관.csv");

        File nutritionFile = new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk" +
                "/application/src/main/resources/nutritions/학생회관영양성분.csv");
        List<Menu> menus = loader.loadMenus(menuFile);


        List<Nutrition> nutritions = loader.loadNutritions(nutritionFile);


        Restaurant studentHall = new Restaurant("학생회관", menus, nutritions, 5500);

        restaurants.add(studentHall);

        return cafeteriaPanel(studentHall);
    }

    public JPanel staffCafeteriaMenuPanel() throws FileNotFoundException {
        File menuFile = new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk/" +
                "application/src/main/resources/menus/교직원식당.csv");

        File nutritionFile = new File("/Users/jingwook/Desktop/study/programming/megaptera/web-02-project01-JiNookk" +
                "/application/src/main/resources/nutritions/교직원식당영양성분.csv");

        List<Menu> menus = loader.loadMenus(menuFile);
        //데이터베이스를 읽어옴 -> 일주일치 메뉴가 저장

        List<Nutrition> nutritions = loader.loadNutritions(nutritionFile);

        Restaurant staffCafeteria = new Restaurant("교직원식당", menus, nutritions, 5500);

        restaurants.add(staffCafeteria);

        return cafeteriaPanel(staffCafeteria);
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

        setBorder(contentPanel, 0, 0, 0, 0);
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

                removeContainer(contentPanel);
                removeContainer(menuPanel);
                resetRestaurants();

                try {
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
}

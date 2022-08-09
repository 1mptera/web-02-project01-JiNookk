package frame;

import models.Menu;
import models.Nutrition;
import panels.CheckPanel;

import javax.swing.*;
import java.util.List;

public class RecordFrame extends JFrame{
    public RecordFrame(List<Menu> menus, List<Nutrition> nutritionLists){
        this.setTitle("기록하기");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,300);
        this.setLocationRelativeTo(null);

        CheckPanel checkPanel = new CheckPanel(menus,nutritionLists,this);
        this.add(checkPanel);

        this.setVisible(true);
    }

}

package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {
    @Test
    void creation (){
        Menu menu = new Menu("rice", "mainMenu","sideMenu","soup" , "gimchi");
    }


}
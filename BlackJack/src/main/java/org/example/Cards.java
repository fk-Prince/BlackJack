package org.example;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Cards {
    public Image getCard(int number) {
        switch (number) {
            case 1:
                return new ImageIcon(getClass().getResource("/Image/1.png")).getImage();
            case 2:
                return new ImageIcon(getClass().getResource("/Image/2.png")).getImage();
            case 3:
                return new ImageIcon(getClass().getResource("/Image/3.png")).getImage();
            case 4:
                return new ImageIcon(getClass().getResource("/Image/4.png")).getImage();
            case 5:
                return new ImageIcon(getClass().getResource("/Image/5.png")).getImage();
            case 6:
                return new ImageIcon(getClass().getResource("/Image/6.png")).getImage();
            case 7:
                return new ImageIcon(getClass().getResource("/Image/7.png")).getImage();
            case 8:
                return new ImageIcon(getClass().getResource("/Image/8.png")).getImage();
            case 9:
                return new ImageIcon(getClass().getResource("/Image/9.png")).getImage();
            case 10:
                Random random = new Random();
                int num = random.nextInt(4) + 1;
                switch (num) {
                    case 1:
                        return new ImageIcon(getClass().getResource("/Image/10.png")).getImage();
                    case 2:
                        return new ImageIcon(getClass().getResource("/Image/11.png")).getImage();
                    case 3:
                        return new ImageIcon(getClass().getResource("/Image/12.png")).getImage();
                    case 4:
                        return new ImageIcon(getClass().getResource("/Image/13.png")).getImage();

                }
        }
        return null;
    }
}

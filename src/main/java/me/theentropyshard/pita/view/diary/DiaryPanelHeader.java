package me.theentropyshard.pita.view.diary;

import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.view.ThemeManager;
import me.theentropyshard.pita.view.component.SimpleButton;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class DiaryPanelHeader extends JPanel {
    public DiaryPanelHeader(DiaryPanel diaryPanel) {
        this.setLayout(new MigLayout("", "push[center]push", "[center]"));

        ThemeManager tm = Pita.getPita().getThemeManager();
        this.setBackground(tm.getColor("mainColor"));

        SimpleButton btn1 = new SimpleButton("<");
        btn1.setRoundCorners(true);
        btn1.setSquareSides(true);

        SimpleButton btn2 = new SimpleButton(">");
        btn2.setRoundCorners(true);
        btn2.setSquareSides(true);

        this.add(btn1);
        this.add(btn2);
    }
}

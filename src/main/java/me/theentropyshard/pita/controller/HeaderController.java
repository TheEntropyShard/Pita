/*      Pita. A simple desktop client for NetSchool by irTech
 *      Copyright (C) 2022-2023 TheEntropyShard
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.theentropyshard.pita.controller;

import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.netschoolapi.models.UserSession;
import me.theentropyshard.pita.netschoolapi.utils.models.IntIdName;
import me.theentropyshard.pita.utils.AbstractCallback;
import me.theentropyshard.pita.utils.SwingUtils;
import me.theentropyshard.pita.utils.Utils;
import me.theentropyshard.pita.view.*;
import me.theentropyshard.pita.view.component.PGradientLabel;
import me.theentropyshard.pita.view.component.PSimpleButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Call;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class HeaderController {
    private static final Logger LOG = LogManager.getLogger(HeaderController.class);

    private final Header header;

    public HeaderController(Header header) {
        this.header = header;

        header.getInfoLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AppWindow.window.getGlassPane().setVisible(true);

                ActiveSessionsPanel panel = new ActiveSessionsPanel();
                panel.loadData();
                panel.revalidate();

                Dimension preferredSize = panel.getPreferredSize();
                if (preferredSize.height > UIConstants.DEFAULT_HEIGHT) {
                    panel.setPreferredSize(new Dimension(preferredSize.width, UIConstants.DEFAULT_HEIGHT));
                }

                header.getInfoLabel().setText(
                        Utils.getTodaysDateRussian() + " - В системе работает " + panel.getActiveSessions() + " чел."
                );

                SwingUtils.newDialog("Список пользователей в сети", true, panel).setVisible(true);

                AppWindow.window.getGlassPane().setVisible(false);
            }
        });

        header.getSchoolNameLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AppWindow.window.getGlassPane().setVisible(true);

                SchoolInfoPanel panel = new SchoolInfoPanel();
                panel.loadData();
                panel.revalidate();

                Dimension preferredSize = panel.getPreferredSize();
                if (preferredSize.height > UIConstants.DEFAULT_HEIGHT) {
                    panel.setPreferredSize(new Dimension(preferredSize.width, UIConstants.DEFAULT_HEIGHT));
                }

                SwingUtils.newDialog("Карточка образовательной организации", true, panel).setVisible(true);

                AppWindow.window.getGlassPane().setVisible(false);
            }
        });

        header.getExitLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Call<Void> logout = NetSchoolAPI.authAPI.logout();
                try {
                    logout.execute();
                } catch (IOException ex) {
                    LOG.error(ex);
                }
                AppWindow.window.switchView(LoginView.class.getName());
            }
        });
    }

    public void loadData() {
        Call<Integer> umcCall = NetSchoolAPI.mailAPI.getUnreadMessagesCount(NetSchoolAPI.userId);
        umcCall.enqueue(new AbstractCallback<Integer>("UnreadMessagesCount") {
            @Override
            public void handleResponse(Integer i) {
                SwingUtilities.invokeLater(() -> {
                    PSimpleButton mailButton = header.getMailButton();
                    if (i > 0) {
                        mailButton.setText("Почта - " + i + " непрочитанных");
                    } else {
                        mailButton.setText("Почта");
                    }
                });
            }
        });

        Call<IntIdName[]> yearlistCall = NetSchoolAPI.utilsAPI.getYearlist();
        yearlistCall.enqueue(new AbstractCallback<IntIdName[]>("Yearlist") {
            @Override
            public void handleResponse(IntIdName[] intIdNames) {
                SwingUtilities.invokeLater(() -> {
                    PGradientLabel currentYearLabel = header.getCurrentYearLabel();
                    currentYearLabel.setText("текущий " + intIdNames[0].name + " уч.год");
                });
            }
        });

        Call<UserSession[]> activeSessionsCall = NetSchoolAPI.utilsAPI.getActiveSessions();
        activeSessionsCall.enqueue(new AbstractCallback<UserSession[]>("ActiveSessions") {
            @Override
            public void handleResponse(UserSession[] userSessions) {
                SwingUtilities.invokeLater(() -> {
                    PGradientLabel infoLabel = header.getInfoLabel();
                    infoLabel.setText(
                            Utils.getTodaysDateRussian() + " - В системе работает " + userSessions.length + " чел."
                    );
                });
            }
        });

        SwingUtilities.invokeLater(() -> {
            this.header.getSchoolNameLabel().setText(NetSchoolAPI.school.shortName);
            this.header.getUsernameLabel().setText(NetSchoolAPI.userName);
        });
    }
}

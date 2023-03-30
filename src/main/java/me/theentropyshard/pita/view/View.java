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

package me.theentropyshard.pita.view;

import me.theentropyshard.pita.Credentials;
import me.theentropyshard.pita.Pita;
import me.theentropyshard.pita.Utils;
import me.theentropyshard.pita.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.netschoolapi.exceptions.AuthException;
import me.theentropyshard.pita.netschoolapi.exceptions.SchoolNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

public final class View {
    private final JFrame frame;

    private final CardLayout rootLayout;
    private final JPanel root;

    private final LoginPanel loginPanel;
    private final MainPanel mainPanel;

    public View() {
        if(view != null) {
            throw new IllegalStateException("View is already shown");
        }
        view = this;

        this.frame = new JFrame("Pita");
        this.rootLayout = new CardLayout();
        this.root = new JPanel(this.rootLayout);

        this.root.setPreferredSize(new Dimension(UIConstants.DEFAULT_WIDTH, UIConstants.DEFAULT_HEIGHT));

        this.mainPanel = new MainPanel();
        this.root.add(this.mainPanel, MainPanel.class.getSimpleName());

        this.loginPanel = new LoginPanel();
        LoginPanel.LoginButtonCallback callback = (address, schoolName, login, password, passwordHashed) -> {
            Thread t = new Thread(() -> {
                try {
                    NetSchoolAPI.I.login(address, schoolName, login, password, passwordHashed);
                    if(!passwordHashed) {
                        Pita.getPita().saveCredentials(new Credentials(
                                address, schoolName, login, Utils.md5(password.getBytes(Charset.forName("windows-1251")))
                        ));
                    }
                    SwingUtilities.invokeLater(() -> {
                        this.rootLayout.show(this.root, MainPanel.class.getSimpleName());
                        this.mainPanel.showComponents();
                        this.loginPanel.reset();
                    });
                } catch (UnknownHostException e) {
                    this.loginPanel.wrongAddress();
                } catch (SchoolNotFoundException e) {
                    this.loginPanel.schoolNotFound();
                } catch (AuthException e) {
                    this.loginPanel.wrongCredentials();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        };
        this.loginPanel.setLoginButtonPressedCallback(callback);
        this.root.add(this.loginPanel, LoginPanel.class.getSimpleName());

        this.rootLayout.show(this.root, LoginPanel.class.getSimpleName());

        this.frame.getRootPane().setGlassPane(new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 127));
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                super.paintComponent(g);
            }
        });
        this.frame.add(this.root, BorderLayout.CENTER);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

    public CardLayout getRootLayout() {
        return this.rootLayout;
    }

    public JPanel getRoot() {
        return this.root;
    }

    public JFrame getFrame() {
        return this.frame;
    }

    private static View view;

    public static View getView() {
        return view;
    }
}

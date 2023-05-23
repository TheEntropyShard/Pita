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
import me.theentropyshard.pita.netschoolapi.announcements.models.Announcement;
import me.theentropyshard.pita.view.announcements.AnnouncementsView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.swing.*;
import java.util.Objects;
import java.util.Scanner;

public class AnnouncementsController {
    private static final Logger LOG = LogManager.getLogger(AnnouncementsController.class);

    private final AnnouncementsView announcementsView;

    public AnnouncementsController(AnnouncementsView announcementsView) {
        this.announcementsView = announcementsView;
    }

    public void loadAnnouncements() {
        Call<Announcement[]> announcementsCall = NetSchoolAPI.announcementsAPI.getAnnouncements(-1);
        announcementsCall.enqueue(new Callback<Announcement[]>() {
            @Override
            public void onResponse(@NotNull Call<Announcement[]> c, @NotNull Response<Announcement[]> r) {
                Announcement[] announcements = r.body();
                if (announcements != null) {
                    SwingUtilities.invokeLater(() -> {
                        announcementsView.setNumAnnouncements(0);
                        announcementsView.getPanel().removeAll();
                        for (Announcement a : announcements) {
                            announcementsView.addNewAnnouncement(a);
                        }
                        announcementsView.scrollToTop();
                        announcementsView.revalidate();
                    });
                } else {
                    Scanner scanner = new Scanner(Objects.requireNonNull(r.errorBody()).charStream());
                    StringBuilder builder = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        builder.append(scanner.nextLine());
                    }
                    scanner.close();
                    LOG.warn(
                            "Announcements response body is null, code: {}, message: {}",
                            r.code(), builder.toString()
                    );
                }
            }

            @Override
            public void onFailure(@NotNull Call<Announcement[]> c, @NotNull Throwable t) {
                LOG.error(t);
            }
        });
    }
}

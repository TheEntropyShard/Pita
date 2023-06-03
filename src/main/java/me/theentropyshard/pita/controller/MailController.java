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
import me.theentropyshard.pita.netschoolapi.mail.MailBox;
import me.theentropyshard.pita.netschoolapi.mail.MailField;
import me.theentropyshard.pita.netschoolapi.mail.MailHelper;
import me.theentropyshard.pita.netschoolapi.mail.models.MailRecord;
import me.theentropyshard.pita.netschoolapi.mail.models.MailRequestEntity;
import me.theentropyshard.pita.netschoolapi.mail.models.MailResponseEntity;
import me.theentropyshard.pita.utils.AbstractCallback;
import me.theentropyshard.pita.view.mail.MailPanelHeader;
import me.theentropyshard.pita.view.mail.MailView;
import retrofit2.Call;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class MailController {
    private static final DateTimeFormatter SENT_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final MailView mailView;

    private int totalMessages;
    private int page = 1;
    private int pageSize = 20;
    private MailBox mailBox = MailBox.BOX_INCOMING;
    private String searchText;
    private MailField searchField = MailField.AUTHOR;

    private MailRecord[] rows;

    public MailController(MailView mailView) {
        this.mailView = mailView;

        MailPanelHeader mph = mailView.getMailPanelHeader();
        mph.getLoadButton().addActionListener(e -> {
            this.loadData();
        });

        mph.getWriteButton().addActionListener(e -> {

        });

        mph.getDeleteButton().addActionListener(e -> {

        });
    }

    public void loadData() {
        this.mailView.getMailPanelHeader().removeAll();
        this.mailView.getMailListPanel()
                .addNewRecord("№", "От кого", "Тема", "Отправлено", false, true);
        this.mailView.getMailListPanel()
                .addNewRecord(" ", " ", " ", " ", false, true);

        //MailBox mailBox = this.mailView.getMailBox();

        MailRequestEntity.FilterContext.Filter[] selectedData = {
                new MailRequestEntity.FilterContext.Filter(
                        "MailBox", mailBox.getFilterValue(), mailBox.getFilterText()
                )
        };
        MailRequestEntity.FilterContext filterContext = new MailRequestEntity.FilterContext(selectedData);
        MailRequestEntity mre = new MailRequestEntity(
                /*filterContext,
                MailHelper.getDefaultFields(),
                this.mailView.getPage(),
                this.mailView.getPageSize(),
                new MailRequestEntity.Search(
                        this.mailView.getSearchField().getFieldId(),
                        this.mailView.getSearchText()
                ),
                new MailRequestEntity.Order(
                        "sent", false
                )*/
        );
        Call<MailResponseEntity> mailCall = NetSchoolAPI.mailAPI.getMail(mre);
        mailCall.enqueue(new AbstractCallback<MailResponseEntity>("Mail") {
            @Override
            public void handleResponse(MailResponseEntity mresp) {
                Call<Set<Integer>> umiCall = NetSchoolAPI.mailAPI.getUnreadMessagesIds(NetSchoolAPI.userId);
                umiCall.enqueue(new AbstractCallback<Set<Integer>>("UnreadMessagesIds") {
                    @Override
                    public void handleResponse(Set<Integer> unreadMessagesIds) {
                        MailRecord[] rows = mresp.rows;
                        for(int i = 0; i < rows.length; i++) {
                            MailRecord record = rows[i];
                            mailView.getMailListPanel().addNewRecord(
                                    String.valueOf(i + 1),
                                    record.author,
                                    record.subject,
                                    LocalDateTime.parse(record.sent).format(MailController.SENT_TIME_FORMATTER),
                                    !unreadMessagesIds.contains(record.id), false
                            );
                            mailView.revalidate();
                        }
                    }
                });
            }
        });
    }
}

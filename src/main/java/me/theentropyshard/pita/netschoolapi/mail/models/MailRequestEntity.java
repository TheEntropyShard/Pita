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

package me.theentropyshard.pita.netschoolapi.mail.models;

import java.util.Arrays;
import java.util.List;

public class MailRequestEntity {
    public FilterContext filterContext;
    public List<String> fields;
    public int page;
    public int pageSize;
    public Search search;
    public Order order;

    public MailRequestEntity() {

    }

    public MailRequestEntity(FilterContext filterContext, List<String> fields, int page, int pageSize, Search search, Order order) {
        this.filterContext = filterContext;
        this.fields = fields;
        this.page = page;
        this.pageSize = pageSize;
        this.search = search;
        this.order = order;
    }

    public static class Order {
        public String fieldId;
        public boolean order;

        public Order(String fieldId, boolean order) {
            this.fieldId = fieldId;
            this.order = order;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "fieldId='" + this.fieldId + '\'' +
                    ", order=" + this.order +
                    '}';
        }
    }

    public static class Search {
        public String fieldId;
        public String text;

        public Search(String fieldId, String text) {
            this.fieldId = fieldId;
            this.text = text;
        }

        @Override
        public String toString() {
            return "Search{" +
                    "fieldId='" + this.fieldId + '\'' +
                    ", text='" + this.text + '\'' +
                    '}';
        }
    }

    public static class FilterContext {
        public Filter[] selectedData;

        public FilterContext(Filter[] selectedData) {
            this.selectedData = selectedData;
        }

        public static class Filter {
            public String filterId;
            public String filterValue;
            public String filterText;

            public Filter() {

            }

            public Filter(String filterId, String filterValue, String filterText) {
                this.filterId = filterId;
                this.filterValue = filterValue;
                this.filterText = filterText;
            }

            @Override
            public String toString() {
                return "Filter{" +
                        "filterId='" + this.filterId + '\'' +
                        ", filterValue='" + this.filterValue + '\'' +
                        ", filterText='" + this.filterText + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "FilterContext{selectedData=" + Arrays.toString(this.selectedData) + "}";
        }
    }

    @Override
    public String toString() {
        return "MailRequestEntity{filterContext=" + this.filterContext + "}";
    }
}

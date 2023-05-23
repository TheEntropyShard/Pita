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

package me.theentropyshard.pita.netschoolapi.utils;

import me.theentropyshard.pita.netschoolapi.Urls;
import me.theentropyshard.pita.netschoolapi.models.MySettings;
import me.theentropyshard.pita.netschoolapi.models.SchoolCard;
import me.theentropyshard.pita.netschoolapi.models.UploadLimits;
import me.theentropyshard.pita.netschoolapi.utils.models.IntIdName;
import me.theentropyshard.pita.netschoolapi.utils.models.Term;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UtilsAPI {
    @GET(Urls.TERMS_SEARCH)
    Call<Term[]> getTerms();

    @GET(Urls.YEARS_CURRENT)
    Call<Object> yearsCurrent();

    @GET(Urls.REPORTS + "/studenttotal")
    Call<Object> reportsTotal();

    @GET(Urls.YEAR_LIST)
    Call<IntIdName[]> getYearlist();

    @GET(Urls.MY_SETTINGS)
    Call<MySettings> getMySettings();

    @GET(Urls.SCHOOL_INFO)
    Call<SchoolCard> getSchoolInfo();

    @GET(Urls.UPLOAD_LIMITS)
    Call<UploadLimits> getUploadLimits();
}

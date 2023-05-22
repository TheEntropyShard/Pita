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

package me.theentropyshard.pita.netschoolapi.auth;

import me.theentropyshard.pita.netschoolapi.Urls;
import me.theentropyshard.pita.netschoolapi.auth.models.GetData;
import me.theentropyshard.pita.netschoolapi.auth.models.Login;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthAPI {
    @POST(Urls.GET_DATA)
    Call<GetData> getData();

    @POST(Urls.LOGIN)
    @FormUrlEncoded
    Call<Login> login(
            @Field("LoginType") int loginType,
            @Field("scid") int scid,
            @Field("UN") String un,
            @Field("lt") String lt,
            @Field("pw2") String pw2,
            @Field("ver") String ver
    );

    @POST(Urls.LOGOUT)
    Call<Void> logout();
}

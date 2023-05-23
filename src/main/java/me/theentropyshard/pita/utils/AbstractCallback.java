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

package me.theentropyshard.pita.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Objects;
import java.util.Scanner;

public abstract class AbstractCallback<T> implements Callback<T> {
    private static final Logger LOG = LogManager.getLogger(AbstractCallback.class);

    private final String name;

    public AbstractCallback(String name) {
        this.name = name;
    }

    public abstract void handleResponse(T t);

    @Override
    public void onResponse(@NotNull Call<T> c, @NotNull Response<T> r) {
        T t = r.body();
        if (t != null) {
            this.handleResponse(t);
        } else {
            Scanner scanner = new Scanner(Objects.requireNonNull(r.errorBody()).charStream());
            StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
            }
            scanner.close();
            LOG.warn("'{}' response body is null, code: {}, message: {}", this.name, r.code(), builder.toString());
        }
    }

    @Override
    public void onFailure(@NotNull Call<T> c, @NotNull Throwable t) {
        LOG.error("[{}]: " + t, this.name);
    }
}

/*
 *     Food Inspector - Choose well to eat better
 *     Copyright (C) 2016  Frédéric Letellier
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.fredericletellier.foodinspector.data.source;

import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.CountryCategory;

import java.util.List;

/**
 * Main entry point for accessing countryCategory data.
 */
public interface CountryCategoryDataSource {

    interface GetCountryCategoryCallback {

        void onCountryCategoryLoaded(CountryCategory countryCategory);

        void onError(Throwable throwable);

    }

    interface CheckExistCountryCategoryCallback {

        void onCountryCategoryExisted(long id);

        void onCountryCategoryNotExisted();

    }

    interface AddCountryCategoryCallback {

        void onCountryCategoryAdded();

        void onError();

    }

    interface UpdateCountryCategoryCallback {

        void onCountryCategoryUpdated();

        void onError();

    }

    interface SaveCountryCategoryCallback {

        void onCountryCategorySaved();

        void onError();

    }

    interface GetCountryCategoryOfProductCallback {

        void onCountryCategoryOfProductLoaded(List<CountryCategory> countryCategories);

        void onError(Throwable throwable);

    }

    void getCountryCategory(@NonNull CountryCategory countryCategory, @NonNull GetCountryCategoryCallback getCountryCategoryCallback);

    void checkExistCountryCategory(@NonNull String categoryKey, @NonNull String countryKey, @NonNull CheckExistCountryCategoryCallback checkExistCountryCategoryCallback);

    void addCountryCategory(@NonNull CountryCategory countryCategory, @NonNull AddCountryCategoryCallback addCountryCategoryCallback);

    void updateCountryCategory(@NonNull CountryCategory countryCategory, @NonNull UpdateCountryCategoryCallback updateCountryCategoryCallback);

    void saveCountryCategory(@NonNull CountryCategory countryCategory, @NonNull SaveCountryCategoryCallback saveCountryCategoryCallback);

    void getCountryCategoryOfProduct(@NonNull String barcode, @NonNull String countryKey, @NonNull GetCountryCategoryOfProductCallback getCountryCategoryOfProductCallback);

}

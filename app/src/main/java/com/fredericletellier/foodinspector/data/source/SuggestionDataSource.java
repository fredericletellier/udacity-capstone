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

import com.fredericletellier.foodinspector.data.Suggestion;

/**
 * Main entry point for accessing suggestion data.
 */
public interface SuggestionDataSource {

    interface CheckExistSuggestionCallback {

        void onSuggestionExisted(long id);

        void onSuggestionNotExisted();

    }

    interface AddSuggestionCallback {

        void onSuggestionAdded();

        void onError();

    }

    interface UpdateSuggestionCallback {

        void onSuggestionUpdated();

        void onError();

    }

    interface SaveSuggestionCallback {

        void onSuggestionSaved();

        void onError();

    }

    void checkExistSuggestion(@NonNull String barcode, @NonNull String categoryKey, @NonNull String countryKey, @NonNull CheckExistSuggestionCallback checkExistSuggestionCallback);

    void addSuggestion(@NonNull Suggestion suggestion, @NonNull AddSuggestionCallback addSuggestionCallback);

    void updateSuggestion(@NonNull Suggestion suggestion, @NonNull UpdateSuggestionCallback updateSuggestionCallback);

    void saveSuggestion(@NonNull Suggestion suggestion, @NonNull SaveSuggestionCallback saveSuggestionCallback);

}

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

package com.fredericletellier.foodinspector.countrycategories;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public class CountryCategoriesFragment extends Fragment implements CountryCategoriesContract.View {

    @NonNull
    private static final String ARGUMENT_PRODUCT_BARCODE = "PRODUCT_BARCODE";

    public CountryCategoriesFragment() {
        // Requires empty public constructor
    }

    public static CountryCategoriesFragment newInstance(String productBarcode) {
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_PRODUCT_BARCODE, productBarcode);
        CountryCategoriesFragment fragment = new CountryCategoriesFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void setPresenter(CountryCategoriesContract.Presenter presenter) {

    }
}

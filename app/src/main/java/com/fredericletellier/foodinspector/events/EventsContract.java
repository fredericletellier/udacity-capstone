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

package com.fredericletellier.foodinspector.events;

import android.database.Cursor;

import com.fredericletellier.foodinspector.BasePresenter;
import com.fredericletellier.foodinspector.BaseView;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface EventsContract {

    interface View extends BaseView<Presenter> {

        void showEvents(Cursor events);

        void showLoadingEventsError();

        void showNoEvents();

        void setLoadingIndicator(Boolean active);

        void showEventDetailsUi(String barcode);

        void showScanUi();

        void showNewEventError();

    }

    interface Presenter extends BasePresenter {

        void loadEvents();

        void openEventDetails(String barcode);

        void clickOnFab();

        void newEvent(Barcode barcode);

    }
}


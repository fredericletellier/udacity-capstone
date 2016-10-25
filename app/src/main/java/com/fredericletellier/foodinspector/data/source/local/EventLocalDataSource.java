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

package com.fredericletellier.foodinspector.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.Event;
import com.fredericletellier.foodinspector.data.source.EventDataSource;
import com.fredericletellier.foodinspector.data.source.EventValues;
import com.fredericletellier.foodinspector.data.source.local.db.EventPersistenceContract;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class EventLocalDataSource implements EventDataSource {

    private static EventLocalDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private EventLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static EventLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new EventLocalDataSource(contentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void checkExistEvent(@NonNull String barcode, @NonNull CheckExistEventCallback checkExistEventCallback) {
        Cursor cursor = mContentResolver.query(
                EventPersistenceContract.EventEntry.buildEventUri(),
                null,
                EventPersistenceContract.EventEntry.COLUMN_NAME_BARCODE + " = ?",
                new String[]{barcode},
                null);

        if (cursor.moveToLast()) {
            long _id = cursor.getLong(cursor.getColumnIndex(EventPersistenceContract.EventEntry._ID));
            checkExistEventCallback.onEventExisted(_id);
        } else {
            checkExistEventCallback.onEventNotExisted();
        }
        cursor.close();
    }

    @Override
    public void addEvent(@NonNull Event Event, @NonNull EventDataSource.AddEventCallback addEventCallback) {
        checkNotNull(Event);

        ContentValues values = EventValues.from(Event);
        Uri uri = mContentResolver.insert(EventPersistenceContract.EventEntry.buildEventUri(), values);

        if (uri != null) {
            addEventCallback.onEventAdded();
        } else {
            addEventCallback.onError();
        }
    }

    @Override
    public void updateEvent(@NonNull Event Event, @NonNull EventDataSource.UpdateEventCallback updateEventCallback) {
        checkNotNull(Event);

        ContentValues values = EventValues.from(Event);

        String selection = EventPersistenceContract.EventEntry._ID + " LIKE ?";
        String[] selectionArgs = {Event.getAsStringId()};

        int rows = mContentResolver.update(EventPersistenceContract.EventEntry.buildEventUri(), values, selection, selectionArgs);

        if (rows != 0) {
            updateEventCallback.onEventUpdated();
        } else {
            updateEventCallback.onError();
        }
    }

    @Override
    public void saveEvent(@NonNull final Event Event, @NonNull final EventDataSource.SaveEventCallback saveEventCallback) {
        checkNotNull(Event);

        String barcode = Event.getBarcode();

        checkExistEvent(barcode, new CheckExistEventCallback() {

            @Override
            public void onEventExisted(long id) {
                Event.setId(id);
                updateEvent(Event, new EventDataSource.UpdateEventCallback() {
                    @Override
                    public void onEventUpdated() {
                        saveEventCallback.onEventSaved();
                    }

                    @Override
                    public void onError() {
                        saveEventCallback.onError();
                    }
                });
            }

            @Override
            public void onEventNotExisted() {
                addEvent(Event, new EventDataSource.AddEventCallback() {
                    @Override
                    public void onEventAdded() {
                        saveEventCallback.onEventSaved();
                    }

                    @Override
                    public void onError() {
                        saveEventCallback.onError();
                    }
                });
            }
        });
    }

    @Override
    public void saveScan(@NonNull String barcode, @NonNull SaveScanCallback saveScanCallback) {
        // no-op in local
    }

    @Override
    public void refreshEventsOnError(@NonNull RefreshEventsOnErrorCallback refreshEventsOnErrorCallback) {
        // no-op in local
    }

    @Override
    public void getEventsOnError(@NonNull GetEventsOnErrorCallback getEventsOnErrorCallback) {

        Cursor cursor = mContentResolver.query(
                EventPersistenceContract.EventEntry.buildEventUri(),
                null,
                EventPersistenceContract.EventEntry.COLUMN_NAME_STATUS + " = ?",
                new String[]{Event.STATUS_NO_NETWORK},
                null);

        if (cursor.moveToFirst()) {

            List<String> eventsOnError = new ArrayList<String>();
            do {
                Event event = Event.from(cursor);
                eventsOnError.add(event.getAsStringId());
            } while (cursor.moveToNext());

            getEventsOnErrorCallback.onEventsOnErrorLoaded(eventsOnError);
        } else {
            getEventsOnErrorCallback.onError();
        }
        cursor.close();
    }
}

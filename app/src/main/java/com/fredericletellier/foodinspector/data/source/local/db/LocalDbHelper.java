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

package com.fredericletellier.foodinspector.data.source.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Local.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String PRIMARY_KEY = " PRIMARY KEY";

    private static final String CREATE_TABLE = "CREATE TABLE ";

    private static final String FOREIGN_KEY = " FOREIGN KEY";

    private static final String REFERENCES = " REFERENCES ";

    private static final String OPEN_PARENTHESIS = " (";

    private static final String CLOSE_PARENTHESIS = " )";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_PRODUCT =
            CREATE_TABLE + ProductPersistenceContract.ProductEntry.TABLE_NAME + OPEN_PARENTHESIS +
                    ProductPersistenceContract.ProductEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                    ProductPersistenceContract.ProductEntry.COLUMN_NAME_BARCODE + TEXT_TYPE + COMMA_SEP +
                    ProductPersistenceContract.ProductEntry.COLUMN_NAME_PARSED + BOOLEAN_TYPE + COMMA_SEP +
                    ProductPersistenceContract.ProductEntry.COLUMN_NAME_BOOKMARKED + BOOLEAN_TYPE + COMMA_SEP +
                    ProductPersistenceContract.ProductEntry.COLUMN_NAME_GENERIC_NAME + TEXT_TYPE + COMMA_SEP +
                    ProductPersistenceContract.ProductEntry.COLUMN_NAME_PRODUCT_NAME + TEXT_TYPE + COMMA_SEP +
                    ProductPersistenceContract.ProductEntry.COLUMN_NAME_QUANTITY + TEXT_TYPE + COMMA_SEP +
                    ProductPersistenceContract.ProductEntry.COLUMN_NAME_NUTRITION_GRADES + TEXT_TYPE + COMMA_SEP +
                    ProductPersistenceContract.ProductEntry.COLUMN_NAME_PARSABLE_CATEGORIES + TEXT_TYPE + COMMA_SEP +
                    ProductPersistenceContract.ProductEntry.COLUMN_NAME_BRANDS + TEXT_TYPE + COMMA_SEP +
                    ProductPersistenceContract.ProductEntry.COLUMN_NAME_IMAGE_FRONT_SMALL_URL + TEXT_TYPE + COMMA_SEP +
                    ProductPersistenceContract.ProductEntry.COLUMN_NAME_IMAGE_FRONT_URL + TEXT_TYPE +
                    CLOSE_PARENTHESIS;

    private static final String SQL_CREATE_EVENT =
            CREATE_TABLE + EventPersistenceContract.EventEntry.TABLE_NAME + OPEN_PARENTHESIS +
                    EventPersistenceContract.EventEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                    EventPersistenceContract.EventEntry.COLUMN_NAME_TIMESTAMP + TEXT_TYPE + COMMA_SEP +
                    EventPersistenceContract.EventEntry.COLUMN_NAME_BARCODE + TEXT_TYPE + COMMA_SEP +
                    EventPersistenceContract.EventEntry.COLUMN_NAME_STATUS + TEXT_TYPE +
                    CLOSE_PARENTHESIS;

    private static final String SQL_CREATE_CATEGORY =
            CREATE_TABLE + CategoryPersistenceContract.CategoryEntry.TABLE_NAME + OPEN_PARENTHESIS +
                    CategoryPersistenceContract.CategoryEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                    CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_CATEGORY_KEY + TEXT_TYPE + COMMA_SEP +
                    CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_CATEGORY_NAME + TEXT_TYPE +
                    CLOSE_PARENTHESIS;

    private static final String SQL_CREATE_CATEGORYTAG =
            CREATE_TABLE + CategoryTagPersistenceContract.CategoryTagEntry.TABLE_NAME + OPEN_PARENTHESIS +
                    CategoryTagPersistenceContract.CategoryTagEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                    CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_BARCODE + TEXT_TYPE + COMMA_SEP +
                    CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_CATEGORY_KEY + TEXT_TYPE + COMMA_SEP +
                    CategoryTagPersistenceContract.CategoryTagEntry.COLUMN_NAME_RANK + INTEGER_TYPE +
                    CLOSE_PARENTHESIS;

    private static final String SQL_CREATE_COUNTRYCATEGORY =
            CREATE_TABLE + CountryCategoryPersistenceContract.CountryCategoryEntry.TABLE_NAME + OPEN_PARENTHESIS +
                    CountryCategoryPersistenceContract.CountryCategoryEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                    CountryCategoryPersistenceContract.CountryCategoryEntry.COLUMN_NAME_CATEGORY_KEY + TEXT_TYPE + COMMA_SEP +
                    CountryCategoryPersistenceContract.CountryCategoryEntry.COLUMN_NAME_COUNTRY_KEY + TEXT_TYPE + COMMA_SEP +
                    CountryCategoryPersistenceContract.CountryCategoryEntry.COLUMN_NAME_SUM_OF_PRODUCTS + INTEGER_TYPE +
                    CLOSE_PARENTHESIS;

    private static final String SQL_CREATE_SUGGESTION =
            CREATE_TABLE + SuggestionPersistenceContract.SuggestionEntry.TABLE_NAME + OPEN_PARENTHESIS +
                    SuggestionPersistenceContract.SuggestionEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                    SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_BARCODE + TEXT_TYPE + COMMA_SEP +
                    SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_CATEGORY_KEY + TEXT_TYPE + COMMA_SEP +
                    SuggestionPersistenceContract.SuggestionEntry.COLUMN_NAME_COUNTRY_KEY + TEXT_TYPE +
                    CLOSE_PARENTHESIS;

    public LocalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCT);
        db.execSQL(SQL_CREATE_EVENT);
        db.execSQL(SQL_CREATE_CATEGORY);
        db.execSQL(SQL_CREATE_CATEGORYTAG);
        db.execSQL(SQL_CREATE_COUNTRYCATEGORY);
        db.execSQL(SQL_CREATE_SUGGESTION);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required as at version 1
    }
}
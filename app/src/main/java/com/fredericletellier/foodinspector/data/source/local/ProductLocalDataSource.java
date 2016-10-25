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

import com.fredericletellier.foodinspector.data.Product;
import com.fredericletellier.foodinspector.data.source.ProductDataSource;
import com.fredericletellier.foodinspector.data.source.ProductValues;
import com.fredericletellier.foodinspector.data.source.local.db.ProductPersistenceContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class ProductLocalDataSource implements ProductDataSource {

    private static ProductLocalDataSource INSTANCE;

    private ContentResolver mContentResolver;

    // Prevent direct instantiation.
    private ProductLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    public static ProductLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (INSTANCE == null) {
            INSTANCE = new ProductLocalDataSource(contentResolver);
        }
        return INSTANCE;
    }

    @Override
    public void getProduct(@NonNull String barcode, @NonNull GetProductCallback getProductCallback) {
        // TODO
    }

    @Override
    public void getProducts(@NonNull String categoryKey, @NonNull String countryKey, @NonNull String nutritionGradeValue, @NonNull Integer offsetProducts, @NonNull Integer numberOfProducts, @NonNull GetProductsCallback getProductsCallback) {
        // TODO
    }

    @Override
    public void checkExistProduct(@NonNull String barcode, @NonNull CheckExistProductCallback checkExistProductCallback) {
       Cursor cursor = mContentResolver.query(
                ProductPersistenceContract.ProductEntry.buildProductUri(),
                new String[]{ProductPersistenceContract.ProductEntry._ID},
                ProductPersistenceContract.ProductEntry.COLUMN_NAME_BARCODE + " = ?",
                new String[]{barcode},
                null);

        if (cursor.moveToLast()) {
            long _id = cursor.getLong(cursor.getColumnIndex(ProductPersistenceContract.ProductEntry._ID));
            checkExistProductCallback.onProductExisted(_id);
        } else {
            checkExistProductCallback.onProductNotExisted();
        }
        cursor.close();
    }

    @Override
    public void addProduct(@NonNull Product Product, @NonNull ProductDataSource.AddProductCallback addProductCallback) {
        checkNotNull(Product);

        ContentValues values = ProductValues.from(Product);
        Uri uri = mContentResolver.insert(ProductPersistenceContract.ProductEntry.buildProductUri(), values);

        if (uri != null) {
            addProductCallback.onProductAdded();
        } else {
            addProductCallback.onError();
        }
    }

    @Override
    public void updateProduct(@NonNull Product Product, @NonNull ProductDataSource.UpdateProductCallback updateProductCallback) {
        checkNotNull(Product);

        ContentValues values = ProductValues.from(Product);

        String selection = ProductPersistenceContract.ProductEntry._ID + " LIKE ?";
        String[] selectionArgs = {Product.getAsStringId()};

        int rows = mContentResolver.update(ProductPersistenceContract.ProductEntry.buildProductUri(), values, selection, selectionArgs);

        if (rows != 0) {
            updateProductCallback.onProductUpdated();
        } else {
            updateProductCallback.onError();
        }
    }

    @Override
    public void saveProduct(@NonNull final Product Product, @NonNull final ProductDataSource.SaveProductCallback saveProductCallback) {
        checkNotNull(Product);

        String barcode = Product.getBarcode();

        checkExistProduct(barcode, new CheckExistProductCallback() {

            @Override
            public void onProductExisted(long id) {
                Product.setId(id);
                updateProduct(Product, new UpdateProductCallback() {
                    @Override
                    public void onProductUpdated() {
                        saveProductCallback.onProductSaved();
                    }

                    @Override
                    public void onError() {
                        saveProductCallback.onError();
                    }
                });
            }

            @Override
            public void onProductNotExisted() {
                addProduct(Product, new AddProductCallback() {
                    @Override
                    public void onProductAdded() {
                        saveProductCallback.onProductSaved();
                    }

                    @Override
                    public void onError() {
                        saveProductCallback.onError();
                    }
                });
            }
        });
    }

    @Override
    public void parseProduct(@NonNull String barcode, @NonNull ParseProductCallback parseProductCallback) {
        // TODO
    }

    @Override
    public void updateProductBookmark(@NonNull String barcode, @NonNull UpdateProductBookmarkCallback updateProductBookmarkCallback) {
        // TODO
    }
}

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

package com.fredericletellier.foodinspector.data.source.remote;

import android.support.annotation.NonNull;

import com.fredericletellier.foodinspector.data.Product;
import com.fredericletellier.foodinspector.data.ProductBarcode;
import com.fredericletellier.foodinspector.data.source.ProductDataSource;
import com.fredericletellier.foodinspector.data.source.remote.API.APIError;
import com.fredericletellier.foodinspector.data.source.remote.API.ErrorUtils;
import com.fredericletellier.foodinspector.data.source.remote.API.OpenFoodFactsAPIEndpointInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a remote db
 */
public class ProductRemoteDataSource implements ProductDataSource {

    private static ProductRemoteDataSource INSTANCE;

    // Prevent direct instantiation.
    private ProductRemoteDataSource() {
    }

    public static ProductRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProductRemoteDataSource();
        }
        return INSTANCE;
    }

    /**
     * Gets the product from remote data source
     * <p/>
     * Note: {@link GetProductCallback#onError(Throwable)} is fired if remote data sources fail to
     * get the data (HTTP error, IOException, IllegalStateException, ...)
     */
    @Override
    public void getProduct(@NonNull String barcode, @NonNull final GetProductCallback getProductCallback) {
        checkNotNull(barcode);
        checkNotNull(getProductCallback);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OpenFoodFactsAPIEndpointInterface.ENDPOINT_BARCODE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OpenFoodFactsAPIEndpointInterface apiService = retrofit.create(OpenFoodFactsAPIEndpointInterface.class);

        Call<ProductBarcode> call = apiService.getProduct(barcode);

        call.enqueue(new Callback<ProductBarcode>() {
            @Override
            public void onResponse(Call<ProductBarcode> call, Response<ProductBarcode> response) {
                if (response.isSuccessful()) {
                    ProductBarcode mProductBarcode = response.body();
                    Product mProduct = mProductBarcode.getProducts().get(0);
                    getProductCallback.onProductLoaded(mProduct);
                } else {
                    APIError e = ErrorUtils.parseError(response);
                    Throwable t = new Throwable(e.message(), new Throwable(String.valueOf(e.status())));
                    getProductCallback.onError(t);
                }
            }

            @Override
            public void onFailure(Call<ProductBarcode> call, Throwable t) {
                getProductCallback.onError(t);
            }
        });
    }

    @Override
    public void getProducts(@NonNull String categoryKey, @NonNull String countryKey, @NonNull String nutritionGradeValue, @NonNull Integer offsetProducts, @NonNull Integer numberOfProducts, @NonNull GetProductsCallback getProductsCallback) {
        //TODO Intégrer la partie remote de la procédure globale du repository
    }

    @Override
    public void addProduct(@NonNull Product product, @NonNull AddProductCallback addProductCallback) {
        //no-op in remote
    }

    @Override
    public void updateProduct(@NonNull Product product, @NonNull UpdateProductCallback updateProductCallback) {
        //no-op in remote
    }

    @Override
    public void saveProduct(@NonNull Product product, @NonNull SaveProductCallback saveProductCallback) {
        //no-op in remote
    }

    @Override
    public void parseProduct(@NonNull String barcode, @NonNull ParseProductCallback parseProductCallback) {
        //no-op in remote
    }

    @Override
    public void updateProductBookmark(@NonNull String barcode, @NonNull UpdateProductBookmarkCallback updateProductBookmarkCallback) {
        //no-op in remote
    }
}
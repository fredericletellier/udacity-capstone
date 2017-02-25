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

package com.fredericletellier.foodinspector.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.fredericletellier.foodinspector.R;
import com.fredericletellier.foodinspector.data.Product;
import com.squareup.picasso.Picasso;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductFragment extends Fragment implements ProductContract.ProductView {

    private ProductContract.Presenter mPresenter;

    protected View mProgressView;
    private ImageView mIvImageBig;
    private TextView mTxProductName;
    private TextView mTxGenericName;
    private TextView mTxBrands;
    private TextView mTxQuantity;
    private TextView mTxNutritionGrade;


    public ProductFragment() {
        // Requires empty public constructor
    }

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }


    @Override
    public void setPresenter(ProductContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.startProduct();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_frag, container, false);

        mProgressView = root.findViewById(android.R.id.progress);
        mIvImageBig = (ImageView) root.findViewById(R.id.ivImageBig);
        mTxProductName = (TextView) root.findViewById(R.id.txProductName);
        mTxGenericName = (TextView) root.findViewById(R.id.txGenericName);
        mTxBrands = (TextView) root.findViewById(R.id.txBrands);
        mTxQuantity = (TextView) root.findViewById(R.id.txQuantity);
        mTxNutritionGrade = (TextView) root.findViewById(R.id.txNutritionGrade);
        return root;
    }

    @Override
    public void setLoadingIncator(Boolean active) {
        if (active){
            //show loading indicator
            mProgressView.startAnimation(AnimationUtils.loadAnimation(this.getContext(), android.R.anim.fade_in));
            mProgressView.setVisibility(View.VISIBLE);

        } else {
            // hide loading indicator
            mProgressView.startAnimation(AnimationUtils.loadAnimation(this.getContext(), android.R.anim.fade_out));
            mProgressView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProduct(Product product) {
        Picasso.with(this.getContext())
                .load(product.getmImageFrontUrl())
                .into(mIvImageBig);

        mTxProductName.setText(product.getmProductName());
        mTxGenericName.setText(product.getmGenericName());
        mTxBrands.setText(product.getmBrands());
        mTxQuantity.setText(product.getmQuantity());
        mTxNutritionGrade.setText(product.getmNutritionGrades());
    }

    @Override
    public void showError() {
        //TODO
    }
}

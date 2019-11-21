package com.everyrupee.data.model.other;

import java.util.ArrayList;

/**
 * Created by aman on 01-08-2018.
 */

public class HomeHomeTransactionModel extends HomeTransactionDetailModel {
    private int imgResId;
    private int imgBgColor;


    public HomeHomeTransactionModel() {

    }

    public int getImgBgColor() {
        return imgBgColor;
    }

    public void setImgBgColor(int imgBgColor) {
        this.imgBgColor = imgBgColor;
    }



    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public ArrayList<HomeHomeTransactionModel> getDummyExpenseData() {
        ArrayList<HomeHomeTransactionModel> list = new ArrayList<>();

        HomeHomeTransactionModel item2 = new HomeHomeTransactionModel();
        item2.setTitle("Entertainment");
        list.add(item2);

        HomeHomeTransactionModel item3 = new HomeHomeTransactionModel();
        item3.setTitle("Shopping");
        list.add(item3);

        HomeHomeTransactionModel item4 = new HomeHomeTransactionModel();
        item4.setTitle("Transport");
        list.add(item4);

        HomeHomeTransactionModel item5 = new HomeHomeTransactionModel();
        item5.setTitle("Health");
        list.add(item5);

        HomeHomeTransactionModel item6 = new HomeHomeTransactionModel();
        item6.setTitle("Gifts");
        list.add(item6);

        return list;
    }

    public ArrayList<HomeHomeTransactionModel> getDummyIncomeData() {
        ArrayList<HomeHomeTransactionModel> list = new ArrayList<>();

        HomeHomeTransactionModel item8 = new HomeHomeTransactionModel();
        item8.setTitle("Salery");
        list.add(item8);

        HomeHomeTransactionModel item9 = new HomeHomeTransactionModel();
        item9.setTitle("Bonus");
        list.add(item9);

        return list;
    }

}

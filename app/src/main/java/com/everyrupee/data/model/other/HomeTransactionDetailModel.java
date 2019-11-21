package com.everyrupee.data.model.other;

/**
 * Created by aman on 30-09-2018.
 */

public class HomeTransactionDetailModel {
    private String title;
    private int subTitle;
    private String currency;
    private String money;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(int subTitle) {
        this.subTitle = subTitle;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}

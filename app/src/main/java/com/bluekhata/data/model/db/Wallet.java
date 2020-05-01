package com.bluekhata.data.model.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "wallet_table")
public class Wallet {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "walletId")
    private int id;

    @NonNull
    @ColumnInfo(name = "walletTitle")
    private String title;

    @Ignore
    public Wallet(String title){
        this.title = title;
    }

    public Wallet(int id, String title){
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public static List<Wallet> getList(){
        List<Wallet> wallets = new ArrayList<>();
        wallets.add(new Wallet("Cash"));
        wallets.add(new Wallet("SBI"));
        wallets.add(new Wallet("Paytm"));
        return wallets;
    }
}

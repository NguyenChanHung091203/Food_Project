package com.example.food_project.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.food_project.Domain.Foods;

import java.util.ArrayList;


public class ManagmentWishList {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentWishList(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFoodWishList(Foods item) {
        ArrayList<Foods> listpop = getWishList();
        boolean existAlready = false;

        for (Foods food : listpop) {
            if (food.getTitle().equals(item.getTitle())) {
                existAlready = true;
                break;
            }
        }

        if (!existAlready) {
            listpop.add(item);
            tinyDB.putListObject("WishList", listpop);
            Toast.makeText(context, "Added to your WishList", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Item already in your WishList", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteFood(Foods item) {
        ArrayList<Foods> listpop = tinyDB.getListObject("WishList");
        boolean existAlready = false;
        int n = 0;

        // Tìm kiếm đối tượng cần xóa trong danh sách
        for (int i = 0; i < listpop.size(); i++) {
            if (listpop.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        // Nếu đối tượng tồn tại, xóa nó
        if(existAlready){
            listpop.remove(n);
            tinyDB.putListObject("WishList", listpop);
            Toast.makeText(context, "Removed from your WishList", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Item not found in your WishList", Toast.LENGTH_SHORT).show();
        }
    }


    public ArrayList<Foods> getWishList() {
        return tinyDB.getListObject("WishList");
    }
}

package com.rovines.online.store.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rovines.online.store.models.CartItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static final String PREFERENCE_NAME = "CartPreferences";
    private static final String CART_ITEMS_KEY = "cartItems";

    private Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static CartManager instance;

    private CartManager(Context context) {
        gson = new Gson();
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context);
        }
        return instance;
    }

    public List<CartItem> getAllCartItem() {
        String cartItemJson = sharedPreferences.getString(CART_ITEMS_KEY, null);

        if (cartItemJson == null) {
            return new ArrayList<>();
        }

        Type listType = new TypeToken<List<CartItem>>() {
        }.getType();
        return gson.fromJson(cartItemJson, listType);
    }

    public CartItem getCartItemById(Integer id) {
        String cartItemJson = sharedPreferences.getString(CART_ITEMS_KEY, null);
        if (cartItemJson != null) {
            Type listType = new TypeToken<List<CartItem>>() {
            }.getType();
            List<CartItem> cartItems = gson.fromJson(cartItemJson, listType);

            for (CartItem item : cartItems) {
                if (item.getId().equals(id)) {
                    return item;
                }
            }
        }
        return null;
    }

    public void addToCart(CartItem cartItem) {
        List<CartItem> cartItems = getAllCartItem();

        CartItem cartItemExist = getCartItemById(cartItem.getId());
        if (cartItemExist != null) {
            cartItem.setQuantity(cartItemExist.getQuantity() + cartItem.getQuantity());
            cartItems.remove(cartItemExist);
            cartItems.add(cartItem);
        } else {
            cartItems.add(cartItem);
        }

        String cartItemsJson = gson.toJson(cartItems);
        editor.putString(CART_ITEMS_KEY, cartItemsJson);
        editor.apply();
    }

    public void updateQuantity(CartItem cartItem, Integer quantity) {
        List<CartItem> cartItems = getAllCartItem();
        CartItem cartItemExist = getCartItemById(cartItem.getId());
        if (cartItemExist != null) {
            if (cartItemExist.getQuantity() + quantity > 0) {
                cartItem.setQuantity(cartItemExist.getQuantity() + quantity);
                cartItems.remove(cartItemExist);
                cartItems.add(cartItem);
            } else {
                cartItems.remove(cartItemExist);
            }
        }

        String cartItemsJson = gson.toJson(cartItems);
        editor.putString(CART_ITEMS_KEY, cartItemsJson);
        editor.apply();
    }


    public void clearCart() {
        editor.remove(CART_ITEMS_KEY);
        editor.apply();
    }

    public void removeFromCart(Integer id) {
        List<CartItem> cartItems = getAllCartItem();
        cartItems.remove(getCartItemById(id));

        String cartItemsJson = gson.toJson(cartItems);
        editor.putString(CART_ITEMS_KEY, cartItemsJson);
        editor.apply();
    }
}
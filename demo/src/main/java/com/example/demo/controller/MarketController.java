package com.example.demo.controller;

import com.example.demo.database.DatabaseHandler;
import com.example.demo.model.Character;
import com.example.demo.model.Item;
import com.example.demo.model.Market;

import javax.inject.Named;

import static org.bitbucket.dollar.Dollar.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Named
public class MarketController {
    private Random random = new Random();

    public int getRandomIndex() {
        return random.nextInt(ItemController.getItems().size());
    }


    public static void randomizeMarketItems(Character character){
        Market market = new Market();
        List<Integer> numbers =  $(4, ItemController.getItems().size()).toList();
        Collections.shuffle(numbers);
        market.setMarketSlot1(numbers.get(0));
        market.setMarketSlot2(numbers.get(1));
        market.setMarketSlot3(numbers.get(2));
        market.setMarketSlot4(numbers.get(3));
        market.setMarketSlot5(numbers.get(4));
        market.setMarketSlot6(numbers.get(5));

        character.setMarket(market);

    }
    public static void randomizeMarketItem1(Character character){
        Market market  = character.getMarket();
        List<Integer> numbers =  $(4, ItemController.getItems().size()).toList();
        Collections.shuffle(numbers);
        market.setMarketSlot1(numbers.get(0));
        character.setMarket(market);

    }
    public static void randomizeMarketItem2(Character character){
        Market market  = character.getMarket();
        List<Integer> numbers =  $(4, ItemController.getItems().size()).toList();
        Collections.shuffle(numbers);
        market.setMarketSlot2(numbers.get(1));
        character.setMarket(market);

    }
    public static void randomizeMarketItem3(Character character){
        Market market  = character.getMarket();
        List<Integer> numbers =  $(4, ItemController.getItems().size()).toList();
        Collections.shuffle(numbers);
        market.setMarketSlot3(numbers.get(2));
        character.setMarket(market);
    }
    public static void randomizeMarketItem4(Character character){
        Market market  = character.getMarket();
        List<Integer> numbers =  $(4, ItemController.getItems().size()).toList();
        Collections.shuffle(numbers);
        market.setMarketSlot4(numbers.get(3));
        character.setMarket(market);
    }
    public static void randomizeMarketItem5(Character character){
        Market market  = character.getMarket();
        List<Integer> numbers =  $(4, ItemController.getItems().size()).toList();
        Collections.shuffle(numbers);
        market.setMarketSlot5(numbers.get(4));
        character.setMarket(market);
    }
    public static void randomizeMarketItem6(Character character){
        Market market  = character.getMarket();
        List<Integer> numbers =  $(4, ItemController.getItems().size()).toList();
        Collections.shuffle(numbers);
        market.setMarketSlot6(numbers.get(5));
        character.setMarket(market);
    }

    public void buyItem(Character character, Item item, String marketSlot) {
        String firstFreeSpace = ItemController.findFirstFreeSpaceInInventory(character);
        if (!firstFreeSpace.equals("none") && character.getCash() > item.getPrice()) {
            switch (marketSlot) {
                case "slot1":
                    randomizeMarketItem1(character);
                    break;
                case "slot2":
                    randomizeMarketItem2(character);
                    break;
                case "slot3":
                    randomizeMarketItem3(character);
                    break;
                case "slot4":
                    randomizeMarketItem4(character);
                    break;
                case "slot5":
                    randomizeMarketItem5(character);
                    break;
                case "slot6":
                    randomizeMarketItem6(character);
                    break;

            }
            int newCash = character.getCash() - item.getPrice();
            character.setCash(newCash);
            DatabaseHandler.updateUser(character.getName(), "cash", newCash);
            ItemController.setInventorySpaceToItem(character, item, ItemController.findFirstFreeSpaceInInventory(character));
        }
    }

    public void sellItem(Character character, Item item, String invSlot) {
        int newCash = character.getCash() + item.getPrice();

        character.setCash(newCash);
        DatabaseHandler.updateUser(character.getName(), "cash", newCash);

        ItemController.removeItemFromInventory(character, invSlot);

    }

}

package web;

import org.tribot.api2007.types.RSItem;
import scripting.exceptions.PriceNotFoundException;

public class PriceLookup {

    private final static int ITEM_ID_COINS = 995;
    private static final String RSBUDDY_PRICE_URL = "https://api.rsbuddy.com/grandExchange?a=guidePrice&i=";
    private static final String OFFICIAL_GE_URL = "http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=";

    public static int getPrice(RSItem item) throws PriceNotFoundException {
        return getPrice(item.getID());
    }

    public static int getPrice(int itemId) throws PriceNotFoundException {
        if (itemId == ITEM_ID_COINS)
            return 1;
        int price = getRSBuddyPrice(itemId);
        if (price == -1)
            price = getOfficialGEPrice(itemId);
        if (price == -1) {
            throw new PriceNotFoundException(itemId, "Both RSBuddy and Official GE APIs can't find this item, or they're down.");
        }
        return price;
    }

    private static int getRSBuddyPrice (int itemID) {
//        try {
//            JsonObject response = Gson.parse(HTTPRequests.noCatchRequestURL(RSBUDDY_PRICE_URL + itemID, 10000)).asObject();
//            return response.getInt("overall", -1);
//        } catch (IOException e) {
//            return -1;
//        } catch (ParseException e) {
//            return -1;
//        }
        return -1;
    }

    private static int getOfficialGEPrice (int itemID) {
//        String priceString = "";
//        try {
//            JsonObject response = Json.parse(HTTPRequests.noCatchRequestURL(OFFICIAL_GE_URL + itemID, 10000)).asObject();
//            JsonObject temp = response.get("item").asObject();
//            temp = temp.get("current").asObject();
//            JsonValue price = temp.get("price");
//            if (price.isNumber())
//                return price.asInt();
//            else
//                priceString = price.asString();
//                if (price.asString().contains("k") || price.asString().contains("m") || price.asString().contains("b")) {
//                    priceString = priceString.replace(".", "");
//                    priceString = priceString.replace("k", "00");
//                    priceString = priceString.replace("m", "00000");
//                    priceString = priceString.replace("b", "00000000");
//                }
//                return Integer.parseInt(priceString.replace(",", ""));
//        } catch (IOException e) {
//            return -1;
//        } catch (ParseException e) {
//            return -1;
//        }
        return -1;
    }

}

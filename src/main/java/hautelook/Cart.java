package hautelook;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by bill on 4/10/17.
 */
public class Cart {
	
    int OVER_WEIGHT = 10;
    
	Map<String, CartItem> realItems;
    Map<String, Integer> itemCounts;
    int percentOff;
    
    public Cart(){
    	realItems = new HashMap<String, CartItem>();
    	itemCounts = new HashMap<String, Integer>();
    }
    
    public void addItem(String productName, int itemCost) {
    	if (itemAlreadyInCart(productName)) {
    		incrementItemCount(productName);
    	} else {
    		CartItem newItem = new CartItem(productName, itemCost);
    		addNewItemToCart(newItem);
    	}
    }
    
    public void updateItemWeight(String productName, int productWeight) {
    	CartItem item = realItems.get(productName);
    	if (item != null) {
    		item.weight = productWeight;
    	}
    }
    
    public void applyDiscount(int percentOff) {
    	this.percentOff = percentOff;
    }
    public double cartTotal() {
    	double sum = subtotal();
    	int shipping = calculateShipping(sum);
    	return sum + shipping;
    }  
    
    public double subtotal() {
    	int sum = 0;
        for(Map.Entry<String, Integer> itemCount : this.itemCounts.entrySet()) {
        	String itemName = itemCount.getKey();
        	CartItem item = realItems.get(itemName);
        	Integer count = itemCount.getValue();
        	sum += item.cost * count;
        }
        // apply discount
        sum = sum * (100 - this.percentOff) / 100;
        return sum;
    }
    
    public int getQuantityOfProduct(String productName) {
    	return itemCounts.get(productName);
    }
    
    private int calculateShipping(double sum) {
    	// split item under 10lb and over weight items
    	int overWeightCounts = 0;
    	int underWeightCounts = 0;
    	for(Map.Entry<String, CartItem> item: realItems.entrySet()) {
    		String productName = item.getKey();
    		CartItem product = item.getValue();
    		if (product.weight >= OVER_WEIGHT) {
    			overWeightCounts += itemCounts.get(productName);
    		} else {
    			underWeightCounts += itemCounts.get(productName);
    		}
    	}
    	// check flat rate
    	int flatRate = 5;
    	if (sum > 100.0 || underWeightCounts == 0) {
    		flatRate = 0;
    	}
    	return flatRate + overWeightCounts * 20;
    }
    
    private boolean itemAlreadyInCart(String productName) {
    	return itemCounts.keySet().contains(productName);
    }
    
    private void incrementItemCount(String productName) {
    	Integer prevCount = itemCounts.get(productName);
    	itemCounts.put(productName, prevCount + 1);
    }
    
    private void addNewItemToCart(CartItem item) {
    	realItems.put(item.name, item);
    	itemCounts.put(item.name, 1);
    }
}

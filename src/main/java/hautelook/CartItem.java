package hautelook;

public class CartItem {
  String name;
  int cost;
  int weight;
  
  public CartItem(String name, int price) {
	  this.name = name;
	  this.cost = price;
	  this.weight = 0;
  }
  
  public CartItem(String name, int price, int weight) {
	  this.name = name;
	  this.cost = price;
	  this.weight = weight;
  }
}

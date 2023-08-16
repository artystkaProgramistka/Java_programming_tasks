/**
 *
 *  @author Łęczycka Dominika S24494
 *
 */

package zad2;


public class Purchase {
	Client client;
	Article article;
	float amount;
	
	public Purchase(Client client, Article article, float amount) {
		this.client = client;
		this.article = article;
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return ""+client.id+";"+client.name+";"+article.name+";"+article.price+";"+amount;
	}
}

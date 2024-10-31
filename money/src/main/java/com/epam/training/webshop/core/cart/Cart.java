package com.epam.training.webshop.core.cart;

import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Cart {

  private Bank bank;
  @Getter
  private Map<Product, Integer> products;

  public static Cart createEmptyCart(Bank bank) {
    return new Cart(bank, new HashMap<>());
  }

  public void add(Product product, int amount) {
    if (product != null && amount > 0) {
      products.merge(product, amount, Integer::sum);
    }
  }

  public boolean isEmpty() {
    return products.isEmpty();
  }

  public void clear() {
    products.clear();
  }

  public boolean containsProduct(Product product) {
    return products.containsKey(product);
  }

  public void removeProduct(Product product) {
    products.remove(product);
  }

  public Money getAggregatedNetPrice() {
    Money aggregatedPrice = new Money(0, Currency.getInstance("HUF"));
    for (Map.Entry<Product, Integer> entry : products.entrySet()) {
      aggregatedPrice = aggregatedPrice.add(entry.getKey().getNetPrice().multiply(entry.getValue()),
          bank);
    }
    return aggregatedPrice;
  }
}
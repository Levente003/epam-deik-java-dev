package com.epam.training.webshop.core.config;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.cart.grosspricecalculator.GrossPriceCalculator;
import com.epam.training.webshop.core.cart.grosspricecalculator.impl.GrossPriceCalculatorImpl;
import com.epam.training.webshop.core.cart.grosspricecalculator.impl.HUGrossPriceCalculatorDecorator;
import com.epam.training.webshop.core.finance.bank.Bank;
import com.epam.training.webshop.core.finance.bank.StaticBank;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.ProductServiceImpl;
import com.epam.training.webshop.ui.command.AbstractCommand;
import com.epam.training.webshop.ui.interpreter.CommandLineInterpreter;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  @Bean
  public Bank bank(){
    return new StaticBank();
  }

  @Bean
  public Cart cart(Bank bank){
    return Cart.createEmptyCart(bank);
  }

  @Bean(initMethod = "initDb")
  public ProductService productService() {
    return new ProductServiceImpl();
  }

  @Bean
  public GrossPriceCalculator grossPriceCalculator() {
    return new HUGrossPriceCalculatorDecorator(new GrossPriceCalculatorImpl());
  }

  @Bean
  public CommandLineInterpreter commandLineInterpreter(
      List<AbstractCommand> abstractCommandList) {
    return new CommandLineInterpreter(abstractCommandList);
  }
}
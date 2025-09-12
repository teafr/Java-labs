package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics));
        products.add(new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном…", smartphones));
        products.add(new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories));

        Cart cart = new Cart();
        List<Order> orders = new ArrayList<>();

        while (true) {
            System.out.println("\nВиберіть опцію:");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Видалити товар з кошика");
            System.out.println("4 - Переглянути кошик");
            System.out.println("5 - Зробити замовлення");
            System.out.println("6 - Переглянути історію замовлень");
            System.out.println("7 - Знайти товар");
            System.out.println("0 - Вийти");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    for (Product product : products) System.out.println(product);
                    break;
                case 2:
                    System.out.println("Введіть ID товару для додавання до кошика:");
                    int id = scanner.nextInt();

                    Product productToAdd = products.stream().findFirst().filter(product -> product.getId() == id).orElse(null);
                    if (productToAdd == null) {
                        System.out.println("Нема товару за цим ID");
                        break;
                    }

                    cart.addProduct(productToAdd);
                    break;
                case 3:
                    System.out.println("Який товар хочете видалити з кошика? Напишіть ID");
                    for (Product product : cart.getProducts()) {
                        System.out.println(product);
                    }

                    int idToDelete = scanner.nextInt();
                    Product foundProduct = cart.getProducts().stream().findFirst().filter(product -> product.getId() == idToDelete).orElse(null);

                    if (foundProduct != null) {
                        cart.removeProduct(foundProduct);
                        System.out.println("Товар видалено з кошика");
                    }

                    System.out.println("Товар не знайдено");
                    break;
                case 4:
                    System.out.println(cart);
                    break;
                case 5:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній. Додайте товари перед оформленням замовлення.");
                    } else {
                        Order order = new Order(cart);
                        orders.add(order);
                        System.out.println("Замовлення оформлено:");
                        System.out.println(order);
                        cart.clear();
                    }
                    break;
                case 6:
                    System.out.println("Історія замовлень:");
                    for (Order order : orders) {
                        System.out.println(order);
                    }
                    break;
                case 7:
                    System.out.println("Знайти за назвою (1) або категорією (2)? Щоб повернутись до меню, натисніть 0");
                    int option = scanner.nextInt();

                    switch (option) {
                        case 1:
                            System.out.println("Напишіть назіу товару:");
                            String name = scanner.next();

                            List<Product> foundProductsByName = products.stream().filter(product -> product.getName().equals(name)).toList();
                            for (Product product : foundProductsByName) System.out.println(product);
                            break;
                        case 2:
                            System.out.println("Напишіть категорію товару:");
                            String category = scanner.next();

                            List<Product> foundProductsByCategory = products.stream().filter(product -> product.getCategory().getName().equals(category)).toList();
                            for (Product product : foundProductsByCategory) System.out.println(product);
                            break;
                        case 0:
                            System.out.println("Повертаємось до основного меню");
                            return;
                        default:
                            System.out.println("Невідома опція.");
                            break;
                    }
                    break;
                case 0:
                    System.out.println("Дякуємо, що використовували наш магазин!");
                    return;
                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
                    break;
            }

        }
    }
}
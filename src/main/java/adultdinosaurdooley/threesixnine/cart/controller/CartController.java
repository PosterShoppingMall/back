package adultdinosaurdooley.threesixnine.cart.controller;

import adultdinosaurdooley.threesixnine.cart.entity.Cart;
import adultdinosaurdooley.threesixnine.cart.entity.CartProduct;
import adultdinosaurdooley.threesixnine.cart.service.CartService;
import adultdinosaurdooley.threesixnine.product.entity.Product;
import adultdinosaurdooley.threesixnine.product.service.ProductService;
import adultdinosaurdooley.threesixnine.users.entity.User;
import adultdinosaurdooley.threesixnine.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/369")
@RestController
@Slf4j
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/cart/{cart_id}")
    public String myCartPage(@PathVariable("cartId") Long cart_id, Model model){
        User user = userService.findUser(cart_id);
        // 로그인 User == 접속 User
        if(user.getId() == cart_id){
            // User의 장바구니를 가져온다.
            Cart cart = user.getCart();
            // 장바구니의 아이템을 가져온다.

            List<CartProduct> cartProducts = cartService.userCartView(cart);

            int totalPrice = 0;
            for(CartProduct cartProduct : cartProducts){
                totalPrice += ( cartProduct.getProduct().getPrice() * cartProduct.getCount());
            }

            model.addAttribute("cart_itemList",cartProducts);
            model.addAttribute("totalPrice",totalPrice);
            model.addAttribute("user",userService.findUser(cart_id));

            return "/cart/cart";
        }else{
            return "redirect:/main";
        }
    }

    @PostMapping("/user/{cart_id}/cart/{productId}")
    public String myCartAdd(@PathVariable("cartId") Long cart_id,@PathVariable("productId") Long productId,int count){
        User user =userService.findUser(cart_id);
        Product product = productService.productView(productId);

        cartService.addCart(user,product,count);

        return "redirect:/item/view/{itemId}";
    }

    @GetMapping("/user/{cart_id}/cart/{productId}")
    public String myCartDelete(@PathVariable("cartId") Long cart_id, @PathVariable("productId") Long productId){
        User user = userService.findUser(cart_id);
        Cart cart = user.getCart();
        cart.setCount(cart.getCount() - 1);
        cartService.cartProductDelete(productId);

        return "redirect:/user/{id}/cart";
    }
}

package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.data.JdbcOrderRepository;
import tacos.data.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/orders")
public class OrderController {

    private OrderRepository orderRepo;

    @Autowired
    public OrderController(OrderRepository orderRepo)
    {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm(Model model)
    {
        //model.addAttribute("order",new Order());
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute("order") Order order, Errors errors, SessionStatus sessionStatus)
    {
        if(errors.hasErrors())
        {
            return "orderForm";
        }

        order=orderRepo.save(order);
        log.info("Order submitted " + order);
        sessionStatus.setComplete();
        return "redirect:/";
    }
}

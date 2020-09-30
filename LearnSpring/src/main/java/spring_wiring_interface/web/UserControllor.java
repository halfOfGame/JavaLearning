package spring_wiring_interface.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import spring_wiring_interface.service.UserService;
import spring_wiring_interface.service.impl.UserServiceNormal;

@Controller
public class UserControllor {

    @Autowired
    @Qualifier("userServiceFestival")
    private UserService userService;

    public void add() {
        userService.add();
    }
}

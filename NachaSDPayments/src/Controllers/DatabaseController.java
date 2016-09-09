package Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

@Controller
public class DatabaseController{
 
   @RequestMapping(value = "/getHello")
   public Test printHello() {
      Test temp = new Test();
      temp.setStr("hello");
      return temp;
   }
}

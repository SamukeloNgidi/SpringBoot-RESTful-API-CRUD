package com.demo.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {


    @RequestMapping(value = "/sendemail")
    public String sendEmail() {
        return "Email sent successfully";
    }


}

//        @Autowired
//        private UserService service;
//
//
//        @PostMapping("/process_register")
//        public String processRegister(Users users, HttpServletRequest request)
//                throws UnsupportedEncodingException, MessagingException {
//            service.register(users, getSiteURL(request));
//            return "register_success";
//        }
//
//        private String getSiteURL(HttpServletRequest request) {
//            String siteURL = request.getRequestURL().toString();
//            return siteURL.replace(request.getServletPath(), "");
//        }
//
//    @GetMapping("/verify")
//    public String verifyUser(@Param("code") String code) {
//        if (service.verify(code)) {
//            return "verify_success";
//        } else {
//            return "verify_fail";
//        }
//    }




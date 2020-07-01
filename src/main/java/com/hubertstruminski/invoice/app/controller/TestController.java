//package com.hubertstruminski.invoice.app.controller;
//
//import javafx.fxml.FXMLLoader;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//public class TestController {
//
//
//    @RequestMapping("/")
//    public String helloWorld() {
//        return "googleLogin";
//    }
//
//    @GetMapping("/google/login/pass/{accessToken}")
//    public ResponseEntity<?> passAccessTokenToServer(@PathVariable String accessToken) {
//        System.out.println(accessToken);
////        closeWindows();
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }
//
//    private void closeWindows() {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/static/googleLoginFormWindow.fxml"));
//        GoogleLoginFormController controller = loader.getController();
//        controller.closeGoogleLoginWindow();
//    }
//
//    @GetMapping("/google/login/pass")
//    public ResponseEntity<?> passAccessTokenToSerkver() {
//        System.out.println("Hubert Strumiński => /google/login/pass");
////        closeWindows();
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }
//}

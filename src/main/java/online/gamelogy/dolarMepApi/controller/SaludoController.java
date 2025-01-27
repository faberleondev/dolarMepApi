//package online.gamelogy.dolarMepApi.controller;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/saludos")
//public class SaludoController {
//
//    @GetMapping("/hola")
//    public String holaMundo(){
//        return "hola mundo..";
//    }
//    @GetMapping("/nombre/{nombre}/{edad}")
//    public String agregarSaludo(@PathVariable String nombre, @PathVariable int edad){
//        return "hola loco " + nombre + ", tu edad es " + edad;
//    }
//}
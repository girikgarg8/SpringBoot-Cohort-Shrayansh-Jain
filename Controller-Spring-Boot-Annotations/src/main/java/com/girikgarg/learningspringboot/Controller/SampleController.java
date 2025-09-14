package com.girikgarg.learningspringboot.Controller;

import com.girikgarg.learningspringboot.Editor.NamePropertyEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class SampleController {

    @InitBinder
    protected void initBinder(DataBinder binder) {
        binder.registerCustomEditor(String.class, "name", new NamePropertyEditor());
    }

    // Get all users
    @RequestMapping(path="/users", method = RequestMethod.GET)
    @ResponseBody
    public String fetchUsers() {
        return "fetching and returning all user details";
    }

    // Get specific user by name parameter
    @GetMapping(path="/users/search")
    @ResponseBody
    public ResponseEntity<String> fetchUser(@RequestParam String name, @RequestParam (required = false) Integer age) {
        String output = "fetching user details for name: " + name + " and age: " + age;
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }
}

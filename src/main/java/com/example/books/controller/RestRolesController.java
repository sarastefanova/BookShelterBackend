package com.example.books.controller;

import com.example.books.model.Roles;
import com.example.books.service.RolesService;
import org.springframework.http.HttpStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/roles",produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class RestRolesController {

    private final RolesService rolesService;

    public RestRolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Roles createRoles(@RequestBody Roles roles) {

        return rolesService.createRoles(roles);
    }
}

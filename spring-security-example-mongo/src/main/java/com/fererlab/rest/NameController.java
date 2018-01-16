package com.fererlab.rest;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class NameController {

    private static final Logger log = Logger.getLogger(NameController.class.getName());

    private List<String> names = new ArrayList<String>() {{
        add("John");
        add("Jake");
        add("Jim");
    }};

    @GetMapping
    @PreAuthorize("hasRole('NAME_QUERY')")
    public List<String> getAllNames() {
        return names;
    }

    @PreAuthorize("permitAll")
    @GetMapping("/{index}")
    public String getName(@PathVariable Integer index) {
        return names.get(index);
    }

    @PreAuthorize("hasRole('NAME_COMMAND')")
    @PostMapping("/{name}")
    public void createName(@PathVariable String name) {
        names.add(name);
    }

    @PreAuthorize("hasRole('NAME_COMMAND')")
    @PutMapping("/{id}/{name}")
    public void updateName(@PathVariable Integer index, @PathVariable String name) {
        names.set(index, name);
    }

    @PreAuthorize("hasRole('NAME_COMMAND')")
    @DeleteMapping("/{name}")
    public void deleteName(@PathVariable String name) {
        names.remove(name);
    }

}

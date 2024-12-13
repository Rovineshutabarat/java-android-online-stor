package com.online.store.server.controllers.implementations;

import com.online.store.server.controllers.BaseController;
import com.online.store.server.models.Role;
import com.online.store.server.payload.api.SuccessResponse;
import com.online.store.server.services.implementations.RoleServiceImpl;
import com.online.store.server.utils.ResponseBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController implements BaseController<Role, Integer> {
    private final RoleServiceImpl roleService;

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse> getAll() {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success get all roles.",
                roleService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getById(@PathVariable Integer id) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success get role by id.",
                roleService.getById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<SuccessResponse> create(@RequestBody @Valid Role role) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.CREATED,
                "Success creating role.",
                roleService.create(role));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@RequestBody @Valid Role role, @PathVariable Integer id) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success updating role.",
                roleService.update(role, id));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Integer id) {
        return ResponseBuilder.createHttpSuccessResponse(
                HttpStatus.OK,
                "Success deleting role.",
                roleService.delete(id));
    }
}

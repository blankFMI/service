package ro.unibuc.hello.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.entity.User;
import ro.unibuc.hello.data.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users") // full path = /api/users
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repo;

    @GetMapping
    public List<User> all() { return repo.findAll(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) { return repo.save(user); }

    @GetMapping("{id}")
    public User byId(@PathVariable String id) {
        return repo.findById(id).orElseThrow();
    }

    @PutMapping("{id}")
    public User update(@PathVariable String id, @RequestBody User incoming) {
        User current = repo.findById(id).orElseThrow();
        current.setUsername(incoming.getUsername());
        current.setEmail(incoming.getEmail());
        current.setPassword(incoming.getPassword()); // hash in prod!
        current.setAccountType(incoming.getAccountType());
        return repo.save(current);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) { repo.deleteById(id); }
}

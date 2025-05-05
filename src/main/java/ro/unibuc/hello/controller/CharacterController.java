package ro.unibuc.hello.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.entity.Character;
import ro.unibuc.hello.data.repository.CharacterRepository;

import java.util.List;

@RestController
@RequestMapping("/characters") // /api/characters
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterRepository repo;

    @GetMapping
    public List<Character> all() { return repo.findAll(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Character create(@RequestBody Character c) { return repo.save(c); }

    @GetMapping("{id}")
    public Character byId(@PathVariable String id) { return repo.findById(id).orElseThrow(); }

    @PutMapping("{id}")
    public Character update(@PathVariable String id, @RequestBody Character incoming) {
        Character cur = repo.findById(id).orElseThrow();
        cur.setName(incoming.getName());
        cur.setBackground(incoming.getBackground());
        cur.setStyle(incoming.getStyle());
        cur.setPersonality(incoming.getPersonality());
        return repo.save(cur);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) { repo.deleteById(id); }
}

package test.redis.instance.redistest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AutocompleteController(private val autocompleteService: AutocompleteService) {

    @GetMapping("/auto")
    fun autocomplete(@RequestParam query: String): List<String> {
        return autocompleteService.getSuggestions(query)
    }

    @PostMapping("/auto")
    fun saveWithCache(@RequestParam query: String): ResponseEntity<String> {
        autocompleteService.addNewEntry(query){x,y -> autocompleteService.addToStringMapWithCache(x,y)}
        return ResponseEntity.ok("saved with cache")
    }
    @PostMapping("/auto_no_cache")
    fun saveWithOutCache(@RequestParam query: String): ResponseEntity<String> {
        autocompleteService.addNewEntry(query){x,y -> autocompleteService.addToStringMapWithOutCache(x,y)}
        return ResponseEntity.ok("saved without cache")
    }
}

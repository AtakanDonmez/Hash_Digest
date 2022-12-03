package messages;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DigestController {

	private final DigestRepository repository;

	DigestController(DigestRepository repository) {
		this.repository = repository;
	}


	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/messages")
	List<Digest> all() {
		return repository.findAll();
	}
	// end::get-aggregate-root[]

	@PostMapping("/messages")
	Digest newMessage(@RequestBody Digest newDigest) throws NoSuchAlgorithmException {
		String message = newDigest.getMessage();
		newDigest.setShaHash(stringToHash(message));
		return repository.save(newDigest);
	}

	// Single item
	
	@GetMapping("/messages/{hash}")
	Digest one(@PathVariable String hash) {
		Digest digest = repository.findByShaHash(hash);
		if (digest != null){
			return digest;
		} else {
			throw new DigestNotFoundException(hash);
		}
	}

	@PutMapping("/messages/{id}")
	Digest replaceEmployee(@RequestBody Digest newDigest, @PathVariable Long id) {
		
		return repository.findById(id)
			.map(digest -> {
				digest.setMessage(newDigest.getMessage());
				digest.setShaHash(newDigest.getShaHash());
				return repository.save(digest);
			})
			.orElseGet(() -> {
				newDigest.setId(id);
				return repository.save(newDigest);
			});
	}

	@DeleteMapping("/messages/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}

	public String stringToHash(String message) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(message.getBytes(StandardCharsets.UTF_8));
		byte[] digest = md.digest();
		String hex = String.format("%064x", new BigInteger(1, digest));
		return hex;
	}
}

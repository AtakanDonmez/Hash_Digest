package messages;

class DigestNotFoundException extends RuntimeException {

	DigestNotFoundException(String hash) {
		super("Could not find message " + hash);
	}
}

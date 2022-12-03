package messages;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Digest {

	private @Id @GeneratedValue Long id;
	private String message;

	 @Column(name = "shaHash")
	private String shaHash;

	Digest() {}

	Digest(String message, String shaHash) throws NoSuchAlgorithmException {
		this.message = message;
		this.shaHash = stringToHash(message);
	}

	Digest(String message) throws NoSuchAlgorithmException {
		this.message = message;
		this.shaHash = stringToHash(message);
	}

	public Long getId() {
		return this.id;
	}

	public String getMessage() {
		return this.message;
	}

	public String getShaHash() {
		return this.shaHash;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setShaHash(String shaHash) {
		this.shaHash = shaHash;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Digest))
			return false;
		Digest digest = (Digest) o;
		return Objects.equals(this.id, digest.id) && Objects.equals(this.message, digest.message)
				&& Objects.equals(this.shaHash, digest.shaHash);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.message, this.shaHash);
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + this.id + ", message='" + this.message + '\'' + ", shaHash='" + this.shaHash + '\'' + '}';
	}

	public String stringToHash(String message) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(message.getBytes(StandardCharsets.UTF_8));
		byte[] digest = md.digest();
		String hex = String.format("%064x", new BigInteger(1, digest));
		return hex;
	}
}

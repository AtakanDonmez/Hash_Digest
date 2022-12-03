package messages;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface DigestRepository extends JpaRepository<Digest, Long> {

    Digest findByShaHash(String hash);
}

package io.github.nobe0716.iwsu.hash;

import io.github.nobe0716.iwsu.type.IwsuConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * refer: https://arxiv.org/ftp/arxiv/papers/1406/1406.2294.pdf
 * Consistent Hashing 포기 ㅠㅠ
 */
@Slf4j
@Service
public class HashCodeBaseShortener implements ShortenHashGenerator {
	private static final Map<Long, String> longToStrMap = new HashMap<>();

	// 0x7FFFFFFFFFFFFFFF
	static {
		for (Long i = 0L; i < 0xAL; ++i) {
			longToStrMap.put(i, i.toString());
		}
		for (Long i = 0xAL; i < 0xAL + 26; ++i) {
			longToStrMap.put(i, Character.toString((char) (i - 0xA + 'A')));
		}
		for (Long i = 0xAL + 26; i < 0x10 + 52; ++i) {
			longToStrMap.put(i, Character.toString((char) (i - (0xA + 26) + 'a')));
		}
	}

	static String decodeFromHash(long hashAsLong) {
		StringBuilder sb = new StringBuilder();
		while (hashAsLong > 0) {
			long key = hashAsLong % 62;
			hashAsLong /= 62;
			sb.append(longToStrMap.get(key));
		}

		while (sb.length() < IwsuConstant.SHORTEN_URL_LEN) {
			sb.append('0');
		}
		return sb.reverse().toString();
	}

	public static void main(String[] args) {
		System.out.println(decodeFromHash((long) Integer.MAX_VALUE - (long) Integer.MIN_VALUE));
	}

	@Override
	public String digest(String original) {
		long key = (long) original.hashCode() - (long) Integer.MIN_VALUE; // assure that it is positive value
		String shortenHash = decodeFromHash(key);
		log.debug("originalUrl: {}, shortenUrl: {}", original, "http://localhost:8080/" + shortenHash);
		return shortenHash;
	}
}

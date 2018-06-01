package io.github.nobe0716.iwsu.hash;

@FunctionalInterface
public interface ShortenHashGenerator {
	String digest(String original);
}

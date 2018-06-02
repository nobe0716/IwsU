package io.github.nobe0716.iwsu.hash;

import io.github.nobe0716.iwsu.type.IwsuConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThat;

@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShortenHashGeneratorTest {
	private final static List<String> URL_LIST = Lists.newArrayList(
		"https://github.com/",
		"http://kakao.com",
		"http://www.naver.com",
		"https://www.yahoo.com/"
	);
	@Autowired
	private RandomStringUtilBasedShortenHashShortener randomStringUtilBasedShortenHashShortener;
	@Autowired
	private HashCodeBaseShortener hashCodeBaseShortener;

	/**
	 * Every generator must obey shorten hash rule
	 */
	@Test
	public void digest() {
		List<ShortenHashGenerator> generators = Lists.newArrayList(
			//		randomStringUtilBasedShortenHashShortener,
			hashCodeBaseShortener
		);
		for (ShortenHashGenerator generator : generators) {
//			for (String url : URL_LIST) {
			Map<String, String> mapping = new HashMap<>();
			for (int i = 0; i < 100; ++i) {
				String url = RandomStringUtils.random(50);
				String digest = generator.digest(url);
				System.out.println(digest);
				if (mapping.containsKey(digest) && !mapping.get(digest).equals(url)) {
					log.error("Hash Conflict on key: {}. val: {}. {}", digest, url, mapping.get(digest));
					continue;
				}
				mapping.put(digest, url);

				assertThat(digest.length(),
					Matchers.is(IwsuConstant.SHORTEN_URL_LEN));
			}
		}
	}
}
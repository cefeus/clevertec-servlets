package cache.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LFUCacheTest {

    private LFUCache<Integer, String> cache;

    @BeforeEach
    void setUp() {
        cache = new LFUCache<>(5);
        cache.put(1, "value1");
        cache.put(2, "value2");
        cache.put(3, "value3");
    }

    @Test
    void get_shouldReturnCachedValueByKey() {
        // given
        String expected = "value1";
        // when
        Optional<String> actual = cache.get(1);
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actual)
                    .isPresent();
            softly.assertThat(actual.get())
                    .isEqualTo(expected);
        });
    }

    @Test
    void get_shouldNotFindLeastFrequentlyUsedValue_whenAddedMoreElementsThanCapacity() {
        // given
        cache.put(4, "value4");
        cache.put(5, "value5");
        cache.get(2);
        cache.get(3);
        cache.get(4);
        cache.get(5);
        cache.put(6, "value6");
        // when
        Optional<String> actual = cache.get(1);
        // then
        assertThat(actual)
                .isEmpty();
    }

    @Test
    void put_shouldPutValueToCache() {
        // given
        Integer key = 1;
        String expected = "value1";
        // when
        cache.put(key, expected);
        Optional<String> actual = cache.get(key);
        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actual)
                    .isPresent();
            softly.assertThat(actual.get())
                    .isEqualTo(expected);
        });
    }

    @Test
    void pop_shouldDeleteValueInCache() {
        // given
        Integer key = 1;
        String value = "value1";
        cache.put(key, value);
        // when
        cache.pop(key);
        Optional<String> actual = cache.get(key);
        // then
        assertThat(actual)
                .isEmpty();
    }

}

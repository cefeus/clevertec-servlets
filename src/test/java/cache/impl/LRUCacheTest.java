package cache.impl;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    private LRUCache<Integer, String> cache;

    @BeforeEach
    void setUp() {
        cache = new LRUCache<>(5);
        cache.put(1, "value1");
        cache.put(2, "value2");
        cache.put(3, "value3");
    }

    @Test
    void get_shouldReturnValueByKey() {
        // given
        String expected = "value1";
        cache.put(1, expected);
        cache.put(2, "value2");
        cache.put(3, "value3");
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
    void get_shouldNotFindLeastRecentlyUsedValue_whenAddedMoreElementsThanCapacity() {
        // given
        cache.put(4, "value4");
        cache.put(5, "value5");
        cache.put(6, "value6");
        // when
        Optional<String> actual = cache.get(1);
        // then
        assertThat(actual)
                .isEmpty();
    }

    @Test
    void put_shouldPutValueInCache() {
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

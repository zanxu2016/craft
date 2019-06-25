package info.luckydog.craft.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * RedisCounterRepository
 * 分布式系统利用 redis 生成全局唯一ID
 *
 * @author eric
 * @since 2019/6/25
 */
@Repository
@Slf4j
public class RedisCounterRepository {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisCounterRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 根据获取的自增数据,添加日期标识构造分布式全局唯一标识
    private String getNumFromRedis(String changeNumPrefix) {
        String dateStr = LocalDate.now().format(dateTimeFormatter);
        Long value = incrementNum(changeNumPrefix + dateStr);
        return dateStr + StringUtils.leftPad(String.valueOf(value), 4, '0');
    }

    // 从redis中获取自增数据(redis保证自增是原子操作)
    private long incrementNum(String key) {

        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        if (null == factory) {
            log.error("Unable to connect to redis.");
            throw new RuntimeException("internal server error");
        }
        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(key, factory);
        long increment = redisAtomicLong.incrementAndGet();
        if (1 == increment) {
            // 如果数据是初次设置,需要设置超时时间
            redisAtomicLong.expire(1, TimeUnit.DAYS);
        }
        return increment;
    }
}

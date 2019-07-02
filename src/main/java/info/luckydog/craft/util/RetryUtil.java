package info.luckydog.craft.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * RetryUtils
 * 重试
 *
 * @author eric
 * @since 2019/7/1
 */
@Slf4j
public class RetryUtil {

    public static <T extends RetryBaseDTO> T retryOnException(int retryTimes, Callable<T> callable) {

        T t = null;
        for (int i = 1; i <= retryTimes; i++) {
            try {
                t = callable.call();
            } catch (Exception e) {
                log.error("retry on " + i + " times t = " + (t == null ? null : t.success()));
            }
            if (null != t && t.success()) {
                break;
            }
            log.error("retry on " + i + " times t = " + (t == null ? null : t.toString()));
        }

        return t;
    }

    public static <T extends RetryBaseDTO> T retryOnException(int retryTimes, long sleepMills, Callable<T> callable) throws InterruptedException {

        T t = null;
        for (int i = 1; i <= retryTimes; i++) {
            try {
                t = callable.call();
            } catch (Exception e) {
                log.error("retry on " + i + " times t = " + (t == null ? null : t.success()));
            }
            if (null != t && t.success()) {
                break;
            }
            log.error("retry on " + i + " times t = " + (t == null ? null : t.toString()));
            Thread.sleep(sleepMills);
        }

        return t;
    }

    public static void main(String[] args) {
        ProductDTO productDTO = new ProductDTO();
        RetryUtil.retryOnException(2, () -> {
            // 成功的情况，不会重试
//            productDTO.setCode(ResultEnum.SUCCESS.getCode());
//            productDTO.setName("Apple");
//            productDTO.setPrice(30.5);

            // 失败的情况，重试
            productDTO.setCode(ResultEnum.FAILED.getCode());

            return productDTO;
        });
    }
}

@Data
@EqualsAndHashCode(callSuper = true)
class ProductDTO extends RetryBaseDTO {

    private String name;
    private double price;
}

@Data
class RetryBaseDTO {
    private int code;

    public boolean success() {
        return code == ResultEnum.SUCCESS.getCode();
    }
}

enum ResultEnum {
    SUCCESS(0, "成功"),
    FAILED(1, "失败"),
    ;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String msg;
}

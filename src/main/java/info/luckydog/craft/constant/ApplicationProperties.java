package info.luckydog.craft.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ApplicationProperties
 *
 * @author eric
 * @since 2019/6/22
 */
@Data
@Component
public class ApplicationProperties {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Value("${email.name}")
    private String name;
}

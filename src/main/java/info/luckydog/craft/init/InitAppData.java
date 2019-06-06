package info.luckydog.craft.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * InitAppData
 *
 * @author eric
 * @since 2019/6/6
 */
@Component
@Slf4j
public class InitAppData implements ApplicationListener<ContextRefreshedEvent> {

    private boolean init = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (init) {
            return;
        }
        init = true;
        log.info("init app data start...");
        //TODO 初始化应用数据
        log.info("CraftApplication init...");
        log.info("init app data end...");
    }
}

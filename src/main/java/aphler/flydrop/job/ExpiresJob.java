package aphler.flydrop.job;

import aphler.flydrop.service.DropObjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExpiresJob {

    @Autowired
    private DropObjService dropObjService;

    @Scheduled(cron = "0 0 1 * * ?")   //每天1点执行
    public void execute() {
        log.info("过期对象清理定时任务开始");
        int count = dropObjService.removeExpiresDropObj();
        log.info("过期对象清理定时任务完成, 共清理过期对象 {} 个", count);
    }
}

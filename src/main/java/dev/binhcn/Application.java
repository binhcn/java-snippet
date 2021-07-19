package dev.binhcn;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.zalopay.migrator.rollout.client.RolloutClient;
import vn.zalopay.migrator.rollout.client.RolloutClientImpl;
import vn.zalopay.migrator.rollout.client.entity.RolloutConfig;
import vn.zalopay.migrator.rollout.client.exception.RolloutException;
import vn.zalopay.migrator.rollout.client.grpc.RolloutClientStub;

@Slf4j
public class Application {

    private static final Logger LOGGER = LogManager.getLogger(Application.class.getName());

    public static void main(String[] args) {
//        LOGGER.debug("Debug Message Logged !!!");
//        LOGGER.info("Info Message Logged !!!");
//        LOGGER.error("Error Message Logged !!!", new Exception("NullError"));

//        log.debug("Debug Message Logged !!!");
//        log.info("Info Message Logged !!!");
//        log.error("Error Message Logged !!!", new Exception("NullError"));

        RolloutConfig rolloutConfig = new RolloutConfig();
        rolloutConfig.setGrpcHost("localhost");
        rolloutConfig.setGrpcPort(8544);
        rolloutConfig.setPoolSize(1);
        rolloutConfig.setTimeoutInSeconds(1);
        rolloutConfig.setUseSsl(false);

        RolloutClientStub rolloutClientStub = new RolloutClientStub(rolloutConfig);
        RolloutClient client = RolloutClientImpl.builder().rolloutClientStub(rolloutClientStub).build();

        long userId = 1086547658145792L;

        boolean isRolledOut = false;
        try {
            isRolledOut = client.isRolledOut(userId);
        } catch (RolloutException e) {
            e.printStackTrace();
        }
        System.out.println(isRolledOut);
    }
}

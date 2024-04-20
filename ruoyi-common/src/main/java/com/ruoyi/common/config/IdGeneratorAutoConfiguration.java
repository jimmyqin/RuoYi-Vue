package com.ruoyi.common.config;

import com.github.yitter.contract.IIdGenerator;
import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.DefaultIdGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * 必须要给每个实例服务配置一个全局唯一的workerid
 * <pre>{@code
 *     id-generator:
 *       workerid: 0~63
 * }</pre>
 * @Author qinrongjun
 */
@Slf4j
@Getter
@Setter
@Configuration
public class IdGeneratorAutoConfiguration {

    @Value("${id-generator.workerid}")
    private Short workerId;

    @Bean
    public IIdGenerator idGenerator() {
        if (Objects.isNull(workerId)) {
            throw new IllegalArgumentException("请先配置雪花的机器编号,配置如下 id-generator.workerid=xx");
        }
        log.info("当前雪花机器配置的id为: {}", workerId);
        IdGeneratorOptions options = new IdGeneratorOptions(workerId);
        return new DefaultIdGenerator(options);
    }
}

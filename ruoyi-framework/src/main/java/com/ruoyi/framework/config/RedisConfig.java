package com.ruoyi.framework.config;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.properties.RedissonProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * redis配置
 *
 * @author Lion Li
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    private static final String REDIS_PROTOCOL_PREFIX = "redis://";
    private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private RedissonProperties redissonProperties;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redisson() throws IOException {
        String prefix = REDIS_PROTOCOL_PREFIX;
        if (redisProperties.isSsl()) {
            prefix = REDISS_PROTOCOL_PREFIX;
        }
        Config config = new Config();
        config.setThreads(redissonProperties.getThreads())
            .setNettyThreads(redissonProperties.getNettyThreads())
            .setCodec(JsonJacksonCodec.INSTANCE)
            .setTransportMode(redissonProperties.getTransportMode());

        RedissonProperties.SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();
        if (ObjectUtil.isNotNull(singleServerConfig)) {
            // 使用单机模式
            config.useSingleServer()
                .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
                .setDatabase(redisProperties.getDatabase())
                .setPassword(StringUtils.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
                .setTimeout(singleServerConfig.getTimeout())
                .setRetryAttempts(singleServerConfig.getRetryAttempts())
                .setRetryInterval(singleServerConfig.getRetryInterval())
                .setSubscriptionsPerConnection(singleServerConfig.getSubscriptionsPerConnection())
                .setClientName(singleServerConfig.getClientName())
                .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
                .setSubscriptionConnectionMinimumIdleSize(singleServerConfig.getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
                .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
                .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize())
                .setDnsMonitoringInterval(singleServerConfig.getDnsMonitoringInterval());
        }
        // 集群配置方式 参考下方注释
        RedissonProperties.ClusterServersConfig clusterServersConfig = redissonProperties.getClusterServersConfig();
        if (ObjectUtil.isNotNull(clusterServersConfig)) {
            // 使用集群模式
            String finalPrefix = prefix;
            List<String> nodes = redisProperties.getCluster().getNodes()
                .stream()
                .map(node -> finalPrefix + node)
                .collect(Collectors.toList());

            config.useClusterServers()
                .setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
                .setPassword(StringUtils.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
                .setTimeout(clusterServersConfig.getTimeout())
                .setRetryAttempts(clusterServersConfig.getRetryAttempts())
                .setRetryInterval(clusterServersConfig.getRetryInterval())
                .setSubscriptionsPerConnection(clusterServersConfig.getSubscriptionsPerConnection())
                .setClientName(clusterServersConfig.getClientName())
                .setIdleConnectionTimeout(clusterServersConfig.getIdleConnectionTimeout())
                .setPingConnectionInterval(clusterServersConfig.getPingConnectionInterval())
                .setSubscriptionConnectionMinimumIdleSize(clusterServersConfig.getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(clusterServersConfig.getSubscriptionConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(clusterServersConfig.getMasterConnectionMinimumIdleSize())
                .setMasterConnectionPoolSize(clusterServersConfig.getMasterConnectionPoolSize())
                .setSlaveConnectionMinimumIdleSize(clusterServersConfig.getSlaveConnectionMinimumIdleSize())
                .setSlaveConnectionPoolSize(clusterServersConfig.getSlaveConnectionPoolSize())
                .setDnsMonitoringInterval(clusterServersConfig.getDnsMonitoringInterval())
                .setFailedSlaveReconnectionInterval(clusterServersConfig.getFailedSlaveReconnectionInterval())
                .setScanInterval(clusterServersConfig.getScanInterval())
                .setReadMode(clusterServersConfig.getReadMode())
                .setSubscriptionMode(clusterServersConfig.getSubscriptionMode())
                .setNodeAddresses(nodes);
        }
        RedissonClient redissonClient = Redisson.create(config);
        log.info("初始化 redis 配置");
        return redissonClient;
    }

    /**
     * 整合spring-cache
     */
    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        List<RedissonProperties.CacheGroup> cacheGroup = redissonProperties.getCacheGroup();
        Map<String, CacheConfig> config = new HashMap<>();
        for (RedissonProperties.CacheGroup group : cacheGroup) {
            CacheConfig cacheConfig = new CacheConfig(group.getTtl(), group.getMaxIdleTime());
            cacheConfig.setMaxSize(group.getMaxSize());
            config.put(group.getGroupId(), cacheConfig);
        }
        return new RedissonSpringCacheManager(redissonClient, config, JsonJacksonCodec.INSTANCE);
    }

   /* @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new RedissonConnectionFactory();
    }

    @Bean
    public RedisTemplate <String, Object> getRedisTemplate(RedisConnectionFactory connectionFactory)
    {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        // 使用Jackson2JsonRedisSerialize替换默认序列化方式
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //启用默认的类型
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        //序列化类，对象映射设置
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }*/

    /**
     * redis集群配置 yml
     *
     * --- # redis 集群配置(单机与集群只能开启一个另一个需要注释掉)
     * spring:
     *   redis:
     *     cluster:
     *       nodes:
     *         - 192.168.0.100:6379
     *         - 192.168.0.101:6379
     *         - 192.168.0.102:6379
     *     # 密码
     *     password:
     *     # 连接超时时间
     *     timeout: 10s
     *     # 是否开启ssl
     *     ssl: false
     *
     * redisson:
     *   # 线程池数量
     *   threads: 16
     *   # Netty线程池数量
     *   nettyThreads: 32
     *   # 传输模式
     *   transportMode: "NIO"
     *   # 集群配置
     *   clusterServersConfig:
     *     # 客户端名称
     *     clientName: ${ruoyi.name}
     *     # master最小空闲连接数
     *     masterConnectionMinimumIdleSize: 32
     *     # master连接池大小
     *     masterConnectionPoolSize: 64
     *     # slave最小空闲连接数
     *     slaveConnectionMinimumIdleSize: 32
     *     # slave连接池大小
     *     slaveConnectionPoolSize: 64
     *     # 连接空闲超时，单位：毫秒
     *     idleConnectionTimeout: 10000
     *     # ping连接间隔
     *     pingConnectionInterval: 1000
     *     # 命令等待超时，单位：毫秒
     *     timeout: 3000
     *     # 如果尝试在此限制之内发送成功，则开始启用 timeout 计时。
     *     retryAttempts: 3
     *     # 命令重试发送时间间隔，单位：毫秒
     *     retryInterval: 1500
     *     # 从可用服务器的内部列表中排除 Redis Slave 重新连接尝试的间隔。
     *     failedSlaveReconnectionInterval: 3000
     *     # 发布和订阅连接池最小空闲连接数
     *     subscriptionConnectionMinimumIdleSize: 1
     *     # 发布和订阅连接池大小
     *     subscriptionConnectionPoolSize: 50
     *     # 单个连接最大订阅数量
     *     subscriptionsPerConnection: 5
     *     # 扫描间隔
     *     scanInterval: 1000
     *     # DNS监测时间间隔，单位：毫秒
     *     dnsMonitoringInterval: 5000
     *     # 读取模式
     *     readMode: "SLAVE"
     *     # 订阅模式
     *     subscriptionMode: "MASTER"
     */

}

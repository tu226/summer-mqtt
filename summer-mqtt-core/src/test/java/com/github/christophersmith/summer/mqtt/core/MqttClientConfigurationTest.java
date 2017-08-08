package com.github.christophersmith.summer.mqtt.core;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MqttClientConfigurationTest
{
    private static final long VALUE_30000 = 30000;
    private static final long VALUE_60000 = 60000;
    @Rule
    public ExpectedException  thrown      = ExpectedException.none();

    @Test
    public void test()
    {
        MqttClientConfiguration configuration = new MqttClientConfiguration();
        Assert.assertEquals(MqttQualityOfService.QOS_0, configuration.getDefaultQualityOfService());
        configuration.setDefaultQualityOfService(MqttQualityOfService.QOS_1);
        Assert.assertEquals(MqttQualityOfService.QOS_1, configuration.getDefaultQualityOfService());
        Assert.assertEquals(VALUE_60000, configuration.getDisconnectWaitMilliseconds());
        configuration.setDisconnectWaitMilliseconds(VALUE_30000);
        Assert.assertEquals(VALUE_30000, configuration.getDisconnectWaitMilliseconds());
        Assert.assertEquals(VALUE_30000, configuration.getSubscribeWaitMilliseconds());
        configuration.setSubscribeWaitMilliseconds(VALUE_60000);
        Assert.assertEquals(VALUE_60000, configuration.getSubscribeWaitMilliseconds());
        Assert.assertEquals(VALUE_30000,
            configuration.getTopicUnsubscribeWaitTimeoutMilliseconds());
        configuration.setTopicUnsubscribeWaitTimeoutMilliseconds(VALUE_60000);
        Assert.assertEquals(VALUE_60000,
            configuration.getTopicUnsubscribeWaitTimeoutMilliseconds());
        Assert.assertNull(configuration.getMqttClientConnectionStatusPublisher());
        configuration
            .setMqttClientConnectionStatusPublisher(new MqttClientConnectionStatusPublisher()
            {
                @Override
                public boolean isStatusMessageRetained()
                {
                    return false;
                }

                @Override
                public String getStatusTopic()
                {
                    return null;
                }

                @Override
                public MqttQualityOfService getStatusMqttQualityOfService()
                {
                    return null;
                }

                @Override
                public byte[] getDisconnectedPayload(String clientId,
                    MqttClientConnectionType connectionType)
                {
                    return null;
                }

                @Override
                public byte[] getConnectedPayload(String clientId,
                    MqttClientConnectionType connectionType)
                {
                    return null;
                }
            });
        Assert.assertNotNull(configuration.getMqttClientConnectionStatusPublisher());
        configuration.setMqttClientConnectionStatusPublisher(null);
        Assert.assertNull(configuration.getMqttClientConnectionStatusPublisher());
    }

    @Test
    public void testDefaultQualityOfServiceNull()
    {
        MqttClientConfiguration configuration = new MqttClientConfiguration();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("'defaultQualityOfService' must be set!");
        configuration.setDefaultQualityOfService(null);
    }
}

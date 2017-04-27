package $package$

import java.util.Properties

import com.typesafe.config.{Config, ConfigFactory}

object ApplicationConfig {

  private val conf = ConfigFactory.load.resolve()

  implicit def config2Properties(config: Config): Properties = {
    val props = new Properties()
    config.entrySet().forEach { entry =>
      props.put(entry.getKey, entry.getValue.unwrapped())
    }
    props
  }

  val inboundTopic: String      = conf.getString("kafka.topics.inbound")
  val outboundTopic: String     = conf.getString("kafka.topics.outbound")
  val streamsConfig: Properties = conf.getConfig("kafka.streams")

}

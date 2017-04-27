package $package$

import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.kstream.KStreamBuilder
import org.slf4j.LoggerFactory
import ApplicationConfig._

object Application extends App {

  implicit val log = LoggerFactory.getLogger(getClass)

  val builder: KStreamBuilder = new KStreamBuilder()

  StreamTopology(builder)

  val streams = new KafkaStreams(builder, streamsConfig)

  log.debug("Starting $name$")
  streams.start()

  Runtime.getRuntime.addShutdownHook(new Thread(() => {
    log.debug("Stopping $name$")
    streams.close()
  }))

}

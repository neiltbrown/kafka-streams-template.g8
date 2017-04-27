package $package$

import org.apache.avro.generic.GenericRecord
import org.apache.kafka.common.serialization.{Serde, Serdes}
import org.apache.kafka.streams.kstream.{KStream, KStreamBuilder}

import ApplicationConfig._

object StreamTopology {

  def apply(builder: KStreamBuilder): Unit = {
    val source: KStream[String, GenericRecord] = builder.stream(inboundTopic)

    source.to(outboundTopic)
  }

}

package $package$

import org.scalatest.WordSpec
import ApplicationConfig._

class ApplicationIntegrationTest extends WordSpec {

  "The $name$" should {
    "process a stream of messages" in {

      val input = Seq()

      MockedStreams()
        .topology(StreamTopology(_))
        .config(streamsConfig)
        .input(inboundTopic, input)
        .output(outboundTopic, input.size)
    }
  }

}
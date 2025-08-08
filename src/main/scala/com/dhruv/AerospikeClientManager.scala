package com.dhruv

import com.aerospike.client.AerospikeClient
import com.dhruv.config.AerospikeConfig

object AerospikeClientManager {
  private val conf = AerospikeConfig.load() // It calls the load method from AerospikeConfig, which reads the host and port
  lazy val client: AerospikeClient = new AerospikeClient(conf.host, conf.port)
}

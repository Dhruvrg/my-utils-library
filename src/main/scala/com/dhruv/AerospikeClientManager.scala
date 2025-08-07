package com.dhruv

import com.aerospike.client.AerospikeClient
import com.dhruv.config.AerospikeConfig

object AerospikeClientManager {
  private val conf = AerospikeConfig.load()
  lazy val client: AerospikeClient = new AerospikeClient(conf.host, conf.port)
}

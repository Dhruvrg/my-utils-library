package com.dhruv.config

import com.typesafe.config.ConfigFactory

case class AerospikeConfig(host: String, port: Int)

object AerospikeConfig {
  private val config = ConfigFactory.load().getConfig("aerospike")

  def load(): AerospikeConfig = {
    AerospikeConfig(
      host = config.getString("host"),
      port = config.getInt("port")
    )
  }
}

package com.dhruv.config

import com.typesafe.config.ConfigFactory // ConfigFactory from Typesafe Config, used to load settings from application.conf

case class AerospikeConfig(host: String, port: Int)

object AerospikeConfig {
  private val config = ConfigFactory.load().getConfig("aerospike")
  // It loads application.conf file and selects the aerospike section from it

  def load(): AerospikeConfig = {
    AerospikeConfig(
      host = config.getString("host"),
      port = config.getInt("port")
    )
  }
}

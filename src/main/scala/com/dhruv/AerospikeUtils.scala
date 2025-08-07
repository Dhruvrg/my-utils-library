package com.dhruv

import com.aerospike.client.policy.WritePolicy
import com.aerospike.client.{Bin, Key, Record}

object AerospikeUtils {
  private val client = AerospikeClientManager.client

  def put(namespace: String, set: String, key: String, bins: Map[String, Any]): Unit = {
    val k = new Key(namespace, set, key)
    val binArray = bins.map {
      case (name, value: String) => new com.aerospike.client.Bin(name, value)
      case (name, value: Int)    => new com.aerospike.client.Bin(name, value)
      case (name, value: Long)   => new com.aerospike.client.Bin(name, value)
      case (name, value: Double) => new com.aerospike.client.Bin(name, value)
      case (name, value: Boolean)=> new com.aerospike.client.Bin(name, value)
      case (name, value)         =>
        throw new IllegalArgumentException(s"Unsupported bin type for key $name: ${value.getClass}")
    }.toArray
    client.put(new WritePolicy(), k, binArray: _*)
  }

  def get(namespace: String, set: String, key: String): Option[Record] = {
    val k = new Key(namespace, set, key)
    Option(client.get(null, k))
  }
}

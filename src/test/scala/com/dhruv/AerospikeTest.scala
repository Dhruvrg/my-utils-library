package com.dhruv

class AerospikeTest extends App {

  val namespace = "test"
  val set = "demo"

  val data = Map("name" -> "Jay", "age" -> 30)
  AerospikeUtils.put(namespace, set, "user3", data)

  val record = AerospikeUtils.get(namespace, set, "user3")
  println(record.map(_.bins))
}

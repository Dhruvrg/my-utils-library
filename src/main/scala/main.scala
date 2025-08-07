import com.dhruv.AerospikeUtils

object Main {
  def main(args: Array[String]): Unit = {
    val namespace = "test"
    val set = "demo"

    val data = Map("name" -> "Alice", "age" -> 30)
    AerospikeUtils.put(namespace, set, "user1", data)

    val record = AerospikeUtils.get(namespace, set, "user1")
    println(record.map(_.bins))
  }
}

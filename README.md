# Aerospike Scala Utils Library

A lightweight Scala utility library for interacting with [Aerospike](https://www.aerospike.com/), featuring simple `put` and `get` functions and a built-in interactive UI to explore your Aerospike namespaces, sets, and records.

---

## 📦 Installation

Add the following to your `build.sbt`:

```scala
resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies ++= Seq(
  "com.aerospike" % "aerospike-client" % "8.0.0",
  "com.github.Dhruvrg" % "my-utils-library_2.13" % "v0.1.1"
)
```

---

## 🚀 Usage

### Import and Basic Usage

```scala
import com.dhruv.utils.AerospikeUtils

val namespace = "test1"
val set = "demo"
val key = "user9"
val data = Map("name" -> "Sam", "age" -> 22)

// Put data into Aerospike
AerospikeUtils.put(namespace, set, key, data)

// Get it back
val record = AerospikeUtils.get(namespace, set, key)
println(s"Fetched record: $record")
```

---

## 🧭 Interactive UI

Easily explore your Aerospike namespaces, sets, and records using a simple web UI.

### 🔧 How to Launch the UI

1. Create a file named `AppLauncher.scala`
2. Add the following code:

```scala
import com.dhruv.api.AerospikeApi

object AppLauncher {
  def main(args: Array[String]): Unit = {
    println("Launching Aerospike API...")
    AerospikeApi.startServer()
  }
}
```

3. Run the `AppLauncher` object to start the server.

Once started, the interactive UI will allow you to:
- View all available namespaces
- Explore all sets under each namespace
- Browse and inspect records

---

## 📘 Features

- ✅ Simple `put` and `get` API for working with Map-type data
- ✅ Interactive UI for live record inspection
- ✅ Lightweight and easy to integrate

---

## 💬 Support

For bugs, questions, or contributions, feel free to open an [issue](https://github.com/Dhruvrg/my-utils-library/issues) or a [pull request](https://github.com/Dhruvrg/my-utils-library/pulls).

---

## 📝 License

This project is licensed under the MIT License.
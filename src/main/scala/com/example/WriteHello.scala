package com.example

import java.io.File

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{IOResult, ActorMaterializer}
import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString

import scala.concurrent.Future


object WriteHello {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("QuickStart")
    implicit val materializer = ActorMaterializer()

    val source: Source[Int, NotUsed] = Source(1 to 10000)
    val factorials = source.scan(BigInt(1))((acc, next) => acc * next)

    val result: Future[IOResult] =
      factorials
        .map(num => ByteString(s"$num\n"))
        .runWith(FileIO.toFile(new File("target/factorials.txt")))
    println("Hello, world!")

    Thread.sleep(1000)
    system.shutdown()
    system.awaitTermination()  }
}

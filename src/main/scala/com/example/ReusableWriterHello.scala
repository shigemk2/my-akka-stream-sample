package com.example

import java.io.File

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.stream.{ActorMaterializer, IOResult}
import akka.util.ByteString

import scala.concurrent.Future


object ReusableWriterHello {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("QuickStart")
    implicit val materializer = ActorMaterializer()

    val source: Source[Int, NotUsed] = Source(1 to 10000)
    val factorials = source.scan(BigInt(1))((acc, next) => acc * next)

    val result: Future[IOResult] =
      factorials
        .map(_.toString)
        .runWith(lineSink("target/factorials2.txt"))
    println("Hello, world!")
    system.shutdown()
  }

  def lineSink(filename: String): Sink[String, Future[IOResult]] =
    Flow[String]
      .map(s => ByteString(s + "\n"))
      .toMat(FileIO.toFile(new File(filename)))(Keep.right)
}

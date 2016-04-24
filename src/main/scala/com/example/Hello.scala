package com.example

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source


object Hello {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("QuickStart")
    implicit val materializer = ActorMaterializer()

    val source: Source[Int, NotUsed] = Source(1 to 100)
    source.runForeach(i => println(i))(materializer)
    println("Hello, world!")
  }
}

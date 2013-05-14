package com.czechscala.serializer

import org.scalatest.{FlatSpec, FunSuite}
import org.scalatest.matchers.ShouldMatchers

class SerializeTest extends FlatSpec with ShouldMatchers{
 
  "stringToInt" should "convert string to int" in {
    Hello.stringToInt("42") should equal (42)
  }

  "serializer" should "serialize case class" in {
    case class Pokus(a: Int, b:Int)

    StringSerializer.serialize(Pokus(24,42)) should equal("Pokus\na:24\nb:42\n")
  }
}

package com.czechscala.serializer

import scala.reflect.macros._
import scala.language.experimental.macros

object Hello {

  def main(args: Array[String]) {
  	println(sayHello)
  }

  def sayHello: String = "Hello World!"

  def stringToInt(str: String):Int = macro stringToInt_impl

  def stringToInt_impl(c: Context)(str: c.Expr[String]): c.Expr[Int] = {
    import c.universe._

    reify {
      str.splice.toInt
    }
  }
}

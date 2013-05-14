package com.czechscala.serializer

import scala.reflect.macros.Context
import scala.language.experimental.macros

/**
 * Created with IntelliJ IDEA.
 * User: hanny
 * Date: 14.5.13
 * Time: 19:50
 * To change this template use File | Settings | File Templates.
 */

object StringSerializer {
  def serialize[T <: Product](instance: T): String = macro serialize_impl[T]

  def serialize_impl[T <: Product](c:Context)(instance: c.Expr[T])(implicit tag: c.WeakTypeTag[T]): c.Expr[String] = {
    import c.universe._
    val cons = tag.tpe.declarations.filter(_.isMethod).map(_.asMethod).filter(_.isPrimaryConstructor).head
    val names = cons.paramss.head.map(_.name.decoded)
    val values = names.zipWithIndex.map {
      case (name, idx) =>
        val x = c.Expr[String](Literal(Constant(name)))
        val i = c.Expr[Int](Literal(Constant(idx)))
        reify {
          x.splice + ":" + instance.splice.productElement(i.splice).toString + "\n"
        }
    }

    val className = reify{
      instance.splice.productPrefix + "\n"
    }

    val concat = values.foldLeft(className) {
      case (a, b) => reify{a.splice + b.splice}
    }
    reify {
      concat.splice
    }
  }
}
package com.nummulus.boite

sealed abstract class Boite[+A] {
  self =>
  
  /**
   * Returns {@code true} if boite contains no value (Empty or Failure), 
   * {@code false} otherwise.
   */
  def isEmpty: Boolean
  
  /**
   * Returns {@code true} if boite contains a value, {@code false}
   */
  def isDefined: Boolean = !isEmpty
  
  /**
   * Returns the value of the boite if it's full, else the specified default.
   */
  def openOr[B >: A](default: => B): B = default
  
  /**
   * Returns {@code true} if both objects are equal based on the contents of
   * this boite. For Failures, equality is based on equivalence of failure
   * causes.
   */
  override def equals(other: Any): Boolean = (this, other) match {
    case (Full(x), Full(y)) => x == y
    case (Full(x), y) => x == y
    case (x, y: AnyRef) => x eq y
    case _ => false
  }
}

final case class Full[+A](value: A) extends Boite[A] {
  def isEmpty = false
  
  override def openOr[B >: A](default: => B): B = value
}

case object Empty extends BoiteVide

sealed abstract class BoiteVide extends Boite[Nothing] {
  def isEmpty = true
  
  override def openOr[B >: Nothing](default: => B): B = default
}

object Failure {
  def apply(message: String) = new Failure(message, Empty, Empty)
  def apply(throwable: Throwable) = new Failure(throwable.getMessage, Full(throwable), Empty)
}

sealed case class Failure(message: String, exception: Boite[Throwable], chain: Boite[Failure]) extends BoiteVide {
  type A = Nothing
  
  override def equals(other: Any): Boolean = (this, other) match {
    case (Failure(x, y, z), Failure(a, b, c)) => (x, y, z) == (a, b, c)
    case (x, y: AnyRef) => x eq y
    case _ => false
  }
} 
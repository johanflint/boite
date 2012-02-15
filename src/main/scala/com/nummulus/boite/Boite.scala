package com.nummulus
package boite

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
   * Applies a function to the value of the boite if it's full and returns a
   * new boite containing the result. Returns empty otherwise.
   * <p>
   * Differs from flatMap in that the given function is not expected to wrap
   * the result in a boite.
   * 
   * @see flatMap
   */
  def map[B](f: A => B): Boite[B] = Empty
  
  /**
   * Applies a function to the value of the boite if it's full and returns a
   * new boite containing the result. Returns empty otherwise.
   * <p>
   * Differs from map in that the given function is expected to return a boite.
   * 
   * @see map
   */
  def flatMap[B](f: A => Boite[B]): Boite[B] = Empty
  
  /**
   * Applies a function to the value of boite if it's full, otherwise do 
   * nothing.
   */
  def foreach[U](f: A => U): Unit = {}
  
  /**
   * Returns a List of one element if boite is full or an empty list
   * otherwise.
   */
  def toList: List[A] = List()
  
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
  
  override def map[B](f: A => B): Boite[B] = Full(f(value))
  
  override def flatMap[B](f: A => Boite[B]): Boite[B] = f(value)
  
  override def foreach[U](f: A => U): Unit = f(value)
  
  override def toList: List[A] = List(value)
}

case object Empty extends BoiteVide

sealed abstract class BoiteVide extends Boite[Nothing] {
  def isEmpty = true
  
  override def openOr[B >: Nothing](default: => B): B = default
}

object Failure {
  def apply(message: String) = new Failure(message, Empty)
  def apply(throwable: Throwable) = new Failure(throwable.getMessage, Full(throwable))
}

sealed case class Failure(message: String, exception: Boite[Throwable]) extends BoiteVide {
  type A = Nothing
  
  override def map[B](f: A => B): Boite[B] = this
  
  override def flatMap[B](f: A => Boite[B]): Boite[B] = this
  
  override def equals(other: Any): Boolean = (this, other) match {
    case (Failure(x, y), Failure(a, b)) => (x, y) == (a, b)
    case (x, y: AnyRef) => x eq y
    case _ => false
  }
} 
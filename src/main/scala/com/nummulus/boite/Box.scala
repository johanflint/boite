package com.nummulus
package boite

sealed abstract class Box[+A] {
  self =>
  
  /**
   * Returns {@code true} if the box contains no value (Empty or Failure), 
   * {@code false} otherwise.
   */
  def isEmpty: Boolean
  
  /**
   * Returns {@code true} if the box contains a value, {@code false} otherwise.
   */
  def isDefined: Boolean = !isEmpty
  
  /**
   * Returns the value of the box if it's full, else the specified default.
   */
  def getOrElse[B >: A](default: => B): B = default
  
  /**
   * Applies a function to the value of the box if it's full and returns a
   * new box containing the result. Returns empty otherwise.
   * <p>
   * Differs from flatMap in that the given function is not expected to wrap
   * the result in a box.
   * 
   * @see flatMap
   */
  def map[B](f: A => B): Box[B] = Empty
  
  /**
   * Applies a function to the value of the box if it's full and returns a
   * new box containing the result. Returns empty otherwise.
   * <p>
   * Differs from map in that the given function is expected to return a box.
   * 
   * @see map
   */
  def flatMap[B](f: A => Box[B]): Box[B] = Empty
  
  /**
   * Applies a function to the value of the box if it's full, otherwise do 
   * nothing.
   */
  def foreach[U](f: A => U): Unit = {}
  
  /**
   * Returns a List of one element if the box is full or an empty list
   * otherwise.
   */
  def toList: List[A] = List()
  
  /**
   * Returns {@code true} if both objects are equal based on the contents of
   * the box. For failures, equality is based on equivalence of failure
   * causes.
   */
  override def equals(other: Any): Boolean = (this, other) match {
    case (Full(x), Full(y)) => x == y
    case (x, y: AnyRef) => x eq y
    case _ => false
  }
  
  override def hashCode: Int = this match {
    case Full(x) => x.hashCode
    case _ => super.hashCode
  }
}

object Box {
  /**
   * A Box factory which returns a Full(f) if f is not null, Empty if it is,
   * and a Failure if f throws an exception.
   */
  def apply[A](f: => A): Box[A] = try {
      val value = f
      if (value == null) Empty else Full(value)
    }
    catch {
      case e: Exception => Failure(e)
    }
}

final case class Full[+A](value: A) extends Box[A] {
  def isEmpty = false
  
  override def getOrElse[B >: A](default: => B): B = value
  
  override def map[B](f: A => B): Box[B] = Full(f(value))
  
  override def flatMap[B](f: A => Box[B]): Box[B] = f(value)
  
  override def foreach[U](f: A => U): Unit = f(value)
  
  override def toList: List[A] = List(value)
}

sealed abstract class BoiteVide extends Box[Nothing] {
  def isEmpty = true
  
  override def getOrElse[B >: Nothing](default: => B): B = default
}

case object Empty extends BoiteVide

sealed case class Failure(message: String, exception: Box[Throwable]) extends BoiteVide {
  type A = Nothing
  
  override def map[B](f: A => B): Box[B] = this
  
  override def flatMap[B](f: A => Box[B]): Box[B] = this
  
  override def equals(other: Any): Boolean = (this, other) match {
    case (Failure(x, y), Failure(a, b)) => (x, y) == (a, b)
    case (x, y: AnyRef) => x eq y
    case _ => false
  }
  
  override def hashCode: Int =
    exception.hashCode + (if (message == null) 0 else 31 * message.hashCode)
}

object Failure {
  def apply(message: String) = new Failure(message, Empty)
  def apply(throwable: Throwable) = new Failure(throwable.getMessage, Full(throwable))
  def apply(message: String, throwable: Throwable) = new Failure(message, Full(throwable))
}

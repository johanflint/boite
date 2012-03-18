package com.nummulus
package boite
package scalatest

import org.scalatest.TestFailedException
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

trait BoxMatcherTestTrait extends WordSpec with ShouldMatchers {
  val aMessage    = "this is the exception message"
  val anException = new IllegalStateException(aMessage)
  
  val aFull    = Full("foo")
  val anEmpty  = Empty
  val aFailure = Failure(anException)
  
  def itShouldFailSaying(substrings: String*)(block: => Unit) {
    val msg = intercept[TestFailedException](block).getMessage
    substrings foreach { msg should include (_) }
  }
}